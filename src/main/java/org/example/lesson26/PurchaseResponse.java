package org.example.lesson26;

public record PurchaseResponse(
        String orderId,
        String productId,
        int quantity,
        int remainingStock,
        long version
) {
}
