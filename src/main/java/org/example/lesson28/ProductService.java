package org.example.lesson28;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public ProductResponse findById(String productId) {
        return productRepository.findById(productId)
                .map(ProductResponse::from)
                .orElseThrow(() -> new ProductNotFoundException(productId));
    }

    @Transactional
    public ProductResponse create(CreateProductRequest request) {
        ProductEntity product = new ProductEntity(request.id(), request.name(), request.stock());
        return ProductResponse.from(productRepository.save(product));
    }
}
