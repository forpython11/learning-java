package org.example.lesson28;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "lesson28_products")
public class ProductEntity {
    @Id
    private String id;
    private String name;
    private int stock;

    protected ProductEntity() {
    }

    public ProductEntity(String id, String name, int stock) {
        this.id = id;
        this.name = name;
        this.stock = stock;
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
}
