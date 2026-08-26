package org.example.lesson23;

import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/dto-products")
public class ProductDtoController {
    private final ProductRepository repository;

    public ProductDtoController(ProductRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<ProductResponse> findAll() {
        // DONE 2: 查询按 ID 排序的 Entity，并把每一项转换成 ProductResponse。
        return repository.findAll(Sort.by("id"))
                .stream()
                .map(ProductResponse::from)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable String id) {
        // DONE 3: 把 Optional<ProductEntity> 转换成 DTO 响应，不存在时返回 404。
        // 查询 Entity，可能有值，也可能为空
        Optional<ProductEntity> optionalEntity =
                repository.findById(id);

        // 如果有 Entity，就转换成 ProductResponse
        Optional<ProductResponse> optionalResponse =
                optionalEntity.map(entity -> ProductResponse.from(entity));

        // 有 DTO 返回 200，没有则返回 404
        return optionalResponse
                .map(response -> ResponseEntity.ok(response))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
