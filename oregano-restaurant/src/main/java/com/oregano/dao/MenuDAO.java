package com.oregano.dao;

import com.oregano.db.DBConnection;
import com.oregano.model.MenuCategory;
import com.oregano.model.MenuItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuDAO {

    public List<MenuCategory> getAllCategories() throws SQLException {
        String sql = "SELECT id, name FROM menu_category ORDER BY id";
        List<MenuCategory> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new MenuCategory(rs.getInt("id"), rs.getString("name")));
            }
        }
        return list;
    }

    public List<MenuItem> getItemsByCategory(int categoryId) throws SQLException {
        String sql = "SELECT id, name, description, price, category_id FROM menu_item WHERE category_id = ? ORDER BY id";
        List<MenuItem> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new MenuItem(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getDouble("price"),
                            rs.getInt("category_id")
                    ));
                }
            }
        }
        return list;
    }

    public List<MenuItem> getAllItems() throws SQLException {
        String sql = "SELECT id, name, description, price, category_id FROM menu_item ORDER BY id";
        List<MenuItem> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new MenuItem(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("category_id")
                ));
            }
        }
        return list;
    }

    public MenuItem findById(int id) throws SQLException {
        String sql = "SELECT id, name, description, price, category_id FROM menu_item WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new MenuItem(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getDouble("price"),
                            rs.getInt("category_id")
                    );
                }
            }
        }
        return null;
    }
}
