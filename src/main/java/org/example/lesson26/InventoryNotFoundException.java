package org.example.lesson26;

public class InventoryNotFoundException extends RuntimeException {
    public InventoryNotFoundException(String productId) {
        super("Inventory item not found: " + productId);
    }
}
