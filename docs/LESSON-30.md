# Lesson 30: JWT 鉴权

> 状态：已完成（2026-09-01）

上一课每次请求都使用 HTTP Basic 携带用户名和密码。本课改为登录一次后得到 JWT，后续请求只携带令牌。Spring Security 会验证签名和过期时间，再从 JWT 的 `subject` 恢复用户名。

这批 API 是第一次出现，不要求凭空猜方法名。先阅读下面的类型地图和现有骨架，再只填写三个局部 TODO。

## 学习目标

- 理解 JWT 的签发、携带、验证和过期流程。
- 使用 `JwtClaimsSet` 描述令牌中的用户身份。
- 使用 Spring Security 的 `JwtEncoder` 和 `JwtDecoder`，不手写 JWT 解析算法。
- 使用 `Authorization: Bearer <token>` 访问受保护接口。
- 区分密码错误、缺少令牌和令牌被篡改。

## 运行方式

运行本课测试：

```shell
mvn -Dtest=org.example.lesson30.JwtAuthenticationIntegrationTest test
```

初始骨架可以编译，三个验收测试带有 `@Disabled`。此时使用正确账号登录会暂时得到 `{"token":"TODO"}`，这是 TODO 2 的占位结果，不是真正的 JWT。完成相同编号的 TODO 后，再删除对应测试上的 `@Disabled`。

## 类型地图

| 类型 | 作用 | 类似的 TypeScript 概念 |
| --- | --- | --- |
| `Authentication` | 已通过密码校验的当前用户 | 登录后的 user/session 对象 |
| `JwtClaimsSet` | JWT 中准备写入的数据 | token payload 对象 |
| `JwtEncoder` | 对 claims 签名并生成 JWT 字符串 | `jwt.sign(payload, secret)` |
| `JwtDecoder` | 验证签名、过期时间并读取 claims | `jwt.verify(token, secret)` |
| `SecurityFilterChain` | 请求进入 Controller 前的安全规则 | Express authentication middleware |

`User`、`Authentication`、`JwtClaimsSet` 都是类型；`authenticate()`、`subject()`、`encode()` 才是方法。

## 请求流程

```text
POST /api/lesson30/login + 用户名密码
    -> AuthenticationManager 校验账号密码
    -> Authentication（已认证用户）
    -> JwtTokenService 生成 JWT
    -> {"token":"eyJ..."}

GET /api/lesson30/profile
Authorization: Bearer eyJ...
    -> JwtDecoder 验证签名和过期时间
    -> 从 subject 恢复用户名
    -> {"username":"frontend"}
```

## 任务

### TODO 1：填写 JWT 身份与过期时间

在 `JwtTokenService` 中给 `claimsBuilder` 增加两项：

- `subject` 使用 `authentication.getName()`。
- `expiresAt` 使用 `issuedAt.plusSeconds(1800)`，表示 30 分钟后过期。

值和类型的变化：

```text
authentication.getName()        String
issuedAt                         Instant
issuedAt.plusSeconds(1800)       Instant
claimsBuilder.build()            JwtClaimsSet
```

### TODO 2：编码并返回 JWT

先使用 `JwsHeader.with(MacAlgorithm.HS256).build()` 创建签名头，再把签名头和 `claims` 交给 `JwtEncoderParameters.from(...)`，最后调用 `jwtEncoder.encode(...)` 并取得 `getTokenValue()`。

这一步的数据流是：

```text
JwsHeader + JwtClaimsSet
    -> JwtEncoderParameters
    -> Jwt
    -> String tokenValue
```

### TODO 3：保护非登录接口

`JwtSecurityConfig` 已经配置好无状态会话和 Bearer JWT 解析。将 `anyRequest().permitAll()` 改为要求认证，使登录接口继续公开，而 `/profile` 必须携带有效 JWT。

## 接口与预期结果

正确登录：

```http
POST /api/lesson30/login
Content-Type: application/json

{"username":"frontend","password":"frontend123"}
```

```json
{"token":"eyJ..."}
```

携带返回的令牌：

```http
GET /api/lesson30/profile
Authorization: Bearer eyJ...
```

```json
{"username":"frontend"}
```

错误结果：

```text
错误密码                         401
访问 /profile 时没有 Bearer JWT  401
JWT 内容或签名被修改              401
```

最终测试结果：

```text
Tests run: 3, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

## TypeScript 对照

```typescript
const token = jwt.sign(
  { sub: authentication.username },
  secret,
  { expiresIn: "30m" },
);

const response = await fetch("/api/lesson30/profile", {
  headers: { Authorization: `Bearer ${token}` },
});
```

Java 中仍是同一条数据流，只是每一步使用明确类型表达。

## 安全提醒

- JWT 的 payload 通常只是 Base64URL 编码，不是加密；不要放密码或秘密数据。
- 签名用于发现内容是否被修改，不能隐藏内容。
- 本课把演示密钥写在源码中只是为了练习。真实项目应从环境变量或密钥管理系统读取，并设计密钥轮换。
- 前端保存令牌时还需要考虑 XSS、CSRF 和刷新令牌策略，这些不在本课的三个 TODO 中展开。
