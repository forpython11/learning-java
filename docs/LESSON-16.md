# Lesson 16: REST 路由与参数

> 状态：已完成（2026-08-25）

真实接口经常同时接收路径参数和查询参数。本课实现商品详情接口，并使用 `ResponseEntity` 明确返回 `200 OK` 或 `404 Not Found`。

## 运行入口

打开 `src/main/java/org/example/lesson16/Lesson16Application.java`，运行 `main` 方法。

终端默认会启动当前课程：

```shell
mvn spring-boot:run
```

如果第 15 题仍在使用 `8080` 端口，请先停止旧服务，或者给第 16 题添加程序参数：

```text
--server.port=8081
```

## 任务

在 `ProductController.java` 中完成三个 `TODO`：

1. 给 `id` 参数添加 `@PathVariable`，接收 URL 路径中的商品 ID。
2. 给 `includeDetails` 添加 `@RequestParam(defaultValue = "false")`，接收可选查询参数。
3. 找到商品时使用 `ResponseEntity.ok(...)` 返回商品；当 `includeDetails` 为 `true` 时，名称使用 `Keyboard (mechanical)`，否则使用 `Keyboard`。

`P100` 是本题唯一存在的商品。其他 ID 的 `404` 逻辑已经写好。

## 预期结果

请求：

```text
GET http://localhost:8080/api/products/P100
```

响应：

```text
HTTP 200
{"id":"P100","name":"Keyboard"}
```

请求：

```text
GET http://localhost:8080/api/products/P100?includeDetails=true
```

响应：

```text
HTTP 200
{"id":"P100","name":"Keyboard (mechanical)"}
```

请求不存在的商品：

```text
GET http://localhost:8080/api/products/P999
```

预期状态码：

```text
HTTP 404
```

## 参数从哪里来

```text
/api/products/P100?includeDetails=true
              ^^^^                路径参数 @PathVariable
                   ^^^^^^^^^^^^^^^ 查询参数 @RequestParam
```

## TypeScript 对照

Express 中的写法：

```typescript
app.get("/api/products/:id", (request, response) => {
  const id = request.params.id;
  const includeDetails = request.query.includeDetails === "true";
});
```

Spring 会根据方法参数上的注解完成字符串读取和 `boolean` 类型转换。

## ResponseEntity

`ResponseEntity<ProductResponse>` 同时包含 HTTP 状态、响应头和响应 body：

```java
ResponseEntity.ok(body);       // 200，带 body
ResponseEntity.notFound().build(); // 404，无 body
```

## 提示

- 注解写在对应方法参数的类型前面。
- 三元表达式与 TypeScript 相同：`condition ? first : second`。
- TODO 3 需要创建 `ProductResponse`，再交给 `ResponseEntity.ok(...)`。
