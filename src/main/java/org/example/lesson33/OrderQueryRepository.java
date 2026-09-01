package org.example.lesson33;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface OrderQueryRepository {
    Optional<Order> findById(String orderId);

    List<Order> findByCustomerId(String customerId);

    Page<Order> findPageByCustomerId(String customerId, Pageable pageable);
}
