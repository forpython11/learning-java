# Lesson 12: 自定义业务异常

> 状态：进行中

后端需要区分“传入的数据不合法”和“业务状态不允许操作”。本课会为订单取消功能选择合适的异常类型，并使用自定义异常携带稳定的错误码。

## 运行入口

打开 `src/main/java/org/example/lesson12/Main.java`，完成 `OrderService` 中的三个 `TODO` 后运行 `main` 方法。

## 任务

1. 当 `order` 为 `null` 时，抛出 `IllegalArgumentException`，消息为 `Order must not be null`。
2. 当订单状态不是 `PENDING` 时，抛出 `BusinessException`：
   - 错误码：`ORDER_CANNOT_CANCEL`
   - 消息：`Only pending orders can be cancelled`
3. 校验通过后，将订单状态改为 `CANCELLED`。

完成后的预期输出：

```text
Order A100 status: CANCELLED
Rejected [ORDER_CANNOT_CANCEL]: Only pending orders can be cancelled
```

## 如何选择异常

- `IllegalArgumentException`：调用者传入的参数本身无效，例如传入 `null`。
- `BusinessException`：参数是一个真实订单，但当前业务状态不允许操作，例如已支付订单不能取消。
- `RuntimeException`：上面两种异常的共同父类，通常不要在业务代码里用它代替更具体的异常。

`BusinessException` 中的 `code` 可以稳定地提供给前端判断错误类型，`message` 则用于说明具体原因。

## TypeScript 对照

JavaScript 中可以自定义错误类型：

```typescript
class BusinessError extends Error {
  constructor(public code: string, message: string) {
    super(message);
  }
}
```

Java 的 `BusinessException extends RuntimeException` 是相同思路。

## 提示

- 判断空值：`order == null`。
- 抛出异常：`throw new IllegalArgumentException(...)`。
- 读取状态：`order.getStatus()`。
- 修改状态：`order.setStatus(OrderStatus.CANCELLED)`。
