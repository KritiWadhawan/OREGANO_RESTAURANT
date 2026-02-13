package com.oregano.model;

public class MenuCategory {
    private final int id;
    private final String name;

    public MenuCategory(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() { return id; }
    public String getName() { return name; }
}
