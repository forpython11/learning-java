# Lesson 25: 分页、排序与筛选

> 状态：已完成（2026-08-26）

商品越来越多以后，接口不应该一次返回全部数据。本课将使用 Spring Data JPA 的 `Pageable` 和 `Page<T>`，实现前端列表页常见的关键字筛选、分页与排序。

## 学习目标

- 理解页码、每页数量、排序方向如何组成 `Pageable`。
- 使用派生查询方法同时处理名称筛选和分页。
- 区分 `List<T>` 与 `Page<T>`。
- 把数据库分页结果转换为稳定的 API 响应 DTO。

## 运行入口

运行 `src/main/java/org/example/lesson25/Lesson25Application.java` 中的 `main` 方法，或在终端执行：

```shell
mvn spring-boot:run
```

如果 `8080` 已占用，可以添加程序参数：

```text
--server.port=8090
```

本课入口会自动启用 `lesson25` Profile，在原有 `P100` 基础上额外初始化 `P200`、`P300`、`P400`。这些额外数据只用于本课，不会改变前面课程的预期结果。

## 请求参数

```http
GET /api/paged-products?keyword=&page=0&size=2&direction=ASC
```

| 参数 | 类型 | 默认值 | 含义 |
| --- | --- | --- | --- |
| `keyword` | `String` | 空字符串 | 名称中包含的文字，忽略大小写 |
| `page` | `int` | `0` | 页码，从 0 开始 |
| `size` | `int` | `2` | 每页条数，范围 1 到 50 |
| `direction` | `Sort.Direction` | `ASC` | 按 ID 升序或降序 |

注意：`page=0` 是第一页，这与很多前端分页组件显示的“第 1 页”不同。前端提交参数前可能需要减 1。

## List 与 Page

`List<ProductEntity>` 只有查询到的元素；`Page<ProductEntity>` 除了当前页内容，还携带总数信息：

```text
Page<ProductEntity>
├── getContent()        当前页的 List<ProductEntity>
├── getNumber()         当前页码
├── getSize()           每页条数
├── getTotalElements()  所有匹配记录数
└── getTotalPages()     总页数
```

前端有了这些信息，才能正确显示总条数和分页按钮。

## 任务

1. `ProductRepository`：声明 `findByNameContainingIgnoreCase` 派生查询方法，参数为 `String keyword` 和 `Pageable pageable`，返回 `Page<ProductEntity>`。
2. `ProductPageResponse.from`：转换 `result.getContent()` 中的商品，并读取当前页码、每页数量、总记录数和总页数。
3. `ProductPageController.findAll`：使用 `PageRequest.of` 创建分页和按 ID 排序的参数，调用 Repository，再转换响应 DTO。

初始骨架可以编译和启动，但接口暂时返回空的分页响应。请只修改三个 `TODO`。

## 完成后的验证

第一页、每页两条、按 ID 升序：

```http
GET /api/paged-products
```

```json
{"content":[{"id":"P100","name":"Keyboard","price":299.90},{"id":"P200","name":"Mouse","price":99.90}],"page":0,"size":2,"totalElements":4,"totalPages":2}
```

第二页：

```http
GET /api/paged-products?page=1&size=2
```

```json
{"content":[{"id":"P300","name":"Monitor","price":1299.00},{"id":"P400","name":"USB Cable","price":39.90}],"page":1,"size":2,"totalElements":4,"totalPages":2}
```

按 ID 降序：

```http
GET /api/paged-products?direction=DESC
```

第一页应依次包含 `P400`、`P300`。

名称筛选：

```http
GET /api/paged-products?keyword=mouse
```

应只返回 `P200 / Mouse`，`totalElements` 为 `1`，`totalPages` 为 `1`。`page=-1` 或 `size=0` 应返回 HTTP `400`。

## 值和类型的流动

```text
page + size + direction
    -> PageRequest.of(...)                         Pageable
keyword + pageable
    -> repository.findByNameContainingIgnoreCase  Page<ProductEntity>
result.getContent().stream()
    -> map(ProductResponse::from)                  List<ProductResponse>
ProductPageResponse.from(result)
    -> Controller                                  ProductPageResponse
```

先看最右侧方法的返回类型，再决定左边变量应该声明成什么类型。不要把 `Page<ProductEntity>` 写成 `List<ProductEntity>`，因为两者携带的信息不同。

## TypeScript 对照

TypeScript 后端通常要分别查询当前页和总数：

```typescript
const [content, totalElements] = await Promise.all([
  repository.find({
    where: { name: contains(keyword) },
    skip: page * size,
    take: size,
    order: { id: direction },
  }),
  repository.count({ where: { name: contains(keyword) } }),
]);

return {
  content,
  page,
  size,
  totalElements,
  totalPages: Math.ceil(totalElements / size),
};
```

Spring Data 的 `Page<T>` 把当前页结果和计数信息封装在同一个对象中。

## 提示

- TODO 1 的完整返回类型是 `Page<ProductEntity>`，第二个参数类型是 `Pageable`。
- TODO 2 可以先把 `result.getContent()` 转换为 `List<ProductResponse>` 局部变量。
- `PageRequest.of(page, size, Sort.by(direction, "id"))` 会创建一个 `Pageable`。
- 排序字段 `"id"` 是 Java Entity 的属性名。
- TODO 3 中 Repository 返回的是 `Page<ProductEntity>`，不是 `List<ProductEntity>`。
