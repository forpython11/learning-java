# Lesson 17: POST 请求与 DTO 校验

> 状态：进行中

本课实现创建商品接口。Spring 会把前端发送的 JSON 转换成请求 DTO，并在进入业务方法前执行 Bean Validation 校验。

## 运行入口

打开 `src/main/java/org/example/lesson17/Lesson17Application.java`，运行 `main` 方法。

终端默认会启动当前课程：

```shell
mvn spring-boot:run
```

如果其他课程仍占用 `8080`，可以添加程序参数：

```text
--server.port=8082
```

## 任务

1. 在 `CreateProductRequest.name` 前添加 `@NotBlank(message = "Name must not be blank")`。
2. 在 `CreateProductRequest.price` 前添加 `@Positive(message = "Price must be greater than 0")`。
3. 在 `ProductController.create` 已有的 `@RequestBody` 前添加 `@Valid`，触发 DTO 校验。

## 正常请求

```http
POST /api/products
Content-Type: application/json

{"name":"Keyboard","price":299.90}
```

预期结果：

```text
HTTP 201
{"id":"P200","name":"Keyboard","price":299.90}
```

## 校验失败

请求：

```json
{"name":"   ","price":0}
```

预期状态码：

```text
HTTP 400
```

本题只要求确认状态码为 `400`。第 19 课会统一定制错误响应 JSON。

## 请求流程

```text
JSON body
   -> @RequestBody 转换为 CreateProductRequest
   -> @Valid 执行 @NotBlank 和 @Positive
   -> 校验通过后进入 create 方法
   -> 返回 ProductResponse 和 HTTP 201
```

## 三个注解的职责

- `@RequestBody`：读取 HTTP body，并将 JSON 转换成指定 Java 类型。
- `@Valid`：要求 Spring 执行这个对象上的校验注解。
- `@NotBlank` / `@Positive`：声明字段必须满足的规则。

只有字段上的校验注解而没有 `@Valid` 时，Spring 不会自动执行这次方法参数校验。初始代码因此可以接收 JSON，但无效数据也会暂时得到 `201`。

## TypeScript 对照

使用 Zod 时可能会写：

```typescript
const schema = z.object({
  name: z.string().trim().min(1),
  price: z.number().positive(),
});

const request = schema.parse(requestBody);
```

Bean Validation 把这些规则声明在 Java DTO 上，Spring 在 Controller 调用前执行它们。

## 为什么返回 201

`201 Created` 表示服务器成功创建了一个新资源，比普通的 `200 OK` 更准确：

```java
ResponseEntity.status(HttpStatus.CREATED).body(response);
```

## 提示

- 校验注解写在 record 组件类型前面。
- 把 `@Valid` 写在已有的 `@RequestBody` 前面。
- 不需要修改已经写好的 `ResponseEntity.status(...)`。
