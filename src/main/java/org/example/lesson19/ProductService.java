package org.example.lesson19;

import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Product getById(String id) {
        // DONE 1: 商品不存在时抛出 ProductNotFoundException。
        return repository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException("Product not found: " + id)
                );
    }
}
