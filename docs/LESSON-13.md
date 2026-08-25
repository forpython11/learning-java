# Lesson 13: 使用 Jackson 处理 JSON

> 状态：已完成（2026-08-25）

前后端通过 HTTP 交换数据时通常使用 JSON。本课使用 Jackson 在 Java 对象和 JSON 字符串之间转换，并练习 Java 字段名与 JSON 字段名不一致的情况。

## 运行入口

打开 `src/main/java/org/example/lesson13/Main.java`，完成三个 `TODO` 后运行 `main` 方法。

## 任务

1. 在 `Product` 的 `name` 组件前添加 `@JsonProperty("product_name")`。
2. 在 `toJson` 中使用 `objectMapper.writeValueAsString(product)` 将对象序列化为 JSON。
3. 在 `fromJson` 中使用 `objectMapper.readValue(json, Product.class)` 将 JSON 反序列化为对象。

完成后的预期输出：

```text
JSON: {"id":"P100","product_name":"Keyboard","price":299.90}
Restored: P101 - Mouse - 159.50
```

## 数据流

```text
Product 对象 --writeValueAsString--> JSON 字符串
JSON 字符串 --readValue------------> Product 对象
```

`Product.class` 是 Java 的类对象，用来告诉 Jackson 最终需要创建哪种类型。可以把它理解为 TypeScript 中运行时解析函数所需要的 schema 信息，但 Java 在这里直接传入类。

## TypeScript 对照

```typescript
const json = JSON.stringify(product);
const restored = JSON.parse(json) as Product;
```

Java 对应使用：

```java
objectMapper.writeValueAsString(product);
objectMapper.readValue(json, Product.class);
```

Java 的反序列化会根据 `Product.class` 创建具体类型，不只是得到一个普通的键值对象。

## 关于异常

Jackson 转换失败时会抛出 `JsonProcessingException`。本课的方法已经声明 `throws JsonProcessingException`，你只需要关注转换本身。

## 提示

- 注解写在 `String name` 前面。
- 序列化方法只需要一条 `return`。
- 反序列化需要同时传入 JSON 字符串和目标类型。
