# Lesson 18: 分层架构与构造器注入

> 状态：已完成（2026-08-25）

Controller 不应该同时负责 HTTP、业务规则和数据存取。本课把商品查询拆成 Controller、Service、Repository 三层，并让 Spring 负责创建对象和注入依赖。

## 运行入口

打开 `src/main/java/org/example/lesson18/Lesson18Application.java`，运行 `main` 方法。

终端默认会启动当前课程：

```shell
mvn spring-boot:run
```

如果 `8080` 已被占用，可以添加：

```text
--server.port=8083
```

## 任务

1. `ProductRepository.findById`：从 `products` Map 取值，并用 `Optional.ofNullable(...)` 包装。
2. `ProductService.findById`：把查询委托给 `repository.findById(id)`。
3. `ProductController.findById`：有商品时返回 `ResponseEntity.ok(product)`，没有时返回 `404`。

## 预期结果

请求存在的商品：

```text
GET /api/catalog/P100
```

响应：

```text
HTTP 200
{"id":"P100","name":"Keyboard"}
```

请求不存在的商品：

```text
GET /api/catalog/P999
```

响应：

```text
HTTP 404
```

## 三层职责

```text
HTTP request
    -> ProductController   读取路径参数，决定 HTTP 响应
    -> ProductService      组织业务流程
    -> ProductRepository   读取数据
    -> Map                 本课的内存数据源
```

- `@RestController`：HTTP 接口层。
- `@Service`：业务逻辑层。
- `@Repository`：数据访问层。

Spring 启动时会找到这些注解，创建对应对象，并根据构造器参数把它们连接起来。

## 构造器注入

`ProductService` 没有自己执行 `new ProductRepository()`：

```java
public ProductService(ProductRepository repository) {
    this.repository = repository;
}
```

Spring 创建 `ProductService` 时会把已经创建的 `ProductRepository` 传进来。这叫构造器注入。

## TypeScript 对照

可以把它理解为前端/Node.js 中显式传入依赖：

```typescript
const repository = new ProductRepository();
const service = new ProductService(repository);
const controller = new ProductController(service);
```

区别是 Spring 容器根据注解和构造器自动完成这些 `new` 和连接操作。

## Optional 响应转换

Controller 可以把 `Optional<Product>` 转成 HTTP 响应：

```java
optional
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
```

`map` 在有商品时运行，`orElseGet` 在没有商品时运行。

## 提示

- Repository：`Optional.ofNullable(products.get(id))`。
- Service：直接 `return repository.findById(id)`。
- Controller：复用上面的 `map` 和 `orElseGet`。
