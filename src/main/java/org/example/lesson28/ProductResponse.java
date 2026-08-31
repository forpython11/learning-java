package org.example.lesson28;

public record ProductResponse(String id, String name, int stock) {
    public static ProductResponse from(ProductEntity entity) {
        return new ProductResponse(entity.getId(), entity.getName(), entity.getStock());
    }
}
