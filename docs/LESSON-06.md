# Lesson 06: 使用 Map 按 ID 存取数据

> 状态：进行中

后端经常需要根据 ID 快速查找数据。这一课使用 `Map<String, Customer>` 实现一个内存客户仓库。

## 运行入口

打开 `src/main/java/org/example/lesson06/Main.java`，运行其中的 `main` 方法。

## 任务

完成 `CustomerRepository` 中的三个 `TODO`：

1. 保存前使用 `containsKey` 判断 ID 是否已经存在；存在时抛出 `IllegalArgumentException`。
2. 使用 `put` 将客户保存到 Map。
3. 使用 `get` 查找客户；找不到时抛出 `IllegalArgumentException`，找到时返回客户。

完成后的预期输出：

```text
Found customer: Ada
Customer count: 2
Duplicate rejected: Customer ID already exists: C001
```

## TypeScript 与 Java 对照

| TypeScript | Java |
| --- | --- |
| `new Map<string, Customer>()` | `new HashMap<String, Customer>()` |
| `map.has(id)` | `map.containsKey(id)` |
| `map.set(id, customer)` | `map.put(id, customer)` |
| `map.get(id)` | `map.get(id)` |
| `map.size` | `map.size()` |

## 提示

- 客户 ID：`customer.getId()`。
- 保存数据：`customers.put(customer.getId(), customer)`。
- 查找结果可以先保存到局部变量 `Customer customer`。
- Map 找不到对应的 Key 时，`get` 返回 `null`。

