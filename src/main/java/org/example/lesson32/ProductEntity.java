package org.example.lesson32;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "final_products")
public class ProductEntity {
    @Id
    private String id;
    private String name;
    private BigDecimal price;
    private int stock;

    protected ProductEntity() {
    }

    public ProductEntity(String id, String name, BigDecimal price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void decreaseStock(int quantity) {
        stock -= quantity;
    }
}
