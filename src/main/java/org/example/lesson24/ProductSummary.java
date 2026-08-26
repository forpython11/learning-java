package org.example.lesson24;

import java.math.BigDecimal;

public record ProductSummary(String id, String name, BigDecimal price) {
    public static ProductSummary from(ProductEntity entity) {
        return new ProductSummary(
                entity.getId(),
                entity.getName(),
                entity.getPrice()
        );
    }
}
