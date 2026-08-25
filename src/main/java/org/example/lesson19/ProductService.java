package org.example.lesson19;

import org.springframework.stereotype.Service;

// 表示这是业务逻辑层组件，Spring 启动时会创建并管理它的对象。
@Service
public class ProductService {
    // Service 通过 Repository 查询数据，而不直接关心数据存在哪里。
    private final ProductRepository repository;

    // 构造器注入：Spring 创建 Service 时会自动传入 ProductRepository 对象。
    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    // 对外提供“根据 ID 获取商品”的业务操作。
    public Product getById(String id) {
        // DONE 1: 商品不存在时抛出 ProductNotFoundException。
        // findById 的结果类型是 Optional<Product>：有值就返回 Product，没有值就执行 orElseThrow。
        return repository.findById(id)
                .orElseThrow(() ->
                        // Lambda 只在商品不存在时执行，用当前 id 创建异常信息。
                        new ProductNotFoundException("Product not found: " + id)
                );
    }
}
