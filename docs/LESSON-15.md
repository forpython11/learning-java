# Lesson 15: 第一个 Spring Boot REST API

> 状态：进行中

从本课开始进入 Spring Boot。你将启动一个内嵌 Web 服务器，并创建第一个可以由浏览器或前端 `fetch` 调用的 GET 接口。

## 运行入口

打开 `src/main/java/org/example/lesson15/Lesson15Application.java`，运行 `main` 方法。默认服务地址是 `http://localhost:8080`。

也可以在终端运行：

```shell
mvn spring-boot:run
```

## 任务

在 `GreetingController.java` 中完成三个 `TODO`：

1. 在类上添加 `@RestController`，让 Spring 创建并管理这个控制器。
2. 在 `hello` 方法上添加 `@GetMapping("/api/hello")`，声明 GET 路由。
3. 返回 `new GreetingResponse("Hello from Spring Boot")`。

完成后访问：

```text
http://localhost:8080/api/hello
```

预期 HTTP 响应 body：

```json
{"message":"Hello from Spring Boot"}
```

## 请求流程

```text
浏览器 GET /api/hello
        -> Spring 根据 @GetMapping 找到 hello 方法
        -> 方法返回 GreetingResponse
        -> Spring 自动转换成 JSON
        -> 前端收到 {"message":"Hello from Spring Boot"}
```

## 三个关键部分

- `@SpringBootApplication`：应用入口，开启自动配置和组件扫描。
- `@RestController`：声明这个类负责处理 HTTP 请求，返回值直接写入响应 body。
- `@GetMapping`：将 GET 请求路径映射到一个 Java 方法。

`GreetingResponse` 是一个 record。Spring 会使用 JSON 转换器把它自动序列化，所以本课不需要手动调用 `ObjectMapper`。

## TypeScript 对照

Express 中的写法：

```typescript
app.get("/api/hello", (request, response) => {
  response.json({ message: "Hello from Spring Boot" });
});
```

Spring Boot 把“路由”和“处理函数”通过注解写在控制器类中。

## 初始状态

初始代码可以启动，但因为控制器还没有添加注解，访问 `/api/hello` 会得到 `404`。完成三个 TODO 后才会返回预期 JSON。

## 提示

- 注解写在它要描述的类或方法正上方。
- 注解不需要 `new`。
- `GreetingResponse` 的构造参数就是响应中的 `message`。
