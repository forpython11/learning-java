# Lesson 33: 订单查询与类型流

> 状态：进行中

Lesson 32 已经把登录、下单、库存和事务连接成完整流程。本课暂时移开 Spring MVC、JWT 和数据库配置，只集中训练一个仍不稳定的能力：看清查询返回的是一个、多个，还是带分页信息的多个结果。

本课使用内存 Repository，但方法签名保持后端项目中的常见形式。你只需要完成三个生产代码 TODO 和三个对应测试 TODO。

## 学习目标

- 根据业务数量选择 `Optional<T>`、`List<T>` 或 `Page<T>`。
- 把 `Order` 转换成对外返回的 `OrderResponse`。
- 沿着链式调用说出每一步的元素类型和容器类型。
- 同时验证正常查询、缺失数据和分页元数据。

## 运行方式

先运行完整测试，初始骨架应能编译，但本课三个测试会显示为跳过：

```shell
mvn test
```

也可以在 IntelliJ 中直接运行 `lesson33/Main.java`，或在终端执行本课入口：

```shell
mvn -q org.codehaus.mojo:exec-maven-plugin:3.6.3:java -Dexec.mainClass=org.example.lesson33.Main
```

初始输出包含 `TODO`、空列表和全零分页信息，这是尚未完成练习时的预期占位行为。完成每个生产代码 TODO 后，再完成并启用相同编号的测试。

## 查询数量与返回类型

```text
按唯一订单 ID 查询          Optional<Order>
查询某个用户的全部订单       List<Order>
查询某个用户的一页订单       Page<Order>
```

它们不是三种随意替换的写法：

- `Optional<Order>` 表示最多一个，并且可能不存在。
- `List<Order>` 表示零个到多个，只携带元素。
- `Page<Order>` 表示当前页的多个元素，同时携带总数和页码。

## 任务

### TODO 1：查询单个订单

完成 `OrderQueryService.findById`：

1. 调用 `repository.findById(orderId)`。
2. 不存在时抛出 `new OrderNotFoundException(orderId)`。
3. 存在时使用 `OrderResponse.from` 转换并返回。

值和类型：

```text
orderId                              String
repository.findById(orderId)         Optional<Order>
orElseThrow(...)                     Order
OrderResponse.from(order)            OrderResponse
```

随后完成并启用 `findsOrderByIdAndRejectsMissingOrder()`。不能只检查 ID，还要检查客户、金额和状态；缺失订单必须精确断言 `OrderNotFoundException`。

### TODO 2：查询客户的全部订单

完成 `OrderQueryService.findByCustomerId`，把 Repository 返回的每个 `Order` 转换成 `OrderResponse`。

```text
repository.findByCustomerId(...)     List<Order>
stream()                             Stream<Order>
map(OrderResponse::from)             Stream<OrderResponse>
toList()                             List<OrderResponse>
```

随后完成并启用 `returnsOnlyTheCustomersOrders()`，精确断言结果顺序为 `O100`、`O200`、`O300`，并确认没有混入 `admin` 的 `O400`。

### TODO 3：转换分页结果

完成 `OrderPageResponse.from`：

1. 从 `result.getContent()` 取得当前页的 `List<Order>`。
2. 把当前页元素转换成 `List<OrderResponse>`。
3. 使用 `getNumber()`、`getSize()`、`getTotalElements()`、`getTotalPages()` 填写分页字段。

随后完成并启用 `returnsPageContentAndMetadata()`。第一页内容应是 `O100`、`O200`，并且总记录数仍然是 `3`，不是当前页数量 `2`。

## 完成后的精确输出

```text
订单详情：O100 / frontend / 598.00 / CREATED
用户订单数：3
订单列表：[O100, O200, O300]
第一页：[O100, O200]
分页信息：page=0, size=2, totalElements=3, totalPages=2
不存在订单：ORDER_NOT_FOUND: UNKNOWN
```

本课测试完成后应显示：

```text
Tests run: 3, Failures: 0, Errors: 0, Skipped: 0
```

## TypeScript 对照

在 TypeScript 中，这三种返回结果可能写成：

```typescript
function findById(id: string): Order | undefined;
function findByCustomerId(customerId: string): Order[];

type Page<T> = {
  content: T[];
  page: number;
  size: number;
  totalElements: number;
  totalPages: number;
};
```

Java 使用不同容器类型把“可能没有”“多个元素”和“分页结果”明确表达在方法签名中。

## 小提示

- TODO 1 先写一个明确类型为 `Order` 的局部变量，再转换 DTO；暂时不要压缩成一整条链。
- TODO 2 看不懂方法引用时，先写成 `.map(order -> OrderResponse.from(order))`。
- TODO 3 的 `result.getContent().size()` 只是当前页数量，不能当作全部匹配记录数。
- 测试中的 `ids(...)` 已经把 `List<OrderResponse>` 转换成 `List<String>`，可以直接与 `List.of(...)` 比较。
- 不要删除 `fail("TODO ...")` 后留下空测试；测试必须包含对应的业务断言。
