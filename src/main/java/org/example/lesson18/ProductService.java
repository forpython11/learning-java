package org.example.lesson18;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Optional<Product> findById(String id) {
        // TODO 2: 委托 repository 查询。
        return Optional.empty();
    }
}
