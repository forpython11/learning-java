package org.example.lesson24;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryRepository repository;

    public CategoryController(CategoryRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<CategoryResponse> findAll() {
        // DONE 3: 查询分类，并把每个 CategoryEntity 转换成 CategoryResponse。
        List<CategoryEntity> categories = repository.findAllByOrderByIdAsc();
        return categories.stream()
                .map(CategoryResponse::from)
                .toList();
    }
}
