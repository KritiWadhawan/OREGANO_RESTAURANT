package com.oregano.ui;

import com.oregano.dao.MenuDAO;
import com.oregano.model.MenuItem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class MenuPanel extends JPanel {
    private final int categoryId;
    private final String categoryName;
    private final MenuDAO menuDAO = new MenuDAO();

    public MenuPanel(int categoryId, String categoryName) throws SQLException {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 20, 10, 20));
        add(createHeader(), BorderLayout.NORTH);
        add(createMenuList(), BorderLayout.CENTER);
    }

    private JPanel createHeader() {
        JPanel p = new JPanel(new BorderLayout());
        JLabel lbl = new JLabel(categoryName, SwingConstants.LEFT);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 22));
        lbl.setBorder(new EmptyBorder(10, 0, 10, 0));
        p.add(lbl, BorderLayout.WEST);
        return p;
    }

    private JScrollPane createMenuList() throws SQLException {
        List<MenuItem> items = menuDAO.getItemsByCategory(categoryId);
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        for (MenuItem item : items) {
            JPanel row = new JPanel();
            row.setLayout(new BorderLayout());
            row.setBorder(new EmptyBorder(8, 8, 8, 8));
            JLabel name = new JLabel(item.getName() + " - AED " + item.getPrice());
            name.setFont(new Font("SansSerif", Font.PLAIN, 16));
            JLabel desc = new JLabel("<html><i>" + item.getDescription() + "</i></html>");
            desc.setFont(new Font("SansSerif", Font.PLAIN, 14));
            JPanel left = new JPanel();
            left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
            left.setOpaque(false);
            left.add(name);
            left.add(Box.createRigidArea(new Dimension(0,4)));
            left.add(desc);
            row.add(left, BorderLayout.WEST);
            listPanel.add(row);
            listPanel.add(Box.createRigidArea(new Dimension(0,6)));
        }
        JScrollPane sp = new JScrollPane(listPanel);
        sp.setBorder(null);
        return sp;
    }
}
