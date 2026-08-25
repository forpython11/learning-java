# Lesson 14: 使用 HttpClient 请求 API

> 状态：已完成（2026-08-25）

Java 自带的 `HttpClient` 可以发送 HTTP 请求。本课会请求一个由 `Main` 临时启动的本地商品 API，练习拼接地址、设置请求头和检查响应状态码，不依赖公网服务。

## 运行入口

打开 `src/main/java/org/example/lesson14/Main.java`，完成 `ProductApiClient` 中的三个 `TODO` 后运行 `main` 方法。

初始代码会请求错误的根路径，因此可能打印本地服务返回的 `404 Not Found` 内容；这是尚未完成 TODO 1 时的预期占位行为。

## 任务

1. `buildProductUri`：创建 `baseUrl + "/products/" + productId` 对应的 `URI`。
2. `buildRequest`：添加请求头 `Accept: application/json`。
3. `ensureSuccess`：当状态码不在 `200` 到 `299` 之间时抛出：

```java
new IllegalStateException("HTTP request failed: " + response.statusCode())
```

完成后的预期输出：

```text
Response: {"id":"P100","name":"Keyboard"}
```

## 请求流程

```text
productId
   -> 构造 URI
   -> 构造 HttpRequest
   -> HttpClient.send
   -> HttpResponse<String>
   -> 检查 statusCode
   -> 读取 body
```

## TypeScript 对照

```typescript
const response = await fetch(`${baseUrl}/products/${productId}`, {
  headers: { Accept: "application/json" },
});

if (!response.ok) {
  throw new Error(`HTTP request failed: ${response.status}`);
}

const body = await response.text();
```

Java 的 `HttpRequest` 相当于请求配置，`HttpResponse<String>` 相当于还没有调用 `response.json()` 时的 Fetch Response 加文本 body。

## 配置说明

`baseUrl` 通过构造器传入，而不是写死在 `ProductApiClient` 中。以后切换开发、测试和生产地址时，只需要改变配置，不需要修改请求逻辑。

## 提示

- 创建地址：`URI.create(...)`。
- 添加请求头：`.header("Accept", "application/json")`。
- 发送并接收字符串：`BodyHandlers.ofString()` 已经写好。
- 成功状态码范围：`statusCode >= 200 && statusCode < 300`。
