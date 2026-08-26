package org.example.lesson26;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record PurchaseRequest(
        @NotBlank String orderId,
        @NotBlank String productId,
        @Min(1) int quantity
) {
}
