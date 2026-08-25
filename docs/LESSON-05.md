# Lesson 05: 使用 JUnit 编写单元测试

> 状态：已完成（2026-08-25）

这一课不再通过肉眼检查控制台输出，而是让代码自动验证计算结果。

## 文件位置

- 被测试代码：`src/main/java/org/example/lesson05/DiscountCalculator.java`
- 测试代码：`src/test/java/org/example/lesson05/DiscountCalculatorTest.java`

在 IntelliJ 中打开测试文件，点击类名或测试方法左侧的运行按钮。

## 任务

每个测试现在都有一个 `@Disabled` 和一个 `fail(...)`：

1. 删除当前测试的 `@Disabled`。
2. 删除 `fail(...)`。
3. 写入对应的断言。
4. 单独运行该测试，看到绿色通过后再处理下一个。

三个测试目标：

1. 原价 `200`、折扣 `0%`，结果应为 `200`。
2. 原价 `200`、折扣 `20%`，结果应为 `160`。
3. 折扣 `120%` 时，应抛出 `IllegalArgumentException`。

## 常用断言

```java
assertEquals(期望值, 实际值, 允许误差);
```

```java
assertThrows(异常类型.class, () -> 要执行的方法调用);
```

浮点数计算可能存在微小误差，因此本题使用 `0.001` 作为允许误差。

## TypeScript 测试对照

```typescript
expect(actualPrice).toBe(160);
expect(() => calculate(100, 120)).toThrow();
```

```java
assertEquals(160.0, actualPrice, 0.001);
assertThrows(IllegalArgumentException.class, () -> calculator.calculate(100.0, 120));
```
