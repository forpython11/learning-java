# Learning Java

一个面向前端开发者的 Java 学习项目。课程使用 TypeScript/JavaScript 中熟悉的概念对照 Java，通过小型练习逐步学习语法、面向对象、集合、异常、测试和泛型。

完整的 32 课路线、阶段目标和最终项目见 [`docs/LEARNING-PLAN.md`](docs/LEARNING-PLAN.md)。

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
| [Lesson 09](docs/LESSON-09.md) | 使用 record 构建不可变 DTO | 进行中 |

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
│   └── lesson09/                 第九课
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
| Jest / Vitest | JUnit |
| npm / package.json | Maven / pom.xml |

## 练习方式

1. 阅读 `docs` 中当前课程的目标和预期输出。
2. 只修改标有 `TODO` 的位置。
3. 每完成一个小任务就编译或运行一次。
4. 不只看程序是否运行，还要核对输出、异常类型和测试断言是否符合要求。
5. 完成课程后，将 `TODO` 标记为 `DONE` 并更新课程状态。
