package org.example.lesson25;

import java.math.BigDecimal;

public record ProductResponse(String id, String name, BigDecimal price) {
    public static ProductResponse from(ProductEntity entity) {
        return new ProductResponse(
                entity.getId(),
                entity.getName(),
                entity.getPrice()
        );
    }
}
