package org.example.lesson32;

import java.math.BigDecimal;

public record OrderResponse(
        String orderId,
        String productId,
        int quantity,
        BigDecimal total,
        int remainingStock
) {
}
