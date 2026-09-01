package org.example.lesson33;

import java.math.BigDecimal;

public record Order(
        String id,
        String customerId,
        BigDecimal total,
        OrderStatus status
) {
}
