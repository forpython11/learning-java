package org.example.lesson12;

public class Order {
    private final String id;
    private OrderStatus status;

    public Order(String id, OrderStatus status) {
        this.id = id;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
