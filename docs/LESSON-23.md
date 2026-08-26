# Lesson 23: Entity 与 DTO 转换

> 状态：已完成（2026-08-26）

上一课直接把 `ProductEntity` 返回给前端。本课增加 `ProductResponse` DTO，把数据库模型转换成稳定的 API 响应模型，避免数据库字段变化直接影响接口。

## 学习目标

- 区分 Entity 和 DTO 的职责。
- 使用 `record` 表达只读响应数据。
- 使用静态工厂方法把 `ProductEntity` 转换为 `ProductResponse`。
- 使用 Stream 和 Optional 在查询结果上继续完成类型转换。

## 运行入口

打开 `src/main/java/org/example/lesson23/Lesson23Application.java`，运行 `main` 方法。

终端默认会启动当前课程：

```shell
mvn spring-boot:run
```

如果 `8080` 已被占用，可以添加程序参数：

```text
--server.port=8088
```

本课继续使用 H2、`schema.sql` 和 `data.sql`，启动时会得到初始商品 `P100`。

## Entity 和 DTO

`ProductEntity` 属于数据库层：

```text
数据库 products 表
        <-> ProductEntity
```

`ProductResponse` 属于 API 层：

```text
ProductResponse
        -> JSON 响应
```

完整的数据流是：

```text
数据库行
    -> ProductEntity
    -> ProductResponse
    -> JSON
```

目前两个类型的字段相同，但它们的职责不同。以后即使 Entity 增加内部字段，例如成本价或删除标记，也不必自动暴露给前端。

## 任务

1. `ProductResponse.from`：使用 Entity 的三个 getter 创建并返回 `ProductResponse`。
2. `ProductDtoController.findAll`：按 ID 查询全部 Entity，使用 `stream()`、`map(ProductResponse::from)` 和 `toList()` 转换成 DTO 列表。
3. `ProductDtoController.findById`：在 `Optional<ProductEntity>` 上调用 `map(ProductResponse::from)`，再转换成存在时 `200`、不存在时 `404` 的响应。

初始骨架可以编译和启动。完成任务前，列表接口返回空数组，按 ID 接口返回 `404`；`ProductResponse.from` 中的占位值也不是正确答案。

## 完成后的验证

查询所有商品：

```http
GET /api/dto-products
```

预期结果：

```text
HTTP 200
[{"id":"P100","name":"Keyboard","price":299.90}]
```

按 ID 查询已有商品：

```http
GET /api/dto-products/P100
```

预期结果：

```text
HTTP 200
{"id":"P100","name":"Keyboard","price":299.90}
```

查询不存在的商品：

```text
GET /api/dto-products/P999
HTTP 404
```

## 类型怎样变化

列表查询：

```text
repository.findAll(...)          List<ProductEntity>
    -> stream()                  Stream<ProductEntity>
    -> map(ProductResponse::from) Stream<ProductResponse>
    -> toList()                  List<ProductResponse>
```

单个查询：

```text
repository.findById(id)          Optional<ProductEntity>
    -> map(ProductResponse::from) Optional<ProductResponse>
    -> map(ResponseEntity::ok)    Optional<ResponseEntity<ProductResponse>>
    -> orElseGet(...)             ResponseEntity<ProductResponse>
```

方法引用：

```java
ProductResponse::from
```

可以先展开成等价 Lambda 理解：

```java
entity -> ProductResponse.from(entity)
```

## TypeScript 对照

在 TypeScript 后端中，也经常把数据库对象映射成响应对象：

```typescript
type ProductRow = {
  id: string;
  name: string;
  price: Decimal;
  internalFlag: boolean;
};

type ProductResponse = {
  id: string;
  name: string;
  price: Decimal;
};

function toResponse(entity: ProductRow): ProductResponse {
  return {
    id: entity.id,
    name: entity.name,
    price: entity.price,
  };
}
```

Java 的 `ProductResponse.from(entity)` 扮演同样的映射角色。

## 提示

- `ProductEntity` 使用 `getId()`、`getName()`、`getPrice()`；它不是 `record`，不能写成 `id()`。
- `ProductResponse` 是 `record`，构造时三个参数的顺序必须与组件声明一致。
- 列表转换先确认每一步的类型，不要把一个 `ProductEntity` 直接返回成 `ProductResponse`。
- 方法引用不熟悉时，先写成 Lambda，理解后再改回方法引用。
- `ResponseEntity.notFound().build()` 用于构造 `404` 响应。
