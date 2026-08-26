package org.example.lesson27;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(String productId, int stock, int requested) {
        super("Insufficient stock for " + productId + ": available=" + stock + ", requested=" + requested);
    }
}
