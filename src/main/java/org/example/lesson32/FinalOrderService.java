package org.example.lesson32;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

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
        // DONE 1: 查询商品；不存在时抛出 ProductNotFoundException。
        ProductEntity product = productRepository.findById(request.productId())
                .orElseThrow(()->new ProductNotFoundException(request.productId()));

        // DONE 2: 库存小于购买数量时抛出 InsufficientStockException。
        if(request.quantity()>product.getStock()){
            throw new InsufficientStockException(request.productId());
        }
        // DONE 3: 扣减库存、计算总价、保存订单并返回真实响应。
        product.decreaseStock(request.quantity());
        BigDecimal total = product.getPrice().multiply(BigDecimal.valueOf(request.quantity()));
        String orderId = UUID.randomUUID().toString();
        PurchaseOrderEntity order = new PurchaseOrderEntity(
                orderId,
                product.getId(),
                request.quantity(),
                total,
                Instant.now()
        );
        orderRepository.save(order);
        return new OrderResponse(
                orderId,
                request.productId(),
                request.quantity(),
                total,
                product == null ? 0 : product.getStock()
        );
    }
}
