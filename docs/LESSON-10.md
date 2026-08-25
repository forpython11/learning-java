# Lesson 10: 使用 BigDecimal 计算金额

> 状态：进行中

金额计算不能依赖 `double` 的二进制浮点近似值。`BigDecimal` 可以精确表示十进制金额，并明确指定舍入规则。

## 运行入口

打开 `src/main/java/org/example/lesson10/Main.java`，完成三个 `TODO` 后运行 `main` 方法。

## 任务

1. `calculateSubtotal`：使用单价乘以数量得到小计。
2. `applyDiscount`：根据折扣率计算折后金额。
3. `roundMoney`：使用 `HALF_UP` 将金额保留两位小数。

本题数据：

```text
单价：99.90
数量：3
折扣率：15%（代码中表示为 0.15）
```

完成后的预期输出：

```text
Subtotal: 299.70
Discounted total: 254.75
```

## 为什么不用 double

JavaScript 和 Java 的 `double` 都使用二进制浮点数，某些十进制小数无法精确表示：

```text
0.1 + 0.2 不一定精确等于 0.3
```

创建金额时使用字符串：

```java
new BigDecimal("99.90")
```

不要使用：

```java
new BigDecimal(99.90)
```

## 常用操作

`BigDecimal` 是不可变对象，每次计算都会返回一个新对象：

```java
price.multiply(quantity);
amount.subtract(discount);
amount.setScale(2, RoundingMode.HALF_UP);
```

## 提示

- 将整数数量转换为 BigDecimal：`BigDecimal.valueOf(quantity)`。
- 小计：`unitPrice.multiply(...)`。
- 折后比例：`BigDecimal.ONE.subtract(discountRate)`。
- 折后金额：`subtotal.multiply(...)`。
- 两位小数：`amount.setScale(2, RoundingMode.HALF_UP)`。
