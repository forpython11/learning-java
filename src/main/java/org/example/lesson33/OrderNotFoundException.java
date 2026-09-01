package org.example.lesson33;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String orderId) {
        super("ORDER_NOT_FOUND: " + orderId);
    }
}
