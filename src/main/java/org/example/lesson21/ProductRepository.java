package org.example.lesson21;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepository {
    private static final RowMapper<Product> PRODUCT_ROW_MAPPER = (resultSet, rowNumber) ->
            new Product(
                    resultSet.getString("id"),
                    resultSet.getString("name"),
                    resultSet.getBigDecimal("price")
            );

    private final JdbcTemplate jdbcTemplate;

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Product> findAll() {
        // TODO 2: 执行 SELECT，并使用 PRODUCT_ROW_MAPPER 转换每一行。
        return List.of();
    }

    public int insert(Product product) {
        // TODO 3: 使用三个 ? 参数新增商品，不要拼接 SQL 字符串。
        return 0;
    }

    public int rename(String id, String name) {
        return jdbcTemplate.update(
                "UPDATE products SET name = ? WHERE id = ?",
                name,
                id
        );
    }

    public int deleteById(String id) {
        return jdbcTemplate.update(
                "DELETE FROM products WHERE id = ?",
                id
        );
    }
}
