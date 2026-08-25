# Lesson 11: 使用日期与时间 API

> 状态：已完成（2026-08-25）

Java 使用 `java.time` 包处理日期和时间。本课会练习不包含时间的 `LocalDate`、包含日期和时间的 `LocalDateTime`，以及日期格式化和先后比较。

## 运行入口

打开 `src/main/java/org/example/lesson11/Main.java`，完成三个 `TODO` 后运行 `main` 方法。

## 任务

1. `calculateDeliveryDate`：在下单日期上增加指定天数，返回预计送达日期。
2. `formatCreatedAt`：将创建时间格式化为 `yyyy-MM-dd HH:mm`。
3. `isExpired`：判断过期时间是否早于当前时间。

完成后的预期输出：

```text
Delivery date: 2026-08-28
Created at: 2026-08-25 09:30
Expired: true
```

## 类型选择

- `LocalDate`：只有年月日，例如生日、送达日期。
- `LocalDateTime`：有年月日和时分秒，例如订单创建时间。
- `DateTimeFormatter`：定义日期时间转换成字符串的格式。

这些类型都是不可变对象，调用 `plusDays` 不会修改原对象，而是返回新对象。这与 JavaScript 的日期库中常见的不可变操作类似。

## TypeScript 对照

```typescript
const deliveryDate = addDays(orderDate, shippingDays);
const expired = expiresAt < now;
```

对应的 Java 思路：

```java
date.plusDays(days);
firstDateTime.isBefore(secondDateTime);
```

## 提示

- 增加天数：`orderDate.plusDays(shippingDays)`。
- 创建格式器：`DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")`。
- 格式化：`createdAt.format(formatter)`。
- 如果 `expiresAt` 早于 `now`，说明已经过期。
