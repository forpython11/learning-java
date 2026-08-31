# Lesson 29: Spring Security 与 CORS

> 状态：进行中

上一课的 MockMvc 请求可以直接到达 Controller。本课在 Controller 前加入 Spring Security：先判断请求者是谁，再判断其角色能否访问接口，同时只允许指定前端开发地址跨域调用。

## 学习目标

- 区分认证（Authentication）与授权（Authorization）。
- 使用 `SecurityFilterChain` 配置公开、登录用户和管理员接口。
- 使用内存用户与 HTTP Basic 验证真实用户名和密码。
- 配置允许 `http://localhost:5173` 访问后端的 CORS 规则。
- 用 MockMvc 区分 `200`、`401` 和 `403`。

## 运行方式

运行本课测试：

```shell
mvn -Dtest=org.example.lesson29.SecurityConfigIntegrationTest test
```

初始代码可以编译，但为了不让新课破坏全项目测试，三个验收测试带有 `@Disabled`。每完成一个 TODO，就删除对应测试上的 `@Disabled`，再运行测试。

## 接口

| 请求 | 访问规则 | 成功响应 |
| --- | --- | --- |
| `GET /api/lesson29/public` | 所有人 | `{"message":"public"}` |
| `GET /api/lesson29/profile` | 已登录用户 | `{"message":"Hello, frontend"}` |
| `GET /api/lesson29/admin` | `ADMIN` 角色 | `{"message":"admin"}` |

测试使用两个内存用户：

```text
frontend / frontend123 / USER
admin    / admin123    / ADMIN
```

这些账号只用于本地练习，真实项目不要把明文密码写入源码。

## 任务

只修改 `SecurityConfig.java` 中的三个 TODO：

1. 配置路由权限：`/public` 使用 `permitAll()`，`/admin` 使用 `hasRole("ADMIN")`，其余请求使用 `authenticated()`。
2. 使用 `User.withUsername(...)` 和传入的 `PasswordEncoder` 创建 `frontend`、`admin` 两个用户，并放入 `InMemoryUserDetailsManager`。
3. 在 `CorsConfiguration` 中允许来源 `http://localhost:5173`、方法 `GET`，以及请求头 `Authorization`、`Content-Type`。

完成每项后，删除测试方法上相同编号的 `@Disabled`。

## 预期结果

```text
Tests run: 3, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

状态码含义：

```text
200  身份与权限都满足
401  没有登录，服务器还不知道你是谁
403  已经登录，但当前角色没有访问权限
```

## 请求链路

```text
浏览器 / MockMvc
    -> CORS 检查
    -> Authentication：用户名和密码是否正确
    -> Authorization：当前角色能否访问该路径
    -> SecurityController
```

## TypeScript 对照

Express 中可能会按顺序挂载中间件：

```typescript
app.use(cors({ origin: "http://localhost:5173" }));
app.get("/public", publicHandler);
app.get("/profile", requireLogin, profileHandler);
app.get("/admin", requireRole("ADMIN"), adminHandler);
```

Spring Security 的 `SecurityFilterChain` 类似一组统一的请求中间件；`requestMatchers(...)` 类似按 URL 选择规则，`permitAll`、`authenticated` 和 `hasRole` 分别对应公开、需要登录和需要指定角色。

## 提示

- 权限规则按书写顺序匹配，先写具体路径，最后再写 `anyRequest()`。
- `roles("ADMIN")` 会创建名为 `ROLE_ADMIN` 的权限，`hasRole("ADMIN")` 会自动补上 `ROLE_` 前缀。
- `PasswordEncoder` 类似登录时统一使用的密码哈希函数；创建用户和校验密码必须使用同一种编码方式。
- CORS 不是登录。它是浏览器在真正请求前检查“这个前端来源能否读取响应”。
- 跨域预检是 `OPTIONS` 请求，它会携带 `Origin` 和 `Access-Control-Request-Method` 请求头。
