# Learning Java

一个面向前端开发者的 Java 学习项目。课程使用 TypeScript/JavaScript 中熟悉的概念对照 Java，通过小型练习逐步学习语法、面向对象、集合、异常、测试和泛型。

完整的 32 课路线、阶段目标和最终项目见 [`docs/LEARNING-PLAN.md`](docs/LEARNING-PLAN.md)。个人阶段复盘和补缺清单见 [`docs/LEARNING-REVIEW.md`](docs/LEARNING-REVIEW.md)。

## 环境要求

- JDK 25
- Maven 3.9+
- IntelliJ IDEA（推荐）

项目的 Java 版本和依赖配置位于 [`pom.xml`](pom.xml)。

## 运行项目

最简单的方式是在 IntelliJ IDEA 中打开对应课程的 `Main.java`，点击 `main` 方法左侧的运行按钮。

使用 Maven 编译并运行全部测试：

```shell
mvn test
```

编译后也可以从终端运行指定课程，例如第七课：

```shell
java -cp target/classes org.example.lesson07.Main
```

如果终端找不到 `java` 或 `mvn`，请先配置 `JAVA_HOME`、Maven 和系统 `PATH`；IntelliJ 内置的 Maven 也可以直接构建本项目。

## 课程进度

| 课程 | 内容 | 状态 |
| --- | --- | --- |
| [Lesson 01](docs/LESSON-01.md) | 从 TypeScript 走进 Java | 已完成 |
| [Lesson 02](docs/LESSON-02.md) | 使用 Stream 处理商品列表 | 已完成 |
| [Lesson 03](docs/LESSON-03.md) | 订单状态与异常处理 | 已完成 |
| [Lesson 04](docs/LESSON-04.md) | 接口与多态 | 已完成 |
| [Lesson 05](docs/LESSON-05.md) | 使用 JUnit 编写单元测试 | 已完成 |
| [Lesson 06](docs/LESSON-06.md) | 使用 Map 按 ID 存取数据 | 已完成 |
| [Lesson 07](docs/LESSON-07.md) | 使用 Optional 处理查询结果 | 已完成 |
| [Lesson 08](docs/LESSON-08.md) | 使用泛型封装 API 响应 | 已完成 |
| [Lesson 09](docs/LESSON-09.md) | 使用 record 构建不可变 DTO | 已完成 |
| [Lesson 10](docs/LESSON-10.md) | 使用 BigDecimal 计算金额 | 已完成 |
| [Lesson 11](docs/LESSON-11.md) | 使用日期与时间 API | 已完成 |
| [Lesson 12](docs/LESSON-12.md) | 自定义业务异常 | 已完成 |
| [Lesson 13](docs/LESSON-13.md) | 使用 Jackson 处理 JSON | 已完成 |
| [Lesson 14](docs/LESSON-14.md) | 使用 HttpClient 请求 API | 已完成 |
| [Lesson 15](docs/LESSON-15.md) | 第一个 Spring Boot REST API | 已完成 |
| [Lesson 16](docs/LESSON-16.md) | REST 路由与参数 | 已完成 |
| [Lesson 17](docs/LESSON-17.md) | POST 请求与 DTO 校验 | 已完成 |
| [Lesson 18](docs/LESSON-18.md) | 分层架构与构造器注入 | 已完成 |
| [Lesson 19](docs/LESSON-19.md) | 全局异常处理 | 已完成 |
| [Lesson 20](docs/LESSON-20.md) | 配置、Profile 与日志 | 已完成 |
| [Lesson 21](docs/LESSON-21.md) | SQL 与 H2 入门 | 已完成 |
| [Lesson 22](docs/LESSON-22.md) | Spring Data JPA | 已完成 |
| [Lesson 23](docs/LESSON-23.md) | Entity 与 DTO 转换 | 已完成 |
| [Lesson 24](docs/LESSON-24.md) | 数据关系 | 已完成 |
| [Lesson 25](docs/LESSON-25.md) | 分页、排序与筛选 | 已完成 |
| [Lesson 26](docs/LESSON-26.md) | 事务与并发更新 | 已完成 |
| [Lesson 27](docs/LESSON-27.md) | 使用 Mockito 测试 Service | 已完成 |
| [Lesson 28](docs/LESSON-28.md) | 使用 MockMvc 编写 Controller 集成测试 | 已完成 |
| [Lesson 29](docs/LESSON-29.md) | Spring Security 与 CORS | 已完成 |
| [Lesson 30](docs/LESSON-30.md) | JWT 鉴权 | 已完成 |
| [Lesson 31](docs/LESSON-31.md) | OpenAPI 与 Docker | 进行中 |

## 项目结构

```text
learning-java/
├── AGENTS.md                     AI 协作与自动出题规则
├── docs/                         总计划、每课说明和练习要求
├── src/main/java/org/example/    Java 源代码
│   ├── Main.java                 第一课入口
│   ├── User.java                 第一课用户模型
│   ├── lesson02/                 第二课
│   ├── ...
│   ├── lesson08/                 第八课
│   ├── lesson09/                 第九课
│   ├── lesson10/                 第十课
│   ├── lesson11/                 第十一课
│   ├── lesson12/                 第十二课
│   ├── lesson13/                 第十三课
│   ├── lesson14/                 第十四课
│   ├── lesson15/                 第十五课（Spring Boot 应用入口）
│   ├── lesson16/                 第十六课（路径参数与查询参数）
│   ├── lesson17/                 第十七课（POST 与 DTO 校验）
│   ├── lesson18/                 第十八课（分层架构与依赖注入）
│   ├── lesson19/                 第十九课（全局异常处理）
│   ├── lesson20/                 第二十课（配置、Profile 与日志）
│   ├── lesson21/                 第二十一课（SQL 与 H2）
│   ├── lesson22/                 第二十二课（Spring Data JPA）
│   ├── lesson23/                 第二十三课（Entity 与 DTO 转换）
│   ├── lesson24/                 第二十四课（JPA 数据关系）
│   ├── lesson25/                 第二十五课（分页、排序与筛选）
│   ├── lesson26/                 第二十六课（事务与并发更新）
│   ├── lesson27/                 第二十七课（Mockito 服务测试）
│   ├── lesson28/                 第二十八课（MockMvc 集成测试）
│   ├── lesson29/                 第二十九课（Security 与 CORS）
│   ├── lesson30/                 第三十课（JWT 鉴权）
│   └── lesson31/                 第三十一课（OpenAPI 与 Docker）
├── src/main/resources/           Spring Boot 配置与数据库初始化脚本
├── src/test/java/                JUnit 测试代码
└── pom.xml                       Maven 项目配置
```

每一课放在独立的包中，避免修改新练习时覆盖已经完成的代码。

## 前端概念对照

| TypeScript / JavaScript | Java |
| --- | --- |
| `console.log()` | `System.out.println()` |
| `Array<T>` | `List<T>` |
| `Map<string, T>` | `Map<String, T>` |
| `array.filter/map/reduce` | Stream 的 `filter/map/sum` |
| `interface` | `interface` |
| `value \| undefined` | `Optional<T>` |
| 泛型 `ApiResponse<T>` | 泛型 `ApiResponse<T>` |
| `readonly` 数据对象 | `record` |
| 金额字符串 + decimal 库 | `BigDecimal` |
| `Date` / 日期库 | `LocalDate`、`LocalDateTime` |
| `class X extends Error` | 自定义类 `extends RuntimeException` |
| `JSON.stringify` / `JSON.parse` | Jackson `ObjectMapper` |
| `fetch` | Java `HttpClient` |
| Express 路由 | Spring `@RestController`、`@GetMapping` |
| `request.params` / `request.query` | `@PathVariable` / `@RequestParam` |
| Zod 表单校验 | Bean Validation、`@Valid` |
| 显式传入 service/repository | Spring 构造器注入 |
| Express 错误处理中间件 | Spring `@RestControllerAdvice` |
| `.env` / 构建环境变量 | Spring Profile 与配置文件 |
| SQLite / SQL 客户端 | H2 / Spring `JdbcTemplate` |
| ORM / 数据访问库 | Spring Data JPA / `JpaRepository` |
| 数据库模型 / API 类型 | Entity / DTO `record` |
| 嵌套对象与关联查询 | `@OneToMany` / `@ManyToOne` / `@EntityGraph` |
| 列表页参数与分页结果 | `Pageable` / `Page<T>` |
| ORM 事务回调与版本字段 | `@Transactional` / `@Version` |
| Jest / Vitest Mock | Mockito `when` / `verify` |
| Supertest / HTTP 集成测试 | MockMvc / `@SpringBootTest` |
| 前端路由守卫 / CORS 配置 | Spring Security / `SecurityFilterChain` |
| `localStorage` 中的登录令牌 | JWT / `Authorization: Bearer` |
| API schema / 前端构建镜像 | OpenAPI / Docker 镜像 |
| Jest / Vitest | JUnit |
| npm / package.json | Maven / pom.xml |

## 练习方式

1. 阅读 `docs` 中当前课程的目标和预期输出。
2. 只修改标有 `TODO` 的位置。
3. 每完成一个小任务就编译或运行一次。
4. 不只看程序是否运行，还要核对输出、异常类型和测试断言是否符合要求。
5. 完成课程后，将 `TODO` 标记为 `DONE` 并更新课程状态。
