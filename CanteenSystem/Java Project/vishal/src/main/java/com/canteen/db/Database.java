package com.canteen.db;

import com.canteen.model.InventoryItem;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Database {
    /**
     * File-based H2 database in the user's home folder (always writable).
     * Avoids failures when the app is started with a read-only or unpredictable working directory
     * (e.g. IDE / shortcuts starting in {@code System32}).
     * <p>
     * No {@code AUTO_SERVER} here so the standalone H2 Console / another process is less likely to
     * conflict on TCP ports. Close the app before opening the same DB file in the H2 Console.
     */
    private static final String JDBC_URL = initJdbcUrl();

    private static String initJdbcUrl() {
        try {
            Path dir = Path.of(System.getProperty("user.home"), ".canteen_ordering");
            Files.createDirectories(dir);
            String filePath = dir.resolve("canteen_db").toAbsolutePath().normalize().toString().replace('\\', '/');
            return "jdbc:h2:" + filePath + ";DB_CLOSE_DELAY=-1";
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public Connection getConnection() throws SQLException {
        return java.sql.DriverManager.getConnection(JDBC_URL, "sa", "");
    }

    public void initializeIfNeeded() throws SQLException {
        try (Connection conn = getConnection()) {
            try (Statement st = conn.createStatement()) {
                st.execute("""
                    CREATE TABLE IF NOT EXISTS users (
                        username VARCHAR(100) PRIMARY KEY,
                        password VARCHAR(100) NOT NULL
                    )
                """);

                st.execute("""
                    CREATE TABLE IF NOT EXISTS inventory (
                        item_id INT AUTO_INCREMENT PRIMARY KEY,
                        item_name VARCHAR(200) UNIQUE NOT NULL,
                        unit_price DECIMAL(10,2) NOT NULL,
                        stock_count INT NOT NULL
                    )
                """);

                st.execute("""
                    CREATE TABLE IF NOT EXISTS transactions (
                        transaction_id INT AUTO_INCREMENT PRIMARY KEY,
                        student_id VARCHAR(50) NOT NULL,
                        total_amount DECIMAL(12,2) NOT NULL,
                        order_date TIMESTAMP NOT NULL,
                        username VARCHAR(100) NOT NULL
                    )
                """);
            }

            // Seed a default user if empty.
            if (isTableEmpty(conn, "users")) {
                try (PreparedStatement ps = conn.prepareStatement(
                        "INSERT INTO users(username, password) VALUES(?, ?)")) {
                    ps.setString(1, "admin");
                    ps.setString(2, "admin123");
                    ps.executeUpdate();
                }
            }

            // Ensure inventory items exist (even if DB already has data).
            // This is important because the GUI now shows Sandwich/Burger subcategories
            // based on item names like "Paneer Sandwich" and "Chicken Sandwich".
            ensureInventoryItem(conn, "Idli", new BigDecimal("20.00"), 50);
            ensureInventoryItem(conn, "Coffee", new BigDecimal("30.00"), 50);
            ensureInventoryItem(conn, "Sandwich", new BigDecimal("60.00"), 50);
            ensureInventoryItem(conn, "Dosa", new BigDecimal("40.00"), 50);
            ensureInventoryItem(conn, "Burger", new BigDecimal("70.00"), 50);

            // Sandwich subcategories.
            ensureInventoryItem(conn, "Paneer Sandwich", new BigDecimal("60.00"), 50);
            ensureInventoryItem(conn, "Chicken Sandwich", new BigDecimal("60.00"), 50);

            // Burger subcategories.
            ensureInventoryItem(conn, "Cheese Burger", new BigDecimal("70.00"), 50);
            ensureInventoryItem(conn, "Chicken Burger", new BigDecimal("70.00"), 50);
            ensureInventoryItem(conn, "Veg Burger", new BigDecimal("70.00"), 50);
        }
    }

    private boolean isTableEmpty(Connection conn, String tableName) throws SQLException {
        String sql = "SELECT COUNT(*) FROM " + tableName;
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1) == 0;
            }
        }
        return true;
    }

    private void insertInventory(Connection conn, String itemName, BigDecimal unitPrice, int stockCount) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO inventory(item_name, unit_price, stock_count) VALUES(?, ?, ?)")) {
            ps.setString(1, itemName);
            ps.setBigDecimal(2, unitPrice);
            ps.setInt(3, stockCount);
            ps.executeUpdate();
        }
    }

    private void ensureInventoryItem(Connection conn, String itemName, BigDecimal unitPrice, int stockCount) throws SQLException {
        String sql = "SELECT 1 FROM inventory WHERE item_name = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, itemName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return; // Item already exists; don't modify price/stock.
                }
            }
        }
        insertInventory(conn, itemName, unitPrice, stockCount);
    }

    public boolean validateUser(String username, String password) throws SQLException {
        String sql = "SELECT 1 FROM users WHERE username = ? AND password = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public List<InventoryItem> getInventoryItems() throws SQLException {
        String sql = "SELECT item_id, item_name, unit_price, stock_count FROM inventory ORDER BY item_name ASC";
        List<InventoryItem> items = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                items.add(new InventoryItem(
                        rs.getInt("item_id"),
                        rs.getString("item_name"),
                        rs.getBigDecimal("unit_price"),
                        rs.getInt("stock_count")
                ));
            }
        }
        return items;
    }

    public void placeOrder(String username, String studentId, BigDecimal totalAmount, Map<Integer, Integer> itemQuantities)
            throws SQLException {
        // Transaction guarantees: either inventory is decremented + transaction inserted, or nothing happens.
        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);
            try {
                // Check stock first (with row-level lock).
                for (Map.Entry<Integer, Integer> e : itemQuantities.entrySet()) {
                    int itemId = e.getKey();
                    int qty = e.getValue();
                    if (qty <= 0) continue;

                    int currentStock = getStockForUpdate(conn, itemId);
                    if (currentStock < qty) {
                        throw new IllegalStateException("Insufficient stock for item_id=" + itemId);
                    }
                }

                // Decrement stock.
                for (Map.Entry<Integer, Integer> e : itemQuantities.entrySet()) {
                    int itemId = e.getKey();
                    int qty = e.getValue();
                    if (qty <= 0) continue;

                    try (PreparedStatement ps = conn.prepareStatement(
                            "UPDATE inventory SET stock_count = stock_count - ? WHERE item_id = ?")) {
                        ps.setInt(1, qty);
                        ps.setInt(2, itemId);
                        ps.executeUpdate();
                    }
                }

                // Store transaction details.
                try (PreparedStatement ps = conn.prepareStatement(
                        "INSERT INTO transactions(student_id, total_amount, order_date, username) VALUES(?, ?, ?, ?)")) {
                    ps.setString(1, studentId);
                    ps.setBigDecimal(2, totalAmount);
                    ps.setTimestamp(3, Timestamp.from(Instant.now()));
                    ps.setString(4, username);
                    ps.executeUpdate();
                }

                conn.commit();
            } catch (Exception ex) {
                conn.rollback();
                if (ex instanceof IllegalStateException) {
                    throw ex;
                }
                throw new SQLException("Failed to place order", ex);
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }

    private int getStockForUpdate(Connection conn, int itemId) throws SQLException {
        String sql = "SELECT stock_count FROM inventory WHERE item_id = ? FOR UPDATE";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, itemId);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new IllegalStateException("Item not found for item_id=" + itemId);
                }
                return rs.getInt(1);
            }
        }
    }
}

