# Lesson 09: 使用 record 构建不可变 DTO

> 状态：已完成（2026-08-25）

`record` 适合表达只保存数据的不可变对象。Java 会自动生成构造方法、字段访问方法、`equals`、`hashCode` 和 `toString`。

## 运行入口

打开 `src/main/java/org/example/lesson09/Main.java`，完成三个 `TODO` 后运行 `main` 方法。

## 任务

1. 在 `CreateProductRequest` 的紧凑构造方法中拒绝空白商品名。
2. 在同一个构造方法中拒绝小于或等于 `0` 的价格。
3. 在 `ProductService.create` 中使用请求数据创建并返回 `ProductResponse`。

完成后的预期输出：

```text
Created: P001 - Keyboard - 299.0
Rejected: Price must be greater than 0
```

## TypeScript 与 Java 对照

TypeScript：

```typescript
type CreateProductRequest = Readonly<{
  name: string;
  price: number;
}>;
```

Java：

```java
public record CreateProductRequest(String name, double price) {
}
```

普通 Java 类通常使用 `request.getName()`，record 自动生成的访问方法不带 `get`：

```java
request.name();
request.price();
```

## 紧凑构造方法

record 可以在紧凑构造方法中校验参数：

```java
public CreateProductRequest {
    // 在这里校验 name 和 price
}
```

参数通过校验后，Java 会自动把它们赋值给 record 的字段，不需要手写 `this.name = name`。

## 提示

- 空白字符串判断：`name == null || name.isBlank()`。
- 价格判断：`price <= 0`。
- record 访问数据：`request.name()` 和 `request.price()`。
- 创建响应：`new ProductResponse("P001", request.name(), request.price())`。
