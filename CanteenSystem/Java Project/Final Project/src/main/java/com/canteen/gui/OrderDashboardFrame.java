package com.canteen.gui;

import com.canteen.db.Database;
import com.canteen.model.InventoryItem;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class OrderDashboardFrame extends JFrame {
    private static final BigDecimal TAX_PERCENTAGE = new BigDecimal("5.0");
    private static final Color PRODUCTS_BACKGROUND = new Color(245, 250, 255);
    private static final Font PRODUCT_TEXT_FONT = new Font("SansSerif", Font.PLAIN, 16);

    private final Database db = new Database();
    private final String username;

    private JPanel itemsPanel;
    private JPanel allItemsPanel;
    private JPanel sandwichItemsPanel;
    private JPanel burgerItemsPanel;
    private JPanel sandwichSubcategoryPanel;
    private JPanel burgerSubcategoryPanel;
    private JTabbedPane categoryTabs;
    private List<InventoryItem> allInventoryItems = List.of();
    private String sandwichFilterPrefix = "ALL";
    private String burgerFilterPrefix = "ALL";

    private final JTextField studentIdField = new JTextField(15);
    private final JButton placeOrderButton = new JButton("Place Order");
    private final JButton clearAllButton = new JButton("Clear All");

    private final JLabel runningTotalLabel = new JLabel("Running Total (before tax): INR 0.00");
    private final JLabel taxAmountLabel = new JLabel("Tax (" + TAX_PERCENTAGE.stripTrailingZeros().toPlainString() + "%): INR 0.00");
    private final JLabel finalTotalLabel = new JLabel("Total Price: INR 0.00");

    private final Map<Integer, JCheckBox> itemChecks = new HashMap<>();
    private final Map<Integer, JSpinner> itemQtySpinners = new HashMap<>();
    private final Map<Integer, InventoryItem> inventoryById = new HashMap<>();

    private final DecimalFormat moneyFormat = new DecimalFormat("0.00");

    public OrderDashboardFrame(String username) {
        super("Order Dashboard");
        this.username = username;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(760, 520);
        setLocationRelativeTo(null);

        moneyFormat.setDecimalSeparatorAlwaysShown(true);

        initUi();
        loadInventoryAndBuildTabs();
        setVisible(true);
    }

    private void initUi() {
        setLayout(new BorderLayout());
        getContentPane().setBackground(PRODUCTS_BACKGROUND);

        // Top panel: student ID + logged-in username.
        JPanel topPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 8, 8, 8);
        c.anchor = GridBagConstraints.WEST;

        JLabel loggedIn = new JLabel("Logged in: " + username);

        c.gridx = 0;
        c.gridy = 0;
        topPanel.add(loggedIn, c);

        c.gridx = 0;
        c.gridy = 1;
        topPanel.add(new JLabel("Student ID:"), c);

        c.gridx = 1;
        c.gridy = 1;
        topPanel.add(studentIdField, c);

        add(topPanel, BorderLayout.NORTH);

        // Center panel: category tabs (All / Sandwich / Burger).
        categoryTabs = new JTabbedPane();
        categoryTabs.setBackground(PRODUCTS_BACKGROUND);

        // --- All tab ---
        allItemsPanel = new JPanel();
        allItemsPanel.setLayout(new BoxLayout(allItemsPanel, BoxLayout.Y_AXIS));
        allItemsPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        allItemsPanel.setBackground(PRODUCTS_BACKGROUND);
        JScrollPane allScrollPane = new JScrollPane(allItemsPanel);
        allScrollPane.getViewport().setBackground(PRODUCTS_BACKGROUND);
        JPanel allTabPanel = new JPanel(new BorderLayout());
        allTabPanel.setBackground(PRODUCTS_BACKGROUND);
        allTabPanel.add(allScrollPane, BorderLayout.CENTER);
        categoryTabs.addTab("All", allTabPanel);

        // --- Sandwich tab ---
        sandwichSubcategoryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        sandwichSubcategoryPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        sandwichSubcategoryPanel.setBackground(PRODUCTS_BACKGROUND);

        sandwichItemsPanel = new JPanel();
        sandwichItemsPanel.setLayout(new BoxLayout(sandwichItemsPanel, BoxLayout.Y_AXIS));
        sandwichItemsPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        sandwichItemsPanel.setBackground(PRODUCTS_BACKGROUND);
        JScrollPane sandwichScrollPane = new JScrollPane(sandwichItemsPanel);
        sandwichScrollPane.getViewport().setBackground(PRODUCTS_BACKGROUND);
        JPanel sandwichTabPanel = new JPanel(new BorderLayout());
        sandwichTabPanel.setBackground(PRODUCTS_BACKGROUND);
        sandwichTabPanel.add(sandwichSubcategoryPanel, BorderLayout.NORTH);
        sandwichTabPanel.add(sandwichScrollPane, BorderLayout.CENTER);
        categoryTabs.addTab("Sandwich", sandwichTabPanel);

        // --- Burger tab ---
        burgerSubcategoryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        burgerSubcategoryPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        burgerSubcategoryPanel.setBackground(PRODUCTS_BACKGROUND);

        burgerItemsPanel = new JPanel();
        burgerItemsPanel.setLayout(new BoxLayout(burgerItemsPanel, BoxLayout.Y_AXIS));
        burgerItemsPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        burgerItemsPanel.setBackground(PRODUCTS_BACKGROUND);
        JScrollPane burgerScrollPane = new JScrollPane(burgerItemsPanel);
        burgerScrollPane.getViewport().setBackground(PRODUCTS_BACKGROUND);
        JPanel burgerTabPanel = new JPanel(new BorderLayout());
        burgerTabPanel.setBackground(PRODUCTS_BACKGROUND);
        burgerTabPanel.add(burgerSubcategoryPanel, BorderLayout.NORTH);
        burgerTabPanel.add(burgerScrollPane, BorderLayout.CENTER);
        categoryTabs.addTab("Burger", burgerTabPanel);

        add(categoryTabs, BorderLayout.CENTER);

        // Keep button state in sync when Student ID changes.
        studentIdField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                recalcRunningTotal();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                recalcRunningTotal();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                recalcRunningTotal();
            }
        });

        // Bottom panel: running total + place order button.
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 12, 8));

        JPanel totalsPanel = new JPanel();
        totalsPanel.setLayout(new BoxLayout(totalsPanel, BoxLayout.Y_AXIS));
        totalsPanel.add(runningTotalLabel);
        totalsPanel.add(taxAmountLabel);
        totalsPanel.add(finalTotalLabel);
        bottomPanel.add(totalsPanel, BorderLayout.CENTER);

        clearAllButton.addActionListener(e -> {
            for (JCheckBox cb : itemChecks.values()) cb.setSelected(false);
            for (JSpinner sp : itemQtySpinners.values()) {
                sp.setValue(0);
                sp.setEnabled(false);
            }
            recalcRunningTotal();
        });

        placeOrderButton.addActionListener(e -> onPlaceOrder());

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionPanel.add(clearAllButton);
        actionPanel.add(placeOrderButton);
        bottomPanel.add(actionPanel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);

        placeOrderButton.setEnabled(false);
    }

    private void loadInventoryAndBuildTabs() {
        try {
            allInventoryItems = db.getInventoryItems();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Failed to load inventory: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        rebuildSubcategoryButtons();

        // Rebuild list based on selected tab.
        categoryTabs.addChangeListener(e -> rebuildCurrentTabItems());
        rebuildCurrentTabItems();
    }

    private void rebuildSubcategoryButtons() {
        sandwichSubcategoryPanel.removeAll();
        burgerSubcategoryPanel.removeAll();

        // Group by "prefix" before the keyword (e.g., "Paneer Sandwich" -> "Paneer").
        Map<String, String> sandwichPrefixToLabel = new HashMap<>();
        Map<String, String> burgerPrefixToLabel = new HashMap<>();

        for (InventoryItem item : allInventoryItems) {
            String name = item.getItemName();

            if (isSandwichItemName(name)) {
                String prefix = extractPrefixBeforeKeyword(name, "sandwich");
                sandwichPrefixToLabel.putIfAbsent(prefix, labelForPrefix(prefix, "Sandwich"));
            } else if (isBurgerItemName(name)) {
                String prefix = extractPrefixBeforeKeyword(name, "burger");
                burgerPrefixToLabel.putIfAbsent(prefix, labelForPrefix(prefix, "Burger"));
            }
        }

        // Sandwich subcategories.
        JButton allSandwichBtn = new JButton("All Sandwiches");
        allSandwichBtn.addActionListener(e -> {
            sandwichFilterPrefix = "ALL";
            rebuildCurrentTabItems();
        });
        sandwichSubcategoryPanel.add(allSandwichBtn);

        var sandwichPrefixes = new java.util.ArrayList<>(sandwichPrefixToLabel.keySet());
        sandwichPrefixes.sort(String::compareToIgnoreCase);
        for (String prefix : sandwichPrefixes) {
            String label = sandwichPrefixToLabel.get(prefix);
            JButton btn = new JButton(label);
            btn.addActionListener(e -> {
                sandwichFilterPrefix = prefix;
                rebuildCurrentTabItems();
            });
            sandwichSubcategoryPanel.add(btn);
        }

        // Burger subcategories.
        JButton allBurgerBtn = new JButton("All Burgers");
        allBurgerBtn.addActionListener(e -> {
            burgerFilterPrefix = "ALL";
            rebuildCurrentTabItems();
        });
        burgerSubcategoryPanel.add(allBurgerBtn);

        var burgerPrefixes = new java.util.ArrayList<>(burgerPrefixToLabel.keySet());
        burgerPrefixes.sort(String::compareToIgnoreCase);
        for (String prefix : burgerPrefixes) {
            String label = burgerPrefixToLabel.get(prefix);
            JButton btn = new JButton(label);
            btn.addActionListener(e -> {
                burgerFilterPrefix = prefix;
                rebuildCurrentTabItems();
            });
            burgerSubcategoryPanel.add(btn);
        }

        sandwichSubcategoryPanel.revalidate();
        sandwichSubcategoryPanel.repaint();
        burgerSubcategoryPanel.revalidate();
        burgerSubcategoryPanel.repaint();
    }

    private void rebuildCurrentTabItems() {
        int selectedIndex = categoryTabs.getSelectedIndex();
        if (selectedIndex == 0) {
            buildItemsList(allItemsPanel, allInventoryItems);
        } else if (selectedIndex == 1) {
            buildItemsList(sandwichItemsPanel, filterSandwichItems());
        } else if (selectedIndex == 2) {
            buildItemsList(burgerItemsPanel, filterBurgerItems());
        }
    }

    private List<InventoryItem> filterSandwichItems() {
        if ("ALL".equals(sandwichFilterPrefix)) {
            var result = new java.util.ArrayList<InventoryItem>();
            for (InventoryItem item : allInventoryItems) {
                if (isSandwichItemName(item.getItemName())) result.add(item);
            }
            return result;
        }

        var result = new java.util.ArrayList<InventoryItem>();
        for (InventoryItem item : allInventoryItems) {
            if (!isSandwichItemName(item.getItemName())) continue;
            String prefix = extractPrefixBeforeKeyword(item.getItemName(), "sandwich");
            if (sandwichFilterPrefix.equals(prefix)) result.add(item);
        }
        return result;
    }

    private List<InventoryItem> filterBurgerItems() {
        if ("ALL".equals(burgerFilterPrefix)) {
            var result = new java.util.ArrayList<InventoryItem>();
            for (InventoryItem item : allInventoryItems) {
                if (isBurgerItemName(item.getItemName())) result.add(item);
            }
            return result;
        }

        var result = new java.util.ArrayList<InventoryItem>();
        for (InventoryItem item : allInventoryItems) {
            if (!isBurgerItemName(item.getItemName())) continue;
            String prefix = extractPrefixBeforeKeyword(item.getItemName(), "burger");
            if (burgerFilterPrefix.equals(prefix)) result.add(item);
        }
        return result;
    }

    private boolean isSandwichItemName(String name) {
        return name != null && name.toLowerCase(Locale.ROOT).endsWith("sandwich");
    }

    private boolean isBurgerItemName(String name) {
        return name != null && name.toLowerCase(Locale.ROOT).endsWith("burger");
    }

    private String extractPrefixBeforeKeyword(String itemName, String keywordLower) {
        if (itemName == null) return "";
        String lower = itemName.toLowerCase(Locale.ROOT);
        if (!lower.endsWith(keywordLower)) return "";
        String prefix = itemName.substring(0, itemName.length() - keywordLower.length()).trim();
        return prefix;
    }

    private String labelForPrefix(String prefix, String suffixLabel) {
        // Prefix "" represents the "classic" item (e.g., "Sandwich" / "Burger").
        if (prefix == null || prefix.isBlank()) return suffixLabel;
        return prefix + " " + suffixLabel;
    }

    private void buildItemsList(JPanel targetPanel, List<InventoryItem> items) {
        // Reset state for the currently displayed tab.
        itemChecks.clear();
        itemQtySpinners.clear();
        inventoryById.clear();

        this.itemsPanel = targetPanel;
        itemsPanel.removeAll();

        // Simple header row.
        JPanel header = new JPanel(new BorderLayout());
        header.setBorder(BorderFactory.createEmptyBorder(0, 4, 8, 4));
        header.setBackground(PRODUCTS_BACKGROUND);

        JLabel foodItemHeader = new JLabel("Food Item");
        foodItemHeader.setFont(PRODUCT_TEXT_FONT);
        JLabel qtyHeader = new JLabel("Qty");
        qtyHeader.setFont(PRODUCT_TEXT_FONT);
        header.add(foodItemHeader, BorderLayout.CENTER);
        header.add(qtyHeader, BorderLayout.EAST);

        itemsPanel.add(header);

        for (InventoryItem item : items) {
            int itemId = item.getItemId();
            inventoryById.put(itemId, item);

            JCheckBox checkBox = new JCheckBox();
            JLabel nameLabel = new JLabel(item.getItemName() + " (INR " + money(item.getUnitPrice()) + ")");
            nameLabel.setFont(PRODUCT_TEXT_FONT);
            checkBox.setBackground(PRODUCTS_BACKGROUND);

            JSpinner qtySpinner = new JSpinner(new SpinnerNumberModel(0, 0, 999, 1));
            qtySpinner.setEnabled(false);
            qtySpinner.setFont(PRODUCT_TEXT_FONT);

            checkBox.addActionListener(e -> {
                boolean selected = checkBox.isSelected();
                qtySpinner.setEnabled(selected);
                if (!selected) {
                    qtySpinner.setValue(0);
                }
                recalcRunningTotal();
            });

            qtySpinner.addChangeListener(e -> recalcRunningTotal());

            itemChecks.put(itemId, checkBox);
            itemQtySpinners.put(itemId, qtySpinner);

            JPanel row = new JPanel(new BorderLayout());
            row.setBorder(BorderFactory.createEmptyBorder(6, 4, 6, 4));
            row.setBackground(PRODUCTS_BACKGROUND);

            JPanel left = new JPanel(new BorderLayout());
            left.setBackground(PRODUCTS_BACKGROUND);
            left.add(checkBox, BorderLayout.WEST);
            left.add(nameLabel, BorderLayout.CENTER);

            JPanel right = new JPanel(new BorderLayout());
            right.setBackground(PRODUCTS_BACKGROUND);
            JLabel qtyLabel = new JLabel("Qty: ");
            qtyLabel.setFont(PRODUCT_TEXT_FONT);
            right.add(qtyLabel, BorderLayout.WEST);
            right.add(qtySpinner, BorderLayout.CENTER);

            row.add(left, BorderLayout.WEST);
            row.add(right, BorderLayout.EAST);

            itemsPanel.add(row);
        }

        itemsPanel.revalidate();
        itemsPanel.repaint();
        recalcRunningTotal();
    }

    private void recalcRunningTotal() {
        BigDecimal subtotal = computeSubtotal();
        runningTotalLabel.setText("Running Total (before tax): INR " + money(subtotal));
        BigDecimal taxAmount = subtotal.multiply(TAX_PERCENTAGE)
                .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
        BigDecimal finalTotal = subtotal.add(taxAmount).setScale(2, RoundingMode.HALF_UP);

        taxAmountLabel.setText("Tax (" + TAX_PERCENTAGE.stripTrailingZeros().toPlainString() + "%): INR " + money(taxAmount));
        finalTotalLabel.setText("Total Price: INR " + money(finalTotal));

        placeOrderButton.setEnabled(!studentIdField.getText().trim().isEmpty() && hasAtLeastOneItemWithQty());
    }

    private BigDecimal computeSubtotal() {
        BigDecimal subtotal = BigDecimal.ZERO;
        for (Map.Entry<Integer, InventoryItem> entry : inventoryById.entrySet()) {
            InventoryItem item = entry.getValue();
            JCheckBox cb = itemChecks.get(item.getItemId());
            JSpinner sp = itemQtySpinners.get(item.getItemId());
            if (cb == null || sp == null) continue;
            if (!cb.isSelected()) continue;

            int qty = (Integer) sp.getValue();
            if (qty <= 0) continue;

            BigDecimal line = item.getUnitPrice().multiply(new BigDecimal(qty));
            subtotal = subtotal.add(line);
        }
        return subtotal.setScale(2, RoundingMode.HALF_UP);
    }

    private boolean hasAtLeastOneItemWithQty() {
        for (Map.Entry<Integer, JCheckBox> e : itemChecks.entrySet()) {
            int itemId = e.getKey();
            JCheckBox cb = e.getValue();
            if (cb == null || !cb.isSelected()) continue;
            JSpinner sp = itemQtySpinners.get(itemId);
            if (sp == null) continue;
            int qty = (Integer) sp.getValue();
            if (qty > 0) return true;
        }
        return false;
    }

    private Map<Integer, Integer> collectQuantitiesFromUi() {
        Map<Integer, Integer> quantities = new HashMap<>();
        for (Map.Entry<Integer, JCheckBox> e : itemChecks.entrySet()) {
            int itemId = e.getKey();
            JCheckBox cb = e.getValue();
            if (cb == null || !cb.isSelected()) continue;
            JSpinner sp = itemQtySpinners.get(itemId);
            if (sp == null) continue;
            int qty = (Integer) sp.getValue();
            if (qty > 0) quantities.put(itemId, qty);
        }
        return quantities;
    }

    private void onPlaceOrder() {
        String studentId = studentIdField.getText().trim();
        if (studentId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Student ID.", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Map<Integer, Integer> quantities = collectQuantitiesFromUi();

        if (quantities.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Select at least one item with quantity > 0.", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Build an itemized receipt using the prices loaded into the UI.
            BigDecimal subtotal = BigDecimal.ZERO;
            StringBuilder receipt = new StringBuilder();
            receipt.append("CANTEEN RECEIPT\n");
            receipt.append("----------------------------\n");
            receipt.append("Username: ").append(username).append("\n");
            receipt.append("Student ID: ").append(studentId).append("\n\n");

            for (Map.Entry<Integer, Integer> q : quantities.entrySet()) {
                InventoryItem item = inventoryById.get(q.getKey());
                if (item == null) continue;
                int qty = q.getValue();
                BigDecimal lineTotal = item.getUnitPrice().multiply(new BigDecimal(qty));
                subtotal = subtotal.add(lineTotal);

                receipt.append(item.getItemName())
                        .append(" | Unit: INR ").append(money(item.getUnitPrice()))
                        .append(" | Qty: ").append(qty)
                        .append(" | Line: INR ").append(money(lineTotal))
                        .append("\n");
            }

            subtotal = subtotal.setScale(2, RoundingMode.HALF_UP);
            BigDecimal taxAmount = subtotal.multiply(TAX_PERCENTAGE).divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
            BigDecimal finalTotal = subtotal.add(taxAmount).setScale(2, RoundingMode.HALF_UP);

            receipt.append("\n");
            receipt.append("Subtotal: INR ").append(money(subtotal)).append("\n");
            receipt.append("Tax (").append(TAX_PERCENTAGE.stripTrailingZeros().toPlainString()).append("%): INR ").append(money(taxAmount)).append("\n");
            receipt.append("Total Price: INR ").append(money(finalTotal)).append("\n");

            // Store transaction + decrement stock.
            db.placeOrder(username, studentId, finalTotal, quantities);

            receipt.append("\nOrder saved successfully.\n");

            JOptionPane.showMessageDialog(
                    this,
                    createReceiptScrollPane(receipt.toString()),
                    "Receipt",
                    JOptionPane.INFORMATION_MESSAGE
            );

            // Clear selections after success.
            for (JCheckBox cb : itemChecks.values()) cb.setSelected(false);
            for (JSpinner sp : itemQtySpinners.values()) {
                sp.setValue(0);
                sp.setEnabled(false);
            }
            recalcRunningTotal();
        } 
        
        catch (IllegalStateException ex) {
            JOptionPane.showMessageDialog(this, "Order failed: " + ex.getMessage(), "Stock Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Order failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JScrollPane createReceiptScrollPane(String receiptText) {
        var receiptArea = new javax.swing.JTextArea(receiptText, 20, 45);
        receiptArea.setEditable(false);
        receiptArea.setLineWrap(true);
        receiptArea.setWrapStyleWord(true);
        return new JScrollPane(receiptArea);
    }

    private String money(BigDecimal amount) {
        if (amount == null) return "0.00";
        BigDecimal scaled = amount.setScale(2, RoundingMode.HALF_UP);
        return moneyFormat.format(scaled);
    }

    private String money(int amount) {
        return String.format(Locale.US, "%.2f", (double) amount);
    }

    private String money(double amount) {
        return String.format(Locale.US, "%.2f", amount);
    }
}

