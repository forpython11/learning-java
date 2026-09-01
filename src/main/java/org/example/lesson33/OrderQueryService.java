package org.example.lesson33;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;

public class OrderQueryService {
    private final OrderQueryRepository repository;

    public OrderQueryService(OrderQueryRepository repository) {
        this.repository = repository;
    }

    public OrderResponse findById(String orderId) {
        // TODO 1: 把 Optional<Order> 转换成 OrderResponse，不存在时抛出 OrderNotFoundException。
        return new OrderResponse(
                "TODO",
                "TODO",
                BigDecimal.ZERO,
                OrderStatus.CREATED
        );
    }

    public List<OrderResponse> findByCustomerId(String customerId) {
        // TODO 2: 把这个客户的 List<Order> 转换成 List<OrderResponse>。
        return List.of();
    }

    public OrderPageResponse findPageByCustomerId(
            String customerId,
            int page,
            int size
    ) {
        Page<Order> result = repository.findPageByCustomerId(
                customerId,
                PageRequest.of(page, size)
        );
        return OrderPageResponse.from(result);
    }
}
