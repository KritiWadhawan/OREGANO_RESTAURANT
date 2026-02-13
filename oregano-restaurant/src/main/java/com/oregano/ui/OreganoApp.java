package com.oregano.ui;

import javax.swing.*;

public class OreganoApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // use system look and feel for native style
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
