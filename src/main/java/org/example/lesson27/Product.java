package org.example.lesson27;

public class Product {
    private final String id;
    private int stock;

    public Product(String id, int stock) {
        this.id = id;
        this.stock = stock;
    }

    public String getId() {
        return id;
    }

    public int getStock() {
        return stock;
    }

    public void decreaseStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("quantity must be greater than 0");
        }
        if (stock < quantity) {
            throw new InsufficientStockException(id, stock, quantity);
        }
        stock -= quantity;
    }
}
