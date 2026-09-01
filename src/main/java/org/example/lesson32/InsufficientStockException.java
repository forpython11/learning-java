package org.example.lesson32;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(String productId) {
        super("Insufficient stock for product: " + productId);
    }
}
