package org.example.lesson19;

import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Product getById(String id) {
        // TODO 1: 商品不存在时抛出 ProductNotFoundException。
        return new Product(id, "TODO");
    }
}
