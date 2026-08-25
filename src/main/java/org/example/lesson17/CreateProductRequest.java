package org.example.lesson17;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreateProductRequest(
        // DONE 1: 添加名称非空白校验和指定消息。
        @NotBlank(message = "Name must not be blank")
        String name,
        // DONE 2: 添加价格为正数校验和指定消息。
        @Positive(message = "Price must be greater than 0")
        BigDecimal price
) {
}
