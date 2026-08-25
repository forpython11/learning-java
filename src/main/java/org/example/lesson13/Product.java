package org.example.lesson13;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record Product(
        String id,
        // TODO 1: 使用 @JsonProperty 将 JSON 字段名设置为 product_name。
        String name,
        BigDecimal price
) {
}
