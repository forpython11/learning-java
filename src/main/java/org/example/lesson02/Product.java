package org.example.lesson02;

public class Product {
    private final String name;
    private final double price;
    private final String category;
    private final boolean inStock;

    public Product(String name, double price, String category, boolean inStock) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.inStock = inStock;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public boolean isInStock() {
        return inStock;
    }
}
