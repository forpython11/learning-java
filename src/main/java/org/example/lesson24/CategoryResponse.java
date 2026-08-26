package org.example.lesson24;

import java.util.List;

public record CategoryResponse(String id, String name, List<ProductSummary> products) {
    public static CategoryResponse from(CategoryEntity entity) {
        // TODO 2: 转换分类字段，并把每个 ProductEntity 转换成 ProductSummary。
        return new CategoryResponse("TODO", "TODO", List.of());
    }
}
