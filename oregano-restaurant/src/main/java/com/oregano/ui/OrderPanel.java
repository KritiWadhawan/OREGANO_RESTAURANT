package com.oregano.ui;

import com.oregano.dao.MenuDAO;
import com.oregano.dao.OrderDAO;
import com.oregano.model.MenuItem;
import com.oregano.model.Order;
import com.oregano.model.OrderItem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class OrderPanel extends JPanel {
    private final MenuDAO menuDAO = new MenuDAO();
    private final OrderDAO orderDAO = new OrderDAO();
    private final List<MenuItem> allItems;
    private final JComboBox<MenuItem>[] itemBoxes;
    private final JSpinner[] qtySpinners;
    private final JTextField addressField = new JTextField();
    private final JTextField phoneField = new JTextField();
    private final JButton confirmBtn = new JButton("Confirm Order");

    @SuppressWarnings("unchecked")
    public OrderPanel() throws SQLException {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 20, 10, 20));
        allItems = menuDAO.getAllItems();

        add(createHeader(), BorderLayout.NORTH);
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.add(createSelectionPanel());
        center.add(Box.createRigidArea(new Dimension(0, 12)));
        center.add(createFormPanel());
        add(center, BorderLayout.CENTER);
        add(createFooter(), BorderLayout.SOUTH);

        itemBoxes = new JComboBox[5];
        qtySpinners = new JSpinner[5];

        // populate rows
        populateSelectionRows(center);

        styleConfirmButton();
    }

    private JPanel createHeader() {
        JPanel p = new JPanel(new BorderLayout());
        JLabel lbl = new JLabel("Place Your Order", SwingConstants.LEFT);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 22));
        lbl.setBorder(new EmptyBorder(6, 0, 6, 0));
        p.add(lbl, BorderLayout.WEST);
        return p;
    }

    private JPanel createSelectionPanel() {
        JPanel p = new JPanel(new BorderLayout());
        JPanel rows = new JPanel();
        rows.setLayout(new BoxLayout(rows, BoxLayout.Y_AXIS));
        rows.setBorder(new EmptyBorder(6,0,6,0));
        for (int i=0;i<5;i++) {
            JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 6));
            row.setAlignmentX(Component.LEFT_ALIGNMENT);
            // placeholders are added later
            rows.add(row);
        }
        p.add(rows, BorderLayout.CENTER);
        return p;
    }

    private JPanel createFormPanel() {
        JPanel p = new JPanel();
        p.setLayout(new GridBagLayout());
        p.setBorder(new EmptyBorder(10, 0, 10, 0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8,8,8,8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; gbc.gridy = 0;
        p.add(new JLabel("Delivery Address:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        addressField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        p.add(addressField, gbc);
        gbc.gridx = 0; gbc.gridy++;
        gbc.weightx = 0;
        p.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1;
        phoneField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        p.add(phoneField, gbc);
        return p;
    }

    private JPanel createFooter() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        confirmBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        confirmBtn.addActionListener(e -> onConfirm());
        p.add(confirmBtn);
        return p;
    }

    private void populateSelectionRows(JPanel center) {
        JPanel rowsContainer = (JPanel) ((JPanel)center.getComponent(0)).getComponent(0);
        for (int i=0;i<5;i++) {
            JPanel row = (JPanel) rowsContainer.getComponent(i);
            JLabel lbl = new JLabel("Item " + (i+1) + ":");
            lbl.setPreferredSize(new Dimension(70, 24));
            JComboBox<MenuItem> cb = new JComboBox<>();
            cb.setPreferredSize(new Dimension(420, 28));
            cb.setFont(new Font("SansSerif", Font.PLAIN, 14));
            cb.addItem(null); // "None" option
            for (MenuItem mi : allItems) {
                cb.addItem(mi);
            }
            cb.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    if (value == null) {
                        setText("None");
                    } else if (value instanceof MenuItem) {
                        MenuItem m = (MenuItem) value;
                        setText(m.getName() + " - AED " + m.getPrice());
                    }
                    return this;
                }
            });
            JSpinner spinner = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1));
            spinner.setPreferredSize(new Dimension(60, 28));
            row.add(lbl);
            row.add(cb);
            row.add(new JLabel("Qty:"));
            row.add(spinner);
            itemBoxes[i] = cb;
            qtySpinners[i] = spinner;
        }

        // Move the form and footer below properly
    }

    private void styleConfirmButton() {
        confirmBtn.setBackground(new Color(40, 167, 69));
        confirmBtn.setForeground(Color.WHITE);
        confirmBtn.setOpaque(true);
        confirmBtn.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        confirmBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                confirmBtn.setBackground(new Color(30, 150, 60));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                confirmBtn.setBackground(new Color(40, 167, 69));
            }
        });
    }

    private void onConfirm() {
        // Validate selections and inputs
        List<OrderItem> selected = new ArrayList<>();
        int totalQty = 0;
        for (int i=0;i<itemBoxes.length;i++) {
            MenuItem sel = (MenuItem) itemBoxes[i].getSelectedItem();
            if (sel != null) {
                int q = (Integer) qtySpinners[i].getValue();
                totalQty += q;
                selected.add(new OrderItem(sel, q));
            }
        }
        if (totalQty < 1 || totalQty > 5) {
            JOptionPane.showMessageDialog(this, "Please select between 1 and 5 total items across all selections.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String address = addressField.getText().trim();
        String phone = phoneField.getText().trim();
        if (address.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a delivery address.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        // phone validation: digits, optional +, 7-15 digits
        Pattern phonePattern = Pattern.compile("^\\+?\\d{7,15}$");
        if (!phonePattern.matcher(phone).matches()) {
            JOptionPane.showMessageDialog(this, "Please enter a valid phone number (7-15 digits, optional leading +).", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Build order
        Order order = new Order();
        order.setAddress(address);
        order.setPhone(phone);
        for (OrderItem oi : selected) order.addItem(oi);

        // Show confirmation summary and save to DB
        StringBuilder sb = new StringBuilder();
        sb.append("Order Summary:\n\n");
        for (OrderItem oi : order.getItems()) {
            sb.append(String.format("%s x%d - AED %.2f\n", oi.getItem().getName(), oi.getQuantity(), oi.getLineTotal()));
        }
        sb.append("\nTotal: AED ").append(String.format("%.2f", order.getTotal()));
        int confirm = JOptionPane.showConfirmDialog(this, sb.toString(), "Confirm Order", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        // Save to DB
        try {
            int orderId = orderDAO.saveOrder(order);
            JOptionPane.showMessageDialog(this, "Order saved successfully! Order ID: " + orderId + "\nTotal: AED " + String.format("%.2f", order.getTotal()), "Success", JOptionPane.INFORMATION_MESSAGE);
            // clear form
            resetForm();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to save order: " + ex.getMessage(), "DB Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetForm() {
        for (JComboBox<MenuItem> cb : itemBoxes) {
            cb.setSelectedIndex(0);
        }
        for (JSpinner sp : qtySpinners) {
            sp.setValue(1);
        }
        addressField.setText("");
        phoneField.setText("");
    }
}
