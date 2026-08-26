package org.example.lesson27;

public class OrderService {
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public OrderService(ProductRepository productRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    public OrderResult placeOrder(String productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        product.decreaseStock(quantity);
        productRepository.save(product);
        orderRepository.save(new Order(productId, quantity));

        return new OrderResult(productId, quantity, product.getStock());
    }
}
