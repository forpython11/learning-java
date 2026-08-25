# Lesson 21: SQL 与 H2 入门

> 状态：已完成（2026-08-25）

前面的 Repository 使用 `Map` 保存数据。本课把数据放进 H2 内存数据库，学习表、主键、初始化脚本和参数化 SQL，并继续通过 REST API 观察数据变化。

## 学习目标

- 区分 `schema.sql` 的表结构和 `data.sql` 的初始数据。
- 使用 `SELECT` 和 `INSERT` 完成查询与新增。
- 阅读已经提供的 `UPDATE` 和 `DELETE` 示例。
- 理解 SQL 的 `?` 是参数位置，不是字符串拼接。

## 运行入口

打开 `src/main/java/org/example/lesson21/Lesson21Application.java`，运行 `main` 方法。

终端默认会启动当前课程：

```shell
mvn spring-boot:run
```

如果 `8080` 已被占用，可以添加程序参数：

```text
--server.port=8086
```

H2 是内存数据库。每次停止应用后数据都会消失，下次启动时 Spring 会重新执行 `schema.sql` 和 `data.sql`。

## 任务

1. `data.sql`：用 `INSERT` 初始化商品 `P100`，名称为 `Keyboard`，价格为 `299.90`。
2. `ProductRepository.findAll`：使用 `jdbcTemplate.query(...)` 执行 `SELECT id, name, price FROM products ORDER BY id`，并传入已有的 `PRODUCT_ROW_MAPPER`。
3. `ProductRepository.insert`：使用 `jdbcTemplate.update(...)` 执行 `INSERT INTO products (id, name, price) VALUES (?, ?, ?)`，三个参数依次为商品的 `id`、`name`、`price`。

初始骨架可以编译和启动，但在完成任务 2 之前，GET 接口会返回空数组；完成任务 3 之前，POST 接口会返回 `500`。这是本课用来提示占位实现尚未完成的预期现象。

## 完成后的验证

启动后查询初始数据：

```http
GET /api/db-products
```

预期结果：

```text
HTTP 200
[{"id":"P100","name":"Keyboard","price":299.90}]
```

新增商品：

```http
POST /api/db-products
Content-Type: application/json

{"id":"P200","name":"Mouse","price":99.90}
```

预期结果：

```text
HTTP 201
{"id":"P200","name":"Mouse","price":99.90}
```

再次查询时应按 ID 排序返回两条数据：

```text
HTTP 200
[{"id":"P100","name":"Keyboard","price":299.90},{"id":"P200","name":"Mouse","price":99.90}]
```

## UPDATE 与 DELETE 示例

Repository 已提供更新名称和删除商品的 SQL，完成三个 TODO 后可以直接验证。

更新名称：

```http
PUT /api/db-products/P200?name=Wireless%20Mouse
```

预期状态码：

```text
HTTP 204
```

删除商品：

```http
DELETE /api/db-products/P200
```

预期状态码：

```text
HTTP 204
```

不存在的 ID 会得到 `404`。`jdbcTemplate.update(...)` 返回受影响的行数：`1` 表示操作了一行，`0` 表示没有找到对应行。

## 表结构

`schema.sql` 中的语句属于 DDL，用来定义数据结构：

```sql
CREATE TABLE products (
    id VARCHAR(20) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10, 2) NOT NULL
);
```

- `PRIMARY KEY`：每个商品 ID 必须唯一且不能为空。
- `VARCHAR(100)`：最多保存 100 个字符的文本。
- `DECIMAL(10, 2)`：适合保存两位小数的金额。
- `NOT NULL`：这一列必须有值。

## 值和类型的流动

查询一行数据时：

```text
SQL 结果行
    -> ResultSet
    -> PRODUCT_ROW_MAPPER
    -> Product
    -> List<Product>
    -> JSON 数组
```

新增时：

```text
Product.id()       String     -> 第一个 ?
Product.name()     String     -> 第二个 ?
Product.price()    BigDecimal -> 第三个 ?
```

## TypeScript 对照

Node.js SQL 客户端也会把 SQL 和参数分开传递：

```typescript
await database.execute(
  "INSERT INTO products (id, name, price) VALUES (?, ?, ?)",
  [product.id, product.name, product.price],
);
```

Java 中的 `JdbcTemplate` 扮演类似角色。使用参数占位符可以正确处理数据类型，也能避免把用户输入直接拼接进 SQL。

## 提示

- SQL 字符串写在双引号中，末尾不需要在字符串内部添加分号。
- `query` 返回 `List<Product>`，`update` 返回受影响行数 `int`。
- `PRODUCT_ROW_MAPPER` 已经负责把一行转换为 `Product`，不需要重新编写。
- `INSERT` 中 `?` 的数量和后面的参数数量必须一致，顺序也必须一致。
