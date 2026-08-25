# Lesson 22: Spring Data JPA

> 状态：进行中

上一课直接使用 `JdbcTemplate` 编写 SQL。本课改用 Spring Data JPA：通过 Entity 描述数据库表，通过 Repository 提供常用查询，并体验按照方法名生成查询。

## 学习目标

- 理解 `@Entity`、`@Table` 和 `@Id` 如何把 Java 类映射到数据库表。
- 使用 `JpaRepository<Entity, ID类型>` 完成基础查询。
- 使用方法名派生查询，不手写简单的筛选 SQL。
- 继续观察数据库行、Entity 和 JSON 之间的值与类型流动。

## 运行入口

打开 `src/main/java/org/example/lesson22/Lesson22Application.java`，运行 `main` 方法。

终端默认会启动当前课程：

```shell
mvn spring-boot:run
```

如果 `8080` 已被占用，可以添加程序参数：

```text
--server.port=8087
```

本课继续使用上一课的 `schema.sql` 和 `data.sql`，启动时会得到初始商品 `P100`。

## 先阅读 Entity

`ProductEntity` 映射到数据库的 `products` 表：

```text
ProductEntity.id    String     <-> products.id    VARCHAR
ProductEntity.name  String     <-> products.name  VARCHAR
ProductEntity.price BigDecimal <-> products.price DECIMAL
```

- `@Entity`：告诉 JPA 这个类由数据库管理。
- `@Table(name = "products")`：指定对应的表名。
- `@Id`：指定主键属性。
- 无参构造器供 JPA 创建对象使用，普通构造器方便业务代码创建对象。

本课暂时直接把 Entity 返回给 API。下一课会增加 DTO，把数据库模型和接口模型隔离开。

## 任务

1. `ProductJpaRepository`：声明 `findByNameContainingIgnoreCaseOrderByIdAsc(String keyword)`，返回 `List<ProductEntity>`。
2. `ProductJpaController.findAll`：调用 `repository.findAll(Sort.by("id"))`，返回按 ID 排序的全部商品。
3. `ProductJpaController.search`：把 `keyword` 传给任务 1 的派生查询方法并返回结果。

初始骨架可以编译和启动，但任务 2、3 完成前，对应接口会返回空数组。

## 完成后的验证

查询所有商品：

```http
GET /api/jpa-products
```

预期结果：

```text
HTTP 200
[{"id":"P100","name":"Keyboard","price":299.90}]
```

按 ID 查询已有商品：

```http
GET /api/jpa-products/P100
```

预期结果：

```text
HTTP 200
{"id":"P100","name":"Keyboard","price":299.90}
```

按名称片段搜索，忽略大小写：

```http
GET /api/jpa-products/search?keyword=key
```

预期结果：

```text
HTTP 200
[{"id":"P100","name":"Keyboard","price":299.90}]
```

搜索不存在的名称：

```http
GET /api/jpa-products/search?keyword=mouse
```

预期结果：

```text
HTTP 200
[]
```

不存在的 ID 应返回：

```text
GET /api/jpa-products/P999
HTTP 404
```

## 方法名如何变成查询

JPA 会拆解这个方法名：

```text
findBy Name Containing IgnoreCase OrderBy Id Asc
       |    |          |           |  |
       属性 包含字符串   忽略大小写    属性 升序
```

它表达的 SQL 意图近似于：

```sql
SELECT id, name, price
FROM products
WHERE LOWER(name) LIKE LOWER('%' || ? || '%')
ORDER BY id ASC
```

这里不需要自己编写 SQL；方法名就是查询规则的一部分。

## TypeScript 对照

可以把 `JpaRepository<ProductEntity, String>` 理解为一个已经提供常用方法的数据访问对象：

```typescript
interface ProductRepository {
  findAll(sort: Sort): Promise<ProductEntity[]>;
  findById(id: string): Promise<ProductEntity | undefined>;
  findByNameContainingIgnoreCaseOrderByIdAsc(
    keyword: string,
  ): Promise<ProductEntity[]>;
}
```

Java 中第二个泛型参数 `String` 表示 `ProductEntity` 的主键类型是 `String`。

## 提示

- 派生查询方法只需要在 Repository 接口中声明，不需要写方法体。
- 方法名必须与任务完全一致，JPA 会根据名字解析查询规则。
- `repository.findAll(...)` 返回 `List<ProductEntity>`，不需要再复制到新列表。
- `ResponseEntity.of(repository.findById(id))` 会把有值的 `Optional` 变成 `200`，空值变成 `404`。
