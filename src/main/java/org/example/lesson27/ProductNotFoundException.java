package org.example.lesson27;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String productId) {
        super("Product not found: " + productId);
    }
}
