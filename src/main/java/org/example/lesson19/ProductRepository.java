package org.example.lesson19;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
public class ProductRepository {
    private final Map<String, Product> products = Map.of(
            "P100", new Product("P100", "Keyboard")
    );

    public Optional<Product> findById(String id) {
        return Optional.ofNullable(products.get(id));
    }
}
