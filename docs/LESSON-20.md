# Lesson 20: 配置、Profile 与日志

> 状态：已完成（2026-08-25）

真实项目不会把不同环境的配置写死在 Java 代码里。本课使用 `application.yml` 提供默认配置，使用 `application-dev.yml` 覆盖开发环境配置，并通过 SLF4J 记录结构化日志。

## 运行入口

打开 `src/main/java/org/example/lesson20/Lesson20Application.java`，运行 `main` 方法。

使用默认配置启动：

```shell
mvn spring-boot:run
```

如果 `8080` 已被占用，可以添加程序参数：

```text
--server.port=8085
```

使用开发环境 Profile 启动时，再增加：

```text
--spring.profiles.active=dev
```

## 任务

1. 在 `application.yml` 中把 `app.catalog-name` 设置为 `Product Catalog`。
2. 在 `application-dev.yml` 中把同一个配置设置为 `Development Catalog`。
3. 在 `CatalogController.info` 中使用日志占位符记录目录名称和当前 Profile，日志格式为 `Catalog requested: name={}, profile={}`。

## 默认配置验证

不指定 Profile 启动后请求：

```http
GET /api/config
```

预期结果：

```text
HTTP 200
{"catalogName":"Product Catalog","profile":"default"}
```

控制台日志应包含：

```text
Catalog requested: name=Product Catalog, profile=default
```

## dev Profile 验证

添加以下程序参数重新启动：

```text
--spring.profiles.active=dev --server.port=8085
```

请求：

```http
GET /api/config
```

预期结果：

```text
HTTP 200
{"catalogName":"Development Catalog","profile":"dev"}
```

## 配置覆盖顺序

```text
application.yml
    -> 提供所有环境都能使用的默认值

application-dev.yml
    -> dev Profile 激活时覆盖同名配置
```

`@Value("${app.catalog-name}")` 会读取最终生效的值。`Environment.getActiveProfiles()` 可以读取当前激活的 Profile；没有显式激活时，本课把它显示为 `default`。

## TypeScript 对照

可以把它理解为前端构建工具或 Node.js 中的环境配置：

```typescript
const catalogName = process.env.CATALOG_NAME ?? "Product Catalog";
console.info("Catalog requested", {
  name: catalogName,
  profile: process.env.NODE_ENV ?? "default",
});
```

Spring Profile 类似按环境加载 `.env.development`，但配置不仅能来自 YAML，也能被环境变量和启动参数覆盖。

## 为什么使用日志占位符

推荐：

```java
log.info("Catalog requested: name={}, profile={}", catalogName, profile);
```

不要为了日志先拼接字符串。占位符写法更清晰，也便于日志框架在对应日志级别未启用时避免不必要的字符串构造。

## 提示

- YAML 使用缩进表达层级，不使用大括号。
- `catalog-name` 要放在 `app` 下方，并缩进两个空格。
- `log.info` 的第一个 `{}` 对应 `catalogName`，第二个对应 `profile`。
