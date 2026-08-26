package org.example.lesson24;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryEntity, String> {
    // DONE 1: 使用 @EntityGraph，让这个查询同时加载 products。
    @EntityGraph(attributePaths = "products")
    List<CategoryEntity> findAllByOrderByIdAsc();
}
