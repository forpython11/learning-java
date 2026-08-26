package org.example.lesson25;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, String> {
    // TODO 1: 声明按名称片段忽略大小写查询，并接收分页参数的派生查询方法。
}
