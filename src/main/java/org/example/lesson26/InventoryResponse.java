package org.example.lesson26;

public record InventoryResponse(String id, String name, int stock, long version) {
    public static InventoryResponse from(InventoryItemEntity entity) {
        return new InventoryResponse(
                entity.getId(),
                entity.getName(),
                entity.getStock(),
                entity.getVersion()
        );
    }
}
