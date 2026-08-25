package org.example.lesson18;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
public class ProductRepository {
    private final Map<String, Product> products = Map.of(
            "P100", new Product("P100", "Keyboard")
    );

    public Optional<Product> findById(String id) {
        // TODO 1: 从 Map 查询商品并包装成 Optional。
        return Optional.empty();
    }
}
