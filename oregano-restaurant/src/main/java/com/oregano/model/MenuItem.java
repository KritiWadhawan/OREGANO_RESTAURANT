package com.oregano.model;

public class MenuItem {
    private final int id;
    private final String name;
    private final String description;
    private final double price;
    private final int categoryId;

    public MenuItem(int id, String name, String description, double price, int categoryId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.categoryId = categoryId;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public int getCategoryId() { return categoryId; }

    @Override
    public String toString() {
        // For JComboBox display; renderer will use this but we also create custom renderer in UI
        return name + " - AED " + price;
    }
}
