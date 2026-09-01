package org.example.lesson32;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record CreateOrderRequest(
        @NotBlank String productId,
        @Positive int quantity
) {
}
