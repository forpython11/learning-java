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
        // DONE 2: 转换当前页内容，并从 Page 中读取分页信息。
        List<ProductResponse> content = result.getContent().stream().map(ProductResponse::from).toList();
        return new ProductPageResponse(content, result.getNumber(), result.getSize(), result.getTotalElements(), result.getTotalPages());
    }

    public static ProductPageResponse empty(int page, int size) {
        return new ProductPageResponse(List.of(), page, size, 0, 0);
    }
}
