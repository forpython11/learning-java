package org.example.lesson26;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "inventory_items")
public class InventoryItemEntity {
    @Id
    private String id;
    private String name;
    private int stock;

    // TODO 1: 使用乐观锁保护并发库存更新。
    private long version;

    protected InventoryItemEntity() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getStock() {
        return stock;
    }

    public long getVersion() {
        return version;
    }

    public void decreaseStock(int quantity) {
        // TODO 2: 校验购买数量和剩余库存，然后扣减库存。
    }
}
