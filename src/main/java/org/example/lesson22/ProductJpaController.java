package org.example.lesson22;

import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/jpa-products")
public class ProductJpaController {
    private final ProductJpaRepository repository;

    public ProductJpaController(ProductJpaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<ProductEntity> findAll() {
        // DONE 2: 使用 Sort.by("id") 查询并返回按 ID 排序的全部商品。
        return repository.findAll(Sort.by("id"));
    }

    @GetMapping("/search")
    public List<ProductEntity> search(@RequestParam String keyword) {
        // DONE 3: 调用 Repository 中的派生查询方法。
        return repository.findByNameContainingIgnoreCaseOrderByIdAsc(keyword);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductEntity> findById(@PathVariable String id) {
        return ResponseEntity.of(repository.findById(id));
    }
}
