package org.example.lesson27;

import java.util.Optional;

public interface ProductRepository {
    Optional<Product> findById(String productId);

    void save(Product product);
}
