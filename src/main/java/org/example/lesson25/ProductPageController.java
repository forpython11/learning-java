package org.example.lesson25;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/paged-products")
public class ProductPageController {
    private final ProductRepository repository;

    public ProductPageController(ProductRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ProductPageResponse findAll(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "2") @Min(1) @Max(50) int size,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction
    ) {
        // DONE 3: 创建 Pageable，调用 Repository，并把 Page 转换成响应 DTO。
        PageRequest pageable = PageRequest.of(page, size, Sort.by(direction, "id"));
        Page<ProductEntity> result = repository.findByNameContainingIgnoreCase(keyword, pageable);
        return ProductPageResponse.from(result);
    }
}
