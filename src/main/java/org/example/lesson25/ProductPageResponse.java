package org.example.lesson25;

import org.springframework.data.domain.Page;

import java.util.List;

public record ProductPageResponse(
        List<ProductResponse> content,
        int page,
        int size,
        long totalElements,
        int totalPages
) {
    public static ProductPageResponse from(Page<ProductEntity> result) {
        // TODO 2: 转换当前页内容，并从 Page 中读取分页信息。
        return new ProductPageResponse(List.of(), 0, 0, 0, 0);
    }

    public static ProductPageResponse empty(int page, int size) {
        return new ProductPageResponse(List.of(), page, size, 0, 0);
    }
}
