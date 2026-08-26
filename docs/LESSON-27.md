# Lesson 27: 使用 Mockito 测试 Service

> 状态：进行中

上一课通过实际 HTTP 请求验证了库存事务。本课把数据库依赖替换成 Mock，只测试 `OrderService` 的业务判断和协作行为，让测试更快、更精确，也更容易覆盖错误分支。

## 学习目标

- 使用 `@Mock` 创建依赖的替身对象。
- 使用 `@InjectMocks` 把 Mock 注入被测 Service。
- 使用 `when(...).thenReturn(...)` 安排依赖返回值。
- 同时使用断言和 `verify(...)` 验证结果与交互。
- 使用 `assertThrows` 和 `never()` 检查失败分支没有产生写操作。

## 运行方式

运行本课测试：

```shell
mvn -Dtest=org.example.lesson27.OrderServiceTest test
```

初始骨架中的三个测试带有 `@Disabled`，所以项目可以编译，但测试会显示为跳过。完成每个测试后删除对应的 `@Disabled` 和 `fail("TODO ...")`。

## 被测流程

`OrderService.placeOrder(productId, quantity)` 已经实现：

```text
productRepository.findById
    -> Product.decreaseStock
    -> productRepository.save
    -> orderRepository.save
    -> OrderResult
```

本课不修改生产实现，只为这个流程建立可信的自动化验证。

## 任务

1. 成功下单：让商品查询返回库存 `10` 的 `P100`，购买 `3` 件；断言响应剩余库存为 `7`，并验证商品和订单各保存一次。
2. 商品不存在：让查询返回 `Optional.empty()`；断言抛出 `ProductNotFoundException`，并验证订单 Repository 没有收到任何调用。
3. 库存不足：让查询返回库存 `2` 的 `P100`，尝试购买 `3` 件；断言抛出 `InsufficientStockException`，并验证两个 Repository 都没有执行 `save`。

不要只删除 `@Disabled`。骨架中的 `fail("TODO ...")` 会让未实现的测试明确失败，防止没有验证任何行为的空测试显示绿色。

## 完成后的结果

```text
Tests run: 3, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

## 测试的三个阶段

每个测试按 Arrange、Act、Assert 拆开：

```text
Arrange  准备 Product，并安排 Mock Repository 的返回值
Act      调用 service.placeOrder(...)
Assert   断言返回值或异常，再验证 Repository 交互
```

成功分支中的类型流：

```text
when(productRepository.findById("P100"))
    -> thenReturn(Optional.of(product))
    -> service.placeOrder("P100", 3)
    -> OrderResult(productId="P100", quantity=3, remainingStock=7)
```

## 状态断言与交互验证

下面两类检查解决不同问题：

```java
assertEquals(7, result.remainingStock());
```

它验证返回的数据正确。

```java
verify(orderRepository).save(new Order("P100", 3));
```

它验证 Service 确实要求 Repository 保存订单。因为 `Order` 是 `record`，两个内容相同的实例可以直接比较。

失败分支还要确认没有发生不该发生的写操作：

```java
verify(productRepository, never()).save(any());
verifyNoInteractions(orderRepository);
```

## TypeScript 对照

Vitest 或 Jest 中也会安排 Mock 返回值并验证调用：

```typescript
productRepository.findById.mockResolvedValue({ id: "P100", stock: 10 });

const result = service.placeOrder("P100", 3);

expect(result.remainingStock).toBe(7);
expect(orderRepository.save).toHaveBeenCalledWith({
  productId: "P100",
  quantity: 3,
});
```

Mockito 的 `when(...).thenReturn(...)` 对应 `mockResolvedValue`，`verify(...)` 对应 `toHaveBeenCalledWith(...)`。

## 提示

- 静态导入通常来自 `org.junit.jupiter.api.Assertions` 和 `org.mockito.Mockito`。
- 查询成功的 Stub：`when(productRepository.findById("P100")).thenReturn(Optional.of(product))`。
- 商品不存在时，`OrderService` 在调用订单 Repository 前就会抛出异常。
- 库存不足时，商品查询已经发生，但两个 `save` 都不应发生。
- `assertThrows` 返回捕获到的异常对象；需要时可以继续断言异常消息。
