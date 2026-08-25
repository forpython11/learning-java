package org.example.lesson19;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 表示这个类负责接收 HTTP 请求，方法返回值会被 Spring 转换为 JSON 响应体。
@RestController
// 当前 Controller 中所有接口的公共路径前缀。
@RequestMapping("/api/products")
public class ProductController {
    // Controller 只负责 HTTP 层，把具体业务交给 ProductService。
    private final ProductService service;

    // 构造器注入：Spring 创建 Controller 时会自动传入 ProductService 对象。
    public ProductController(ProductService service) {
        this.service = service;
    }

    // 把 GET /api/products/{id} 请求映射到这个方法。
    @GetMapping("/{id}")
    // @PathVariable 把 URL 中的 {id} 取出，并作为 String 参数传入。
    public Product findById(@PathVariable String id) {
        // 查询成功时 Product 会被转换为 JSON；异常会继续交给全局异常处理器。
        return service.getById(id);
    }
}
