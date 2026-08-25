package org.example.lesson22;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductJpaRepository extends JpaRepository<ProductEntity, String> {
    // TODO 1: 声明按名称片段搜索、忽略大小写并按 ID 升序排列的派生查询方法。
}
