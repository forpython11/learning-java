# Lesson 02: 用 Stream 处理商品列表

> 状态：已完成（2026-08-24）

这一课把前端常用的 `filter`、`map` 和 `reduce` 思路迁移到 Java Stream。

## 运行入口

打开 `src/main/java/org/example/lesson02/Main.java`，运行其中的 `main` 方法。

练习骨架可以编译，但三个方法暂时返回空结果。完成后应输出：

```text
=== Available products (price >= 100.0) ===
Keyboard: 299.0
Desk: 899.0
Lamp: 129.0
Total price: 1327.0
Electronics count: 1
```

## 任务

只修改 `Main.java` 中标有 `TODO` 的三个方法：

1. `filterAvailableProducts`：保留有库存并且价格不低于 `minPrice` 的商品。
2. `calculateTotalPrice`：计算筛选结果的价格总和。
3. `countByCategory`：统计指定分类的商品数量。

本题要求使用 Stream，不使用 `for` 循环。

## TypeScript 与 Java 对照

| TypeScript | Java Stream |
| --- | --- |
| `products.filter(fn)` | `products.stream().filter(fn).toList()` |
| `products.map(fn)` | `products.stream().map(fn).toList()` |
| `products.reduce(...)` | `products.stream().mapToDouble(...).sum()` |
| `products.filter(fn).length` | `products.stream().filter(fn).count()` |

## 提示

- Lambda 写法：`product -> product.isInStock()`。
- 两个条件使用 `&&` 连接。
- 获取价格：`Product::getPrice` 或 `product -> product.getPrice()`。
- Java 字符串比较内容使用 `equals`，不要使用 `==`。
