# Lesson 03: 订单状态与异常处理

> 状态：已完成（2026-08-24）

这一课练习 Java 的 `enum`、业务校验、抛出异常和 `try/catch`。

## 运行入口

打开 `src/main/java/org/example/lesson03/Main.java`，运行其中的 `main` 方法。

## 业务规则

支付订单时必须满足：

1. 订单金额必须大于 `0`，否则抛出 `IllegalArgumentException`。
2. 只有 `PENDING` 状态能支付，否则抛出 `IllegalStateException`。
3. 支付成功后，状态变为 `PAID`。

## 任务

1. 完成 `OrderService.pay` 中的两个校验。
2. 完成 `Main.processOrder`，使用 `try/catch` 调用支付方法并打印结果。

完成后的预期输出：

```text
Order A100 paid successfully: PAID
Order A101 failed: Only pending orders can be paid
Order A102 failed: Order amount must be greater than 0
```

## TypeScript 与 Java 对照

| TypeScript | Java |
| --- | --- |
| `enum Status` | `enum OrderStatus` |
| `throw new Error(message)` | `throw new IllegalArgumentException(message)` |
| `try { ... } catch (error) { ... }` | `try { ... } catch (RuntimeException exception) { ... }` |
| `order.status = Status.PAID` | `order.setStatus(OrderStatus.PAID)` |

## 提示

- 金额条件：`order.getAmount() <= 0`。
- 状态比较：`order.getStatus() != OrderStatus.PENDING`。
- 异常消息：`exception.getMessage()`。
