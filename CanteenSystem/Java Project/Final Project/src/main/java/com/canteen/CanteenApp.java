package com.canteen;

import com.canteen.gui.LoginFrame;

import javax.swing.SwingUtilities;

public class CanteenApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame());
    }
}

