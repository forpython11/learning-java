package org.example.lesson33;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryOrderQueryRepository implements OrderQueryRepository {
    private final Map<String, Order> orders = new LinkedHashMap<>();

    public InMemoryOrderQueryRepository(List<Order> initialOrders) {
        initialOrders.forEach(order -> orders.put(order.id(), order));
    }

    @Override
    public Optional<Order> findById(String orderId) {
        return Optional.ofNullable(orders.get(orderId));
    }

    @Override
    public List<Order> findByCustomerId(String customerId) {
        return orders.values().stream()
                .filter(order -> order.customerId().equals(customerId))
                .sorted(Comparator.comparing(Order::id))
                .toList();
    }

    @Override
    public Page<Order> findPageByCustomerId(String customerId, Pageable pageable) {
        List<Order> matchingOrders = findByCustomerId(customerId);
        int fromIndex = Math.min((int) pageable.getOffset(), matchingOrders.size());
        int toIndex = Math.min(fromIndex + pageable.getPageSize(), matchingOrders.size());

        return new PageImpl<>(
                matchingOrders.subList(fromIndex, toIndex),
                pageable,
                matchingOrders.size()
        );
    }
}
