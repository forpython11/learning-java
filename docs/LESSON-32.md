# Lesson 32: 最终全栈项目

> 状态：已完成（2026-09-01）

这是整套课程的最后一课。项目提供一个可供前端调用的小型订单系统：访客可以查看商品，用户登录后获得 JWT，携带 Bearer 令牌创建订单，服务端在同一事务中扣减库存并保存订单。

你不需要重新猜一遍所有 Spring API。安全、Controller、Repository、Entity、异常处理和集成测试已经连接好，本课只完成 `FinalOrderService.createOrder()` 中的三个业务 TODO。

## 学习目标

- 串联 `Controller -> Service -> Repository -> Entity -> DTO -> JSON`。
- 使用 `Optional.orElseThrow()` 处理商品不存在。
- 在写数据库前完成库存校验。
- 使用 `@Transactional` 保证库存和订单同时成功或失败。
- 从前端视角完成“商品列表 -> 登录 -> Bearer 下单”的完整流程。

## 运行方式

运行最终项目测试：

```shell
mvn -Dtest=org.example.lesson32.FinalProjectIntegrationTest test
```

初始骨架可以编译。安全和公开商品测试会正常执行，三个业务验收测试带有 `@Disabled`。每完成一个 TODO，再删除相同编号测试上的 `@Disabled`。

运行应用时启用 Lesson 32 配置：

```shell
mvn spring-boot:run -Dspring-boot.run.profiles=lesson32
```

根目录 Dockerfile 已默认设置 `SPRING_PROFILES_ACTIVE=lesson32`。完成练习并执行 `mvn clean package` 后，也可以在安装了 Docker 的环境中构建和运行最终项目。

## 完整请求流程

```text
GET /api/lesson32/products
    -> ProductRepository
    -> List<ProductEntity>
    -> List<ProductResponse>
    -> JSON 商品列表

POST /api/lesson32/login
    -> AuthenticationManager
    -> JWT 字符串

POST /api/lesson32/orders + Bearer JWT
    -> FinalOrderService.createOrder(request)
    -> 查询商品
    -> 校验库存
    -> 扣减库存 + 保存订单
    -> OrderResponse
    -> JSON 下单结果
```

## 类型地图

| Java 类型 | 当前值 | TypeScript 对照 |
| --- | --- | --- |
| `CreateOrderRequest` | 商品 ID 和数量 | 表单提交 DTO |
| `Optional<ProductEntity>` | 查询可能有或没有商品 | `Product \| undefined` |
| `ProductEntity` | 数据库中的商品与库存 | ORM product model |
| `PurchaseOrderEntity` | 准备保存的订单 | ORM order model |
| `OrderResponse` | 返回前端的结果 | API response type |
| `@Transactional` | 一个完整数据库工作单元 | transaction callback |

## 任务

### TODO 1：查询商品

当前代码使用 `orElse(null)`，这会让错误延迟成 `NullPointerException`。改为：

```java
.orElseThrow(() -> new ProductNotFoundException(request.productId()));
```

数据流：

```text
request.productId()                       String
productRepository.findById(...)           Optional<ProductEntity>
orElseThrow(...)                          ProductEntity
```

完成后删除 `rejectsMissingProduct()` 上的 `@Disabled`。

### TODO 2：校验库存

当商品库存小于购买数量时，抛出：

```java
new InsufficientStockException(request.productId())
```

条件表达式两边都是 `int`：

```text
product.getStock()        当前库存
request.quantity()        购买数量
```

这里必须在扣库存和保存订单之前失败。完成后删除 `rejectsInsufficientStockWithoutWriting()` 上的 `@Disabled`。

### TODO 3：完成事务下单

成功分支按以下顺序完成：

1. `product.decreaseStock(request.quantity())` 扣减库存。
2. 使用商品单价乘以数量计算 `BigDecimal total`。
3. 使用 `UUID.randomUUID().toString()`、商品 ID、数量、总价和 `Instant.now()` 创建 `PurchaseOrderEntity`。
4. 调用 `orderRepository.save(order)`。
5. 返回包含真实订单 ID、总价和剩余库存的 `OrderResponse`。

关键类型流：

```text
product.getPrice()                         BigDecimal
BigDecimal.valueOf(request.quantity())     BigDecimal
price.multiply(quantity)                   BigDecimal total
new PurchaseOrderEntity(...)               PurchaseOrderEntity
orderRepository.save(order)                PurchaseOrderEntity
new OrderResponse(...)                     OrderResponse
```

`ProductEntity` 是事务中查询到的受管理实体，调用 `decreaseStock()` 后，事务提交时 JPA 会把变化写回数据库，不需要额外调用 `productRepository.save(product)`。

完成后删除 `createsOrderAndDeductsStock()` 上的 `@Disabled`。

## 接口与预期结果

公开商品列表：

```http
GET /api/lesson32/products
```

```json
[
  {"id":"P100","name":"Keyboard","price":299.00,"stock":5}
]
```

登录：

```http
POST /api/lesson32/login
Content-Type: application/json

{"username":"frontend","password":"frontend123"}
```

```json
{"token":"eyJ..."}
```

下单：

```http
POST /api/lesson32/orders
Authorization: Bearer eyJ...
Content-Type: application/json

{"productId":"P100","quantity":2}
```

```json
{
  "orderId":"生成的 UUID",
  "productId":"P100",
  "quantity":2,
  "total":598.00,
  "remainingStock":3
}
```

错误分支：

```text
未携带 JWT               401
商品不存在                404 PRODUCT_NOT_FOUND
库存不足                  409 INSUFFICIENT_STOCK
```

最终测试结果：

```text
Tests run: 4, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

## TypeScript 对照

Java Service 的事务代码，对应前端熟悉的服务调用流程：

```typescript
const product = await productRepository.findById(request.productId);
if (!product) throw new ProductNotFoundError(request.productId);
if (product.stock < request.quantity) throw new InsufficientStockError();

await database.transaction(async () => {
  product.stock -= request.quantity;
  await orderRepository.save(order);
});
```

Java 的区别是实体变化由 JPA 在事务提交时自动检测和写回。

## 最终自检

- 三个业务测试全部启用，`Skipped: 0`。
- 成功分支同时验证 HTTP 响应、订单数量和剩余库存。
- 两个失败分支都确认没有保存订单。
- 能用自己的话说出一次下单从 JSON 到数据库再回到 JSON 的类型变化。
- 能解释为什么查询、扣库存和保存订单必须位于同一个事务。
