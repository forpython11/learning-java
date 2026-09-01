package org.example.lesson33;

import org.springframework.data.domain.Page;

import java.util.List;

public record OrderPageResponse(
        List<OrderResponse> content,
        int page,
        int size,
        long totalElements,
        int totalPages
) {
    public static OrderPageResponse from(Page<Order> result) {
        // TODO 3: 转换当前页的订单，并填写真实分页信息。
        return new OrderPageResponse(List.of(), 0, 0, 0, 0);
    }
}
