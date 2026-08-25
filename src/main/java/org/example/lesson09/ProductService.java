package org.example.lesson09;

public class ProductService {
    public ProductResponse create(CreateProductRequest request) {
        // DONE 3: 使用固定 ID "P001" 和请求数据创建 ProductResponse。
        return new ProductResponse("P001", request.name(), request.price());
    }
}
