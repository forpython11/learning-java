package org.example.lesson17;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreateProductRequest(
        // TODO 1: 添加名称非空白校验和指定消息。
        String name,
        // TODO 2: 添加价格为正数校验和指定消息。
        BigDecimal price
) {
}
