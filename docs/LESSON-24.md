# Lesson 24: JPA 数据关系

> 状态：进行中

前面的商品是独立数据。本课增加商品分类：一个分类包含多个商品，每个商品属于一个分类，并继续使用 DTO 控制返回给前端的嵌套 JSON。

## 学习目标

- 理解一对多和多对一关系的两个方向。
- 阅读 `@OneToMany`、`@ManyToOne`、`mappedBy` 和 `cascade`。
- 使用 `@EntityGraph` 一次加载接口需要的关联数据。
- 把关联 Entity 转换成不会循环引用的嵌套 DTO。

## 运行入口

打开 `src/main/java/org/example/lesson24/Lesson24Application.java`，运行 `main` 方法。

终端默认会启动当前课程：

```shell
mvn spring-boot:run
```

如果 `8080` 已被占用，可以添加程序参数：

```text
--server.port=8089
```

启动后，H2 中会有分类 `C100 / Peripherals`，其中包含商品 `P100 / Keyboard`。

## 关系方向

数据库使用 `products.category_id` 外键记录关系：

```text
categories                         products
┌───────────────┐                  ┌─────────────────────┐
│ id: C100      │ 1              n │ id: P100            │
│ Peripherals   │ <─────────────── │ category_id: C100    │
└───────────────┘                  └─────────────────────┘
```

Java Entity 中有两个方向：

```text
CategoryEntity.products  List<ProductEntity>  @OneToMany
ProductEntity.category   CategoryEntity        @ManyToOne
```

`mappedBy = "category"` 表示关系由 `ProductEntity.category` 对应的外键维护。`cascade = CascadeType.ALL` 表示保存或删除分类时，相关操作也会传播到它管理的商品；真实项目中要根据业务规则谨慎选择级联范围。

## 为什么还需要 DTO

如果直接返回双向关联的 Entity，JSON 序列化可能沿着下面的路径不断往返：

```text
Category -> products -> category -> products -> ...
```

本课使用单向的响应结构：

```text
CategoryResponse
    -> List<ProductSummary>
```

`ProductSummary` 不再包含分类，因此 JSON 不会循环。

## 任务

1. `CategoryRepository`：给已有查询方法添加 `@EntityGraph(attributePaths = "products")`，让查询分类时同时加载商品集合。
2. `CategoryResponse.from`：把分类的 `id`、`name` 和每个商品转换成嵌套 DTO。
3. `CategoryController.findAll`：调用 Repository 查询方法，并把每个 `CategoryEntity` 转换成 `CategoryResponse`。

初始骨架可以编译和启动，但接口暂时返回空数组；`CategoryResponse.from` 中也仍是占位结果。

## 完成后的验证

查询分类和商品：

```http
GET /api/categories
```

预期结果：

```text
HTTP 200
[{"id":"C100","name":"Peripherals","products":[{"id":"P100","name":"Keyboard","price":299.90}]}]
```

响应中的商品不包含 `category` 字段，这证明 API 返回的是 DTO，而不是直接序列化双向关联的 Entity。

## EntityGraph 做什么

`products` 使用懒加载，普通分类查询可能先查分类，再为每个分类分别查询商品，形成 N+1 查询问题：

```text
1 次查询 categories
N 次查询每个分类的 products
```

`@EntityGraph(attributePaths = "products")` 告诉 JPA，本次查询需要一起加载 `products`。它不改变字段的全局加载策略，只影响标注的 Repository 方法。

## 值和类型的流动

```text
repository.findAllByOrderByIdAsc()  List<CategoryEntity>
    -> category.getProducts()       List<ProductEntity>
    -> ProductSummary.from(...)     ProductSummary
    -> CategoryResponse.from(...)   CategoryResponse
    -> Controller                   List<CategoryResponse>
```

## TypeScript 对照

在 TypeScript ORM 中也会显式声明要加载的关联，并映射响应：

```typescript
const categories = await categoryRepository.find({
  relations: { products: true },
  order: { id: "ASC" },
});

return categories.map(category => ({
  id: category.id,
  name: category.name,
  products: category.products.map(product => ({
    id: product.id,
    name: product.name,
    price: product.price,
  })),
}));
```

`relations: { products: true }` 与本课的 `@EntityGraph` 作用相近。

## 提示

- TODO 1 添加的是方法注解，不需要写 SQL。
- `attributePaths` 中写的是 Java 属性名 `products`，不是数据库表名。
- TODO 2 可以先得到 `List<ProductSummary>` 局部变量，再构造 `CategoryResponse`。
- TODO 3 与上一课的列表转换结构相同，只是输入和输出类型变成了分类。
- 方法引用不清楚时，先展开成 `entity -> CategoryResponse.from(entity)`。
