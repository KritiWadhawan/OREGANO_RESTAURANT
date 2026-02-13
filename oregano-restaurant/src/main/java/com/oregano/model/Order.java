package com.oregano.model;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private int id;
    private String address;
    private String phone;
    private final List<OrderItem> items = new ArrayList<>();

    public Order() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public List<OrderItem> getItems() { return items; }

    public void addItem(OrderItem item) { items.add(item); }

    public double getTotal() {
        return items.stream().mapToDouble(OrderItem::getLineTotal).sum();
    }
}
