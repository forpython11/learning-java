# Java 学习阶段复盘与查漏补缺

> 复盘日期：2026-08-25
> 当前进度：已完成 Lesson 01 - Lesson 23，Lesson 24 进行中

## 核心结论

目前最大的问题不是记不住 Java 语法，而是还没有稳定形成下面这个解题习惯：

```text
先看当前方法必须返回什么类型
    -> 再看准备调用的方法返回什么类型
    -> 追踪这个值下一步去了哪里
    -> 最后处理为空、失败和异常分支
```

遇到陌生代码时，容易先猜一个看起来相似的方法名或写法，再交给编译器验证。例如调用方法后没有接住返回值、给方法传错参数、混淆对象和类、选择了不符合语义的异常类型。这些表面上是不同问题，根源大多是没有先确认“值和类型怎样流动”。

当前处于“有提示可以完成，面对空白题目还不够稳定”的阶段。下一步不需要追求背更多 API，而要训练自己拆解类型、方法签名和执行流程。

## 已经具备的能力

- 能持续根据编译错误和运行结果修改代码，而不是只停留在阅读。
- 已经完成 Java 基础、集合、接口、异常、泛型、`record`、金额、日期、JSON、HTTP 和 Spring Boot 入门练习。
- 对 HTTP 方法、JSON、状态码和前后端联调已有前端经验，理解 Spring Web 会比纯新手快。
- 得到一个小提示后，通常能完成剩余代码，说明问题主要在拆题方法，不是理解能力不足。
- 已经能够写出 Repository、Service、Controller 三层的基本调用链。

## 需要优先补强的问题

### 1. 类型与数据流追踪

这是当前优先级最高的问题。

典型卡点包括：

- 不清楚 `Book book = books.get(isbn)` 中左右两边分别是什么。
- 调用了 `service.findById(id)`，但没有使用它返回的 `Optional<Product>`。
- 看到 `Optional<Product>`、`ResponseEntity<Product>` 时，容易只关注语法，没有继续追踪内部的 `Product` 如何变化。
- 对 `Book::getTitle`、`ResponseEntity::ok` 这类方法引用不容易立即看出输入和输出。

固定分析方式：

```java
Optional<Product> optional = service.findById(id);
ResponseEntity<Product> response = optional
        .map(product -> ResponseEntity.ok(product))
        .orElseGet(() -> ResponseEntity.notFound().build());
return response;
```

每一行都写清楚类型后，再缩写成链式调用。方法引用看不懂时，先展开成 Lambda。

重点复习：Lesson 06、07、08、18。

### 2. 方法签名与 API 使用

写方法调用前，还不够习惯确认以下三项：

```text
谁来调用：对象还是类
传入什么：参数数量和类型
得到什么：返回值类型
```

例如：

```java
unitPrice.multiply(new BigDecimal(quantity))
```

- 调用者：`unitPrice`，类型是 `BigDecimal`。
- 参数：另一个 `BigDecimal`。
- 返回值：新的 `BigDecimal`。
- 原对象不会被修改，所以必须接住或返回结果。

之前在 `multiply`、`Product.class`、`toString()`、`assertEquals` 等位置的疑问，都可以用这三项拆解。不要凭方法名猜参数，优先使用 IntelliJ 的参数提示或跳转到方法定义。

重点复习：Lesson 05、10、13、14。

### 3. 正常分支、空分支和异常分支

目前容易先完成“正常能运行”的路径，忽略数据不存在、状态不合法或输入错误时应该发生什么。

需要建立三分支意识：

```text
正常数据 -> 返回结果
数据不存在 -> Optional.empty / 404 / NotFoundException
数据不合法或状态错误 -> 对应的业务异常
```

尤其要分清：

| 情况 | 常用表达 |
| --- | --- |
| 调用参数本身不合法 | `IllegalArgumentException` |
| 对象当前状态不允许操作 | `IllegalStateException` |
| 业务场景需要稳定错误码 | 自定义业务异常 |
| 查询结果可能不存在 | `Optional<T>` |
| Web 资源不存在 | 通常转换为 HTTP `404` |

异常消息也不是异常类型。`throw new IllegalStateException("ProductNotFoundException")` 仍然抛出的是 `IllegalStateException`，字符串不会把它变成 `ProductNotFoundException`。

重点复习：Lesson 03、06、07、12、19。

### 4. 类、对象与职责边界

对“类在哪里定义”“为什么这里用 Service 而不是 Repository”还需要更稳定的整体认识。

```text
Product.class           -> Product 这个类型本身的描述
new Product(...)        -> 创建一个 Product 对象
Product product         -> 声明一个保存 Product 对象的变量
product.name()          -> 调用某个 Product 对象的方法
```

Spring 分层中要同时明确职责：

```text
Controller 处理 HTTP 输入和响应
Service 组织业务规则
Repository 查询和保存数据
```

Service 不应该创建 HTTP 响应，Repository 也不应该决定状态码。先判断代码属于哪一层，再选择要使用的类型。

重点复习：Lesson 04、09、15、18。

### 5. 编译、运行与 HTTP 错误的分层诊断

目前看到报错时容易把不同阶段的问题混在一起。建议先判断错误发生在哪一层：

| 现象 | 所在阶段 | 第一检查点 |
| --- | --- | --- |
| `找不到符号` | 编译期 | 包名、类名、导入、文件位置 |
| `ClassNotFoundException` | 启动期 | 是否编译、包名与运行配置是否一致 |
| 程序退出码为 `0` 但无输出 | 运行期 | 是否真的调用了打印或接口方法 |
| Java 异常堆栈 | 运行期 | 从最底部 `Caused by` 和自己代码行号看起 |
| HTTP `415` | Web 请求解析前 | 实际 `Content-Type` 与 Body 类型是否一致 |
| HTTP `400` | 请求解析或校验 | JSON 格式、字段类型、校验规则 |
| HTTP `404` | 路由或资源查询 | URL 映射是否正确、数据是否存在 |
| HTTP `500` | 服务端执行 | 异常类型、堆栈和全局异常处理 |

重点复习：Lesson 14 - Lesson 19。

### 6. 独立验证与测试意识

现在更多依赖“能运行”和人工询问是否正确，还没有形成自己验收正常与错误分支的固定流程。

每题完成前至少检查：

- [ ] 项目能编译。
- [ ] 正常输入得到精确预期结果。
- [ ] 一个边界或不存在的输入得到预期结果。
- [ ] 异常类型和消息都正确。
- [ ] HTTP 状态码和响应 JSON 都正确。
- [ ] 测试断言验证的是业务结果，不只是让测试变绿。

重点复习：Lesson 03、05、06、16、17、18。

## 写代码前的五个问题

以后每个 TODO 下笔前，先用注释或草稿回答：

1. 当前方法承诺返回什么类型？
2. 我要调用的方法需要什么参数？
3. 这个调用会返回什么类型？
4. 返回值会被 `return`、赋值、继续转换，还是被丢掉？
5. 找不到、输入错误或状态错误时应该返回什么或抛出什么？

如果其中任意一项说不清，先不要猜代码。用 IntelliJ 查看方法签名，或者把链式表达式拆成多个带明确类型的局部变量。

## 针对性补缺清单

### A. 类型和方法调用

- [ ] 不看答案，解释 `Book book = books.get(isbn)` 每一部分的类型。
- [ ] 把一个 `Optional.map(...).orElse(...)` 链拆成三个局部变量，并标出类型。
- [ ] 把 `ResponseEntity::ok` 改写成等价 Lambda，再改回来。
- [ ] 对三个陌生方法，先在 IntelliJ 查看签名，再写调用代码。

### B. Java 核心

- [ ] 重做 Lesson 06 的保存、查询和重复 ID 分支。
- [ ] 重做 Lesson 07 的 `ofNullable`、`map`、`orElseThrow`。
- [ ] 重做 Lesson 10 的乘法、折扣和两位小数处理。
- [ ] 为 Lesson 03 的非法金额和非法状态分别说出正确异常类型。

### C. Spring 与 HTTP

- [ ] 画出 `HTTP -> Controller -> Service -> Repository -> Map` 的调用链。
- [ ] 独立发出一次正确 JSON POST，并解释 `201`、`400`、`415` 的区别。
- [ ] 不看 Lesson 18 答案，重新写出存在返回 `200`、不存在返回 `404` 的查询。
- [ ] 完成 Lesson 19 后，解释为什么 Service 不应该返回 `ResponseEntity`。

### D. 测试与排错

- [ ] 为一个方法同时写正常分支和异常分支测试。
- [ ] 看到编译错误时，先说出错误阶段和准确代码位置再修改。
- [ ] 看到异常堆栈时，从最底层业务异常向上找到自己的第一处代码。
- [ ] 每次接口练习同时验证成功请求和一个失败请求。

## 建议的复习节奏

不要暂停后续课程重新刷完全部内容。采用“继续新课 + 定点回补”的方式：

1. 每次新课开始前，从补缺清单选择一项，用 10 分钟复习。
2. 当前题先独立思考 10 分钟，至少写出输入类型、返回类型和失败分支。
3. 需要提示时，先描述自己已经确认的类型和卡住的表达式，不只说“没思路”。
4. 每完成三课，重新检查本清单，把能独立完成的项目打勾。

## 下一阶段目标

到 Lesson 20 结束时，目标不是完全脱离资料，而是达到下面的状态：

- 能沿着方法签名说明一段代码的数据流。
- 能区分编译错误、运行异常和 HTTP 错误。
- 能独立完成包含 Controller、Service、Repository 的小型查询接口。
- 能为不存在的数据选择正确异常，并转换成统一的 HTTP 错误响应。
- 遇到陌生 API 时先查签名和类型，而不是随机尝试参数。

达到这些标准后，再进入数据库和 JPA 阶段会顺畅很多。
