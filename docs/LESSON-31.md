# Lesson 31: OpenAPI 与 Docker

> 状态：进行中

上一课解决了“谁能访问接口”，本课解决“前端怎样读懂接口”和“应用怎样在一致环境中启动”。你会让 Spring 根据 Controller 生成 OpenAPI JSON，再把打包后的 jar 放进 Docker 镜像。

这些仍然是新 API，不要求凭空猜注解参数。先看类型地图，再逐个完成三个 TODO。

## 学习目标

- 理解 OpenAPI JSON、Swagger UI 和 Controller 之间的关系。
- 使用 `OpenAPI`、`Info`、`@Tag` 和 `@Operation` 补充接口文档。
- 理解 jar、Docker 镜像和容器的区别。
- 使用环境变量覆盖端口和应用显示名称。

## 运行方式

运行本课测试：

```shell
mvn -Dtest=org.example.lesson31.OpenApiAndDockerIntegrationTest test
```

初始 Java 骨架可以编译，三个测试带有 `@Disabled`。`Dockerfile` 初始时也是合法文件，但只有基础镜像和工作目录，不会启动应用；完成 TODO 3 后才具备运行能力。

## 类型地图

| Java / Docker | 作用 | TypeScript / 前端对照 |
| --- | --- | --- |
| `OpenAPI` | 整份接口说明对象 | 一份 API schema 配置对象 |
| `Info` | 文档标题、版本和描述 | `package.json` 的项目元信息 |
| `@Tag` | 给一组 Controller 接口分类 | 文档导航分组 |
| `@Operation` | 描述一个 HTTP 操作 | 给 fetch 封装函数写说明 |
| jar | 编译和打包后的 Java 应用 | 前端的 `dist` 构建产物 |
| Docker 镜像 | 应用及其运行环境模板 | 固定 Node 版本的构建镜像 |
| Docker 容器 | 镜像的一次运行实例 | 从同一构建产物启动的进程 |

## 请求流程

```text
CatalogController + OpenAPI 注解
    -> springdoc 扫描
    -> GET /v3/api-docs
    -> OpenAPI JSON
    -> Swagger UI / 前端开发者阅读
```

```text
mvn package
    -> target/learning-java-1.0-SNAPSHOT.jar
    -> docker build
    -> 镜像
    -> docker run
    -> 容器中的 Spring Boot 应用
```

## 任务

### TODO 1：设置 OpenAPI 基本信息

在 `OpenApiConfig.learningJavaOpenApi()` 中创建 `Info`，并设置：

```text
title       displayName
version     1.0
description Product catalog API for frontend integration
```

再把 `Info` 交给 `new OpenAPI().info(...)`。

值和类型的变化：

```text
displayName                     String
new Info().title(...)           Info
new OpenAPI().info(info)        OpenAPI
```

完成后删除第一个测试上的 `@Disabled`。

### TODO 2：描述商品接口

给 `CatalogController` 添加：

- 类上的 `@Tag(name = "Catalog", description = "Product catalog operations")`。
- `products()` 方法上的 `@Operation(summary = "List products")`。

注解本身不会改变接口返回值，只会给 springdoc 扫描时提供元数据。完成后删除第二个测试上的 `@Disabled`。

### TODO 3：完成 Dockerfile

在现有 `Dockerfile` 后依次添加：

```dockerfile
COPY target/learning-java-1.0-SNAPSHOT.jar app.jar
ENV SERVER_PORT=8080
ENV APP_DISPLAY_NAME=Learning-Java-Container
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

`SERVER_PORT` 会覆盖 Spring Boot 的 `server.port`，`APP_DISPLAY_NAME` 会被 `@Value` 读取并成为 OpenAPI 标题。完成后删除第三个测试上的 `@Disabled`。

## 预期结果

完成 TODO 1 和 TODO 2 后，启动应用并请求：

```http
GET /v3/api-docs
```

关键 JSON 内容：

```json
{
  "info": {
    "title": "Learning Java API",
    "description": "Product catalog API for frontend integration",
    "version": "1.0"
  },
  "paths": {
    "/api/lesson31/products": {
      "get": {
        "tags": ["Catalog"],
        "summary": "List products"
      }
    }
  }
}
```

商品接口仍然返回：

```json
[
  {"id":"P100","name":"Keyboard"},
  {"id":"P200","name":"Mouse"}
]
```

有 Docker 的环境中，先打包再构建和运行：

```shell
mvn clean package
docker build -t learning-java:lesson31 .
docker run --rm -p 8080:8080 -e APP_DISPLAY_NAME=My-Catalog learning-java:lesson31
```

此时 `/v3/api-docs` 中的标题应为 `My-Catalog`。

最终测试结果：

```text
Tests run: 3, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

## 提示

- 注解是给框架读取的元数据，不是普通方法调用。
- `ENV` 设置镜像内的默认值，`docker run -e` 可以在启动容器时覆盖它。
- `EXPOSE` 只是声明容器预期监听端口；真正映射到电脑端口的是 `-p 8080:8080`。
- 修改 Java 后要重新运行 `mvn package`，否则 Docker 复制的还是旧 jar。
