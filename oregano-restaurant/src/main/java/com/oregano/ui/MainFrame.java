package com.oregano.ui;

import com.oregano.dao.MenuDAO;
import com.oregano.model.MenuCategory;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class MainFrame extends JFrame {
    private final JPanel contentPanel = new JPanel(new BorderLayout());
    private final JPanel navBar = new JPanel();
    private final MenuDAO menuDAO = new MenuDAO();

    public MainFrame() {
        setTitle("OREGANO Restaurant");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());
        add(createHeader(), BorderLayout.NORTH);
        add(createNavBar(), BorderLayout.CENTER);
        add(contentPanel, BorderLayout.SOUTH);

        // load home content initially
        showHome();
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBorder(new EmptyBorder(20, 20, 10, 20));
        JLabel title = new JLabel("Welcome to OREGANO!", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        header.add(title, BorderLayout.CENTER);
        return header;
    }

    private JPanel createNavBar() {
        navBar.setLayout(new FlowLayout(FlowLayout.CENTER, 12, 10));
        navBar.setBackground(Color.LIGHT_GRAY);
        navBar.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Add dynamic categories from DB plus Order link
        try {
            List<MenuCategory> cats = menuDAO.getAllCategories();
            for (MenuCategory c : cats) {
                JButton btn = createNavButton(c.getName());
                btn.addActionListener(e -> showMenuCategory(c.getId(), c.getName()));
                navBar.add(btn);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            // fallback to static buttons if DB not available
            String[] names = {"Starters", "Main Courses", "Drinks"};
            for (String n : names) {
                JButton btn = createNavButton(n);
                btn.addActionListener(e -> showMenuByName(n));
                navBar.add(btn);
            }
        }

        JButton orderBtn = createNavButton("Order");
        orderBtn.addActionListener(e -> showOrderPage());
        navBar.add(orderBtn);

        return navBar;
    }

    private JButton createNavButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("SansSerif", Font.PLAIN, 14));
        btn.setBackground(new Color(230, 230, 230));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(200, 200, 200));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(230, 230, 230));
            }
        });
        return btn;
    }

    private void showHome() {
        contentPanel.removeAll();
        JPanel home = new JPanel();
        home.setLayout(new BoxLayout(home, BoxLayout.Y_AXIS));
        home.setBorder(new EmptyBorder(20, 20, 20, 20));
        JLabel welcome = new JLabel("<html><div style='text-align:center'>Welcome to OREGANO!<br><small>Select a category to view our menu or place an order.</small></div></html>", SwingConstants.CENTER);
        welcome.setFont(new Font("SansSerif", Font.PLAIN, 18));
        welcome.setAlignmentX(Component.CENTER_ALIGNMENT);
        home.add(welcome);
        contentPanel.add(home, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private void showMenuCategory(int categoryId, String categoryName) {
        try {
            MenuPanel mp = new MenuPanel(categoryId, categoryName);
            setContentPanel(mp);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Failed to load menu: " + ex.getMessage(), "DB Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    // fallback name-based loader if categories not available
    private void showMenuByName(String name) {
        int id = switch (name) {
            case "Starters" -> 1;
            case "Main Courses" -> 2;
            case "Drinks" -> 3;
            default -> 1;
        };
        showMenuCategory(id, name);
    }

    private void showOrderPage() {
        try {
            OrderPanel op = new OrderPanel();
            setContentPanel(op);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Failed to load order page: " + ex.getMessage(), "DB Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void setContentPanel(JPanel panel) {
        contentPanel.removeAll();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(panel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
}
