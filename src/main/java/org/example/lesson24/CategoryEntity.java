package org.example.lesson24;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
public class CategoryEntity {
    @Id
    private String id;
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @OrderBy("id ASC")
    private List<ProductEntity> products = new ArrayList<>();

    protected CategoryEntity() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<ProductEntity> getProducts() {
        return products;
    }
}
