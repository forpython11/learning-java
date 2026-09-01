package org.example.lesson32;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "purchase_orders")
public class PurchaseOrderEntity {
    @Id
    private String id;
    private String productId;
    private int quantity;
    private BigDecimal total;
    private Instant createdAt;

    protected PurchaseOrderEntity() {
    }

    public PurchaseOrderEntity(
            String id,
            String productId,
            int quantity,
            BigDecimal total,
            Instant createdAt
    ) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.total = total;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public String getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
