package com.oregano.dao;

import com.oregano.db.DBConnection;
import com.oregano.model.Order;
import com.oregano.model.OrderItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDAO {

    public int saveOrder(Order order) throws SQLException {
        String insertOrder = "INSERT INTO orders (address, phone) VALUES (?, ?)";
        String insertItem = "INSERT INTO order_items (order_id, menu_item_id, quantity) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection()) {
            try {
                conn.setAutoCommit(false);
                int generatedOrderId;
                try (PreparedStatement ps = conn.prepareStatement(insertOrder, PreparedStatement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, order.getAddress());
                    ps.setString(2, order.getPhone());
                    ps.executeUpdate();
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            generatedOrderId = rs.getInt(1);
                        } else {
                            throw new SQLException("Failed to obtain order id.");
                        }
                    }
                }

                try (PreparedStatement psItem = conn.prepareStatement(insertItem)) {
                    for (OrderItem oi : order.getItems()) {
                        psItem.setInt(1, generatedOrderId);
                        psItem.setInt(2, oi.getItem().getId());
                        psItem.setInt(3, oi.getQuantity());
                        psItem.addBatch();
                    }
                    psItem.executeBatch();
                }

                conn.commit();
                return generatedOrderId;
            } catch (SQLException ex) {
                conn.rollback();
                throw ex;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }
}
