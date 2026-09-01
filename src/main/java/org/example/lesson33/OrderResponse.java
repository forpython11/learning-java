package org.example.lesson33;

import java.math.BigDecimal;

public record OrderResponse(
        String id,
        String customerId,
        BigDecimal total,
        OrderStatus status
) {
    public static OrderResponse from(Order order) {
        return new OrderResponse(
                order.id(),
                order.customerId(),
                order.total(),
                order.status()
        );
    }
}
