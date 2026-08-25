package org.example.lesson23;

import java.math.BigDecimal;

public record ProductResponse(String id, String name, BigDecimal price) {
    public static ProductResponse from(ProductEntity entity) {
        // TODO 1: 把 Entity 的 id、name、price 转换成 ProductResponse。
        return new ProductResponse("TODO", "TODO", BigDecimal.ZERO);
    }
}
