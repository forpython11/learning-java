# Lesson 19: 全局异常处理

> 状态：已完成（2026-08-25）

上一课的 Controller 自己决定 `404`。真实项目中，Service 通常只表达“商品不存在”，再由全局异常处理器统一把异常转换成 HTTP 状态码和 JSON，避免每个 Controller 重复编写错误响应。

## 运行入口

打开 `src/main/java/org/example/lesson19/Lesson19Application.java`，运行 `main` 方法。

终端默认会启动当前课程：

```shell
mvn spring-boot:run
```

如果 `8080` 已被占用，可以添加程序参数：

```text
--server.port=8084
```

## 任务

1. `ProductService.getById`：商品不存在时抛出 `ProductNotFoundException`，消息为 `Product not found: ` 加商品 ID。
2. `GlobalExceptionHandler.handleProductNotFound`：添加 `@ExceptionHandler(ProductNotFoundException.class)`，让 Spring 用这个方法处理对应异常。
3. 在异常处理方法中返回 `HTTP 404`，响应体为 `new ApiError("PRODUCT_NOT_FOUND", exception.getMessage())`。

## 正常请求

```http
GET /api/products/P100
```

预期结果：

```text
HTTP 200
{"id":"P100","name":"Keyboard"}
```

## 商品不存在

```http
GET /api/products/P999
```

预期结果：

```text
HTTP 404
{"code":"PRODUCT_NOT_FOUND","message":"Product not found: P999"}
```

## 请求流程

```text
GET /api/products/P999
    -> ProductController
    -> ProductService 抛出 ProductNotFoundException
    -> GlobalExceptionHandler 捕获异常
    -> 返回 HTTP 404 + ApiError JSON
```

Controller 不需要写 `try/catch`。Spring 会在异常从 Controller 调用链中抛出后，寻找匹配的 `@ExceptionHandler` 方法。

## TypeScript 对照

可以把 `@RestControllerAdvice` 理解为 Express 的全局错误处理中间件：

```typescript
app.use((error, request, response, next) => {
  if (error instanceof ProductNotFoundError) {
    response.status(404).json({
      code: "PRODUCT_NOT_FOUND",
      message: error.message,
    });
  }
});
```

Java 中由异常类型决定调用哪个处理方法，`ResponseEntity` 同时描述状态码和响应体。

## 提示

- Service：在 `Optional` 后使用 `orElseThrow(...)`。
- 异常消息中的 ID 使用当前方法参数 `id`。
- 方法注解写在 `handleProductNotFound` 上方。
- `404` 可以使用 `ResponseEntity.status(HttpStatus.NOT_FOUND).body(...)`。
