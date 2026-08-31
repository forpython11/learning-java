# Lesson 28: 使用 MockMvc 编写 Controller 集成测试

> 状态：已完成（2026-08-31）

上一课用 Mockito 隔离 Repository，只验证了 Service 的业务判断。本课不再 Mock Service 或 Repository，而是启动 Spring 测试上下文，用 MockMvc 发送模拟 HTTP 请求，并让请求真正经过 Controller、Service、JPA 和 H2 测试数据库。

## 学习目标

- 使用 `@SpringBootTest` 启动完整 Spring 应用上下文。
- 使用 `@AutoConfigureMockMvc` 和 `MockMvc` 测试 HTTP 状态码与 JSON。
- 在测试中通过 Repository 准备和核对 H2 数据。
- 区分 Mockito 单元测试与 Controller 集成测试的边界。

## 运行方式

运行本课测试：

```shell
mvn -Dtest=org.example.lesson28.ProductControllerIntegrationTest test
```

初始骨架中的三个测试带有 `@Disabled`。完成每个测试后删除对应的 `@Disabled` 和 `fail("TODO ...")`。生产代码已经完整，本课只修改测试文件中的三个 TODO。

## 被测链路

```text
MockMvc HTTP 请求
    -> ProductController
    -> ProductService
    -> ProductRepository
    -> H2 lesson28-test 数据库
    -> HTTP 状态码和 JSON
```

与 Lesson 27 的 Mock 不同，这里的 Repository 是真实 Spring Data JPA 实现，数据会写入专用的内存数据库。

## 任务

1. 查询成功：先向 Repository 保存 `P100 / Keyboard / 10`，再请求 `GET /api/lesson28/products/P100`；断言 `HTTP 200` 和三个 JSON 字段。
2. 查询失败：请求 `GET /api/lesson28/products/P404`；断言 `HTTP 404`、错误码 `PRODUCT_NOT_FOUND` 和消息 `Product not found: P404`。
3. 创建商品：发送 `POST /api/lesson28/products`，JSON 为 `{"id":"P200","name":"Mouse","stock":5}`；断言 `HTTP 201` 和响应字段，再通过 Repository 确认 `P200` 已写入 H2。

## 预期结果

```text
Tests run: 3, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

查询成功响应：

```json
{"id":"P100","name":"Keyboard","stock":10}
```

商品不存在响应：

```json
{"code":"PRODUCT_NOT_FOUND","message":"Product not found: P404"}
```

创建成功响应：

```json
{"id":"P200","name":"Mouse","stock":5}
```

## Arrange、Act、Assert

查询成功测试仍然分成三个阶段：

```text
Arrange  productRepository.save(new ProductEntity(...))
Act      mockMvc.perform(get(...))
Assert   andExpect(status...) + andExpect(jsonPath...)
```

MockMvc 的 `perform` 类似前端测试中发出一条模拟请求，`andExpect` 类似连续写多个 `expect(...)`。

## MockMvc 基本写法

GET 请求的结构：

```java
mockMvc.perform(get("/api/lesson28/products/P100"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value("P100"));
```

POST 请求还需要声明 JSON 类型和请求体：

```java
mockMvc.perform(post("/api/lesson28/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"P200\",\"name\":\"Mouse\",\"stock\":5}"))
        .andExpect(status().isCreated());
```

`$.id` 是 JSONPath，表示响应 JSON 根对象的 `id` 字段。

## 测试数据库

`@ActiveProfiles("lesson28-test")` 会加载 `src/test/resources/application-lesson28-test.yml`。它使用独立的 H2 内存数据库，并由 Hibernate 创建和删除 `lesson28_products` 表。

`@BeforeEach` 在每个测试前执行：

```java
productRepository.deleteAll();
```

这样每个测试都从空数据库开始，不依赖测试执行顺序。

## TypeScript 对照

使用 Supertest 测试 Express 或 NestJS 时，思路相同：

```typescript
await productRepository.save({ id: "P100", name: "Keyboard", stock: 10 });

const response = await request(app)
  .get("/api/lesson28/products/P100")
  .expect(200);

expect(response.body).toEqual({
  id: "P100",
  name: "Keyboard",
  stock: 10,
});
```

MockMvc 对应 `request(app)`，`andExpect` 对应状态码和响应体断言，H2 对应测试专用的临时数据库。

## 提示

- GET、POST、状态码和 JSONPath 的静态导入分别来自 `MockMvcRequestBuilders`、`MockMvcResultMatchers`。
- 成功查询前必须先调用 `productRepository.save(...)`，否则会进入 404 分支。
- 创建测试完成 HTTP 断言后，再调用 `productRepository.findById("P200")` 检查数据库状态。
- `Optional.orElseThrow()` 可以把查询到的 `ProductEntity` 取出来，再断言它的字段。
- 不要在本课使用 `@Mock`；本课的目标就是验证真实的 Spring 和数据库协作。
