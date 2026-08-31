package org.example.lesson28;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CreateProductRequest(
        @NotBlank String id,
        @NotBlank String name,
        @Min(0) int stock
) {
}
