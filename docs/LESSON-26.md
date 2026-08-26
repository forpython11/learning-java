# Lesson 26: 事务与并发更新

> 状态：进行中

一次购买会同时修改两处数据：扣减库存，并保存订单。只完成其中一步会让数据库进入不一致状态。本课使用 `@Transactional` 保证两步一起成功或一起回滚，并使用 `@Version` 防止并发请求互相覆盖库存。

## 学习目标

- 理解事务的原子性：一组数据库操作要么全部成功，要么全部回滚。
- 使用 `@Transactional` 声明 Service 方法的事务边界。
- 把库存校验放在 Entity 的业务方法中。
- 使用 `@Version` 和乐观锁识别并发更新冲突。

## 运行入口

运行 `src/main/java/org/example/lesson26/Lesson26Application.java` 中的 `main` 方法，或执行：

```shell
mvn spring-boot:run
```

如果 `8080` 已占用，可以添加程序参数：

```text
--server.port=8091
```

启动后，库存表包含 `P100 / Keyboard`，初始库存为 `10`、版本号为 `0`。

## 任务

1. `InventoryItemEntity.version`：添加 `@Version`，让 JPA 在更新库存时检查版本号。
2. `InventoryItemEntity.decreaseStock`：数量小于等于 `0` 时抛出 `IllegalArgumentException`；库存不足时抛出 `InsufficientStockException`；否则扣减库存。
3. `PurchaseService.purchase`：添加 `@Transactional`，查询库存、调用 `decreaseStock`、使用 `saveAndFlush` 保存订单，并返回真实的剩余库存和版本号。

初始骨架可以编译和启动，但购买接口只返回 `-1` 占位值，也不会修改数据库。

## 正常购买

先查询库存：

```http
GET /api/inventory/P100
```

```json
{"id":"P100","name":"Keyboard","stock":10,"version":0}
```

创建订单并购买 3 件：

```http
POST /api/inventory/purchases
Content-Type: application/json

{"orderId":"O100","productId":"P100","quantity":3}
```

预期响应：

```text
HTTP 201
```

```json
{"orderId":"O100","productId":"P100","quantity":3,"remainingStock":7,"version":1}
```

`saveAndFlush` 会在构造响应前把当前事务中的 SQL 发送到数据库，因此 Entity 的版本号已经从 `0` 变为 `1`。事务仍然要等方法正常结束后才正式提交。再次 GET 时应看到相同版本：

```json
{"id":"P100","name":"Keyboard","stock":7,"version":1}
```

## 回滚验证

再次使用相同的 `orderId`，数据库的唯一约束会拒绝重复订单：

```http
POST /api/inventory/purchases
Content-Type: application/json

{"orderId":"O100","productId":"P100","quantity":2}
```

预期返回 `HTTP 409` 和错误码 `DUPLICATE_ORDER`。虽然代码在保存订单前已经执行了库存扣减，但事务失败后会回滚；再次查询库存仍应是 `7`，不能变成 `5`。

库存不足时也应返回 `HTTP 409`：

```json
{"orderId":"O101","productId":"P100","quantity":20}
```

此时库存仍然是 `7`。

## 事务边界

```text
PurchaseController
    -> PurchaseService.purchase       @Transactional 开始
        -> 查询 InventoryItemEntity
        -> decreaseStock
        -> 保存 PurchaseOrderEntity
    -> 方法正常结束                    提交
    -> 任一步抛出 RuntimeException      回滚
```

`@Transactional` 应放在 Service 的公开方法上。Controller 只负责 HTTP 输入输出，Entity 只负责自己的库存规则，Service 负责组织完整业务流程。

## 乐观锁

`@Version` 会让更新语句带上读取时的版本号，效果类似：

```sql
UPDATE inventory_items
SET stock = ?, version = version + 1
WHERE id = ? AND version = ?;
```

两个请求同时读取版本 `0` 时，只有第一个更新能成功；第二个发现版本已变化，会得到并发更新异常，而不是悄悄覆盖第一个请求的库存。

## TypeScript 对照

TypeScript ORM 中通常也把多个写操作包在事务回调里，并使用版本字段进行乐观锁检查：

```typescript
await database.transaction(async manager => {
  const item = await manager.inventory.findOneOrFail({
    where: { id: request.productId },
  });

  item.decreaseStock(request.quantity);
  await manager.inventory.save(item);
  await manager.orders.insert(request);
});
```

Java 的 `@Transactional` 把事务回调的边界声明在方法上。方法正常返回时提交，运行时异常向外抛出时回滚。

## 提示

- TODO 1 需要导入 `jakarta.persistence.Version`。
- TODO 2 先判断 `quantity <= 0`，再判断 `stock < quantity`，最后执行 `stock -= quantity`。
- TODO 3 先看 `inventoryRepository.findById` 的返回类型，并用 `orElseThrow` 处理不存在的商品。
- 创建订单使用 `new PurchaseOrderEntity(request.orderId(), request.productId(), request.quantity())`。
- 使用 `orderRepository.saveAndFlush(...)` 可以在方法返回前触发唯一约束检查，便于观察回滚。
- 库存 Entity 是事务中查询到的受管理对象，调用 `decreaseStock` 后可以依靠 JPA dirty checking 更新，不必再次调用 `save`。
