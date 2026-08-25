package org.example.lesson23;

import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dto-products")
public class ProductDtoController {
    private final ProductRepository repository;

    public ProductDtoController(ProductRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<ProductResponse> findAll() {
        // TODO 2: 查询按 ID 排序的 Entity，并把每一项转换成 ProductResponse。
        return List.of();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable String id) {
        // TODO 3: 把 Optional<ProductEntity> 转换成 DTO 响应，不存在时返回 404。
        return ResponseEntity.notFound().build();
    }
}
