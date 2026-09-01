package org.example.lesson32;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class FinalOrderService {
    private final ProductRepository productRepository;
    private final PurchaseOrderRepository orderRepository;

    public FinalOrderService(
            ProductRepository productRepository,
            PurchaseOrderRepository orderRepository
    ) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request) {
        // TODO 1: 查询商品；不存在时抛出 ProductNotFoundException。
        ProductEntity product = productRepository.findById(request.productId())
                .orElse(null);

        // TODO 2: 库存小于购买数量时抛出 InsufficientStockException。

        // TODO 3: 扣减库存、计算总价、保存订单并返回真实响应。
        return new OrderResponse(
                "TODO",
                request.productId(),
                request.quantity(),
                BigDecimal.ZERO,
                product == null ? 0 : product.getStock()
        );
    }
}
