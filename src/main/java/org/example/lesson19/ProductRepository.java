package org.example.lesson19;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

// 表示这是数据访问层组件，Spring 启动时会创建并管理它的对象。
@Repository
public class ProductRepository {
    // 用不可变 Map 模拟数据库：key 是商品 ID，value 是 Product 对象。
    private final Map<String, Product> products = Map.of(
            "P100", new Product("P100", "Keyboard")
    );

    // Optional<Product> 明确表示查询结果可能有商品，也可能为空。
    public Optional<Product> findById(String id) {
        // products.get(id) 找不到时返回 null；ofNullable 把结果包装成 Optional。
        return Optional.ofNullable(products.get(id));
    }
}
