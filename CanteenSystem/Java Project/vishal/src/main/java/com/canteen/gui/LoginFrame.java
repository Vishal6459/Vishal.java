package com.canteen.gui;

import com.canteen.db.Database;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Font;
import java.sql.SQLException;

public class LoginFrame extends JFrame {
    private final JTextField usernameField = new JTextField(20);
    private final JPasswordField passwordField = new JPasswordField(20);
    private final JButton loginButton = new JButton("Login");

    private final Database db = new Database();

    public LoginFrame() {
        super("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 200);
        setLocationRelativeTo(null);

        initDb();

        JPanelForm();
        setVisible(true);
    }

    private void initDb() {
        try {
            db.initializeIfNeeded();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Database initialization failed: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void JPanelForm() {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Canteen Ordering System");
        title.setFont(new Font("SansSerif", Font.BOLD, 18));

        JLabel userLabel = new JLabel("Username:");
        JLabel passLabel = new JLabel("Password:");

        loginButton.addActionListener(e -> onLogin());

        var form = new java.awt.Panel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 8, 8, 8);
        c.anchor = GridBagConstraints.WEST;

        c.gridx = 0;
        c.gridy = 0;
        form.add(userLabel, c);

        c.gridx = 1;
        c.gridy = 0;
        form.add(usernameField, c);

        c.gridx = 0;
        c.gridy = 1;
        form.add(passLabel, c);

        c.gridx = 1;
        c.gridy = 1;
        form.add(passwordField, c);

        c.gridx = 1;
        c.gridy = 2;
        form.add(loginButton, c);

        add(title, BorderLayout.NORTH);
        add(form, BorderLayout.CENTER);

        JLabel note = new JLabel("Demo login: admin / admin123");
        add(note, BorderLayout.SOUTH);

        getRootPane().setDefaultButton(loginButton);
    }

    private void onLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter username and password.", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }

        loginButton.setEnabled(false);
        SwingUtilities.invokeLater(() -> {
            try {
                if (db.validateUser(username, password)) {
                    // Redirect to dashboard.
                    var dashboard = new OrderDashboardFrame(username);
                    dashboard.setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                    loginButton.setEnabled(true);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Login failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                loginButton.setEnabled(true);
            }
        });
    }
}

