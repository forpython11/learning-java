# Java 学习阶段复盘与查漏补缺

> 复盘日期：2026-08-31
>
> 当前进度：已完成 Lesson 01 - Lesson 28，Lesson 29 进行中
>
> 当前阶段：已经能在提示下使用 MockMvc 验证 HTTP 与数据库链路，开始学习 Spring Security 的认证、授权与 CORS

## 核心结论

最近连续完成了配置、日志、SQL、JPA、DTO 和数据关系课程，说明已经越过“完全看不懂 Spring”的阶段。现在的主要困难不是课程太难，也不是 Java 语法完全不会，而是几个概念叠在一起时，容易失去对类型和数据流的追踪。

目前最符合实际的学习状态是：

```text
看到一个局部 TODO
    -> 经过方法签名或示例提示后通常能写出来

看到一整段陌生链式代码
    -> 容易混淆容器类型、元素类型和方法返回值

代码能够编译
    -> 还不够习惯主动验证接口结果、错误分支和 SQL 行为
```

下一步不需要背更多长方法名或注解。重点是每写一行都能回答：输入是什么类型、输出是什么类型、值接下来去了哪里。

## 最近已经取得的进步

- 已完成 Spring Boot REST API 基础，包括 Controller、Service、Repository、全局异常处理、配置、Profile 和日志。
- 已经实际连接和启动过数据库，并开始区分“项目中的连接配置”和“Navicat 连接数据库服务器”是两件事。
- 能在提示后写出 Spring Data JPA 派生查询方法，例如按名称片段搜索、忽略大小写并排序。
- 能完成 `Entity -> DTO` 的基本转换，不再只关注数据库对象本身。
- 能使用 `stream().map(...).toList()` 转换集合，并在展开讲解后理解每一步的作用。
- 已完成分页查询的数据流：能声明返回 `Page<ProductEntity>` 的 Repository 方法，创建 `PageRequest`，并把当前页 Entity 转换成 DTO。
- 在指出差异后能改用 `getTotalElements()` 返回全部匹配记录数，最终接口的分页、排序、筛选和非法参数行为均通过实际请求验证。
- 能为库存 Entity 添加 `@Version`，并完成数量校验、库存不足异常和正常扣减。
- 能在逐步提示后完成 `@Transactional` Service：查询库存、修改受管理 Entity、保存订单并返回更新后的结果。
- 第 26 课的正常购买、重复订单回滚、库存不足、缺失商品、数量边界和并发冲突均通过实际请求验证。
- 已完成 Mockito Service 测试，能使用 Stub 安排查询结果、用断言检查返回值和异常，并用 `verify` 检查 Repository 交互。
- 能覆盖商品不存在和库存不足两个错误分支，并最终确认三个测试均执行、`Skipped: 0`。
- 已完成三个 MockMvc 集成测试：能准备 H2 数据、断言成功与 404 响应，并在 POST 后再次查询 Repository 核对持久化结果。
- 能完成一对多关系查询：正确使用 `@EntityGraph(attributePaths = "products")`，并把分类及其商品转换成嵌套 DTO。
- 遇到 `找不到符号 Optional` 时，能够根据提示补充导入并继续完成题目。
- 会主动询问“为什么这样写”，而不只是要求给出答案。这对形成长期理解比记模板更重要。

## 当前能力状态

| 能力 | 当前状态 | 具体表现 |
| --- | --- | --- |
| Java 基础语法 | 已有基础 | 能阅读类、方法、构造器、`record` 和常见集合代码 |
| Spring MVC | 正在形成 | 能理解路由、参数、状态码和全局异常处理，但注解较多时容易混乱 |
| Repository / JPA | 入门阶段 | 能按提示完成派生查询和关联加载，尚未稳定理解方法签名与查询行为 |
| DTO 转换 | 正在形成 | 能写 `from(entity)`，但有时会用 `BigDecimal.ZERO` 或空集合代替真实字段 |
| `Optional` / `List` / `Page` | 正在形成 | 最终能完成 `Page<ProductEntity>` 数据流，但开始时曾把分页查询写成 `Optional`，仍需独立练习 |
| Stream 数据流 | 重点训练中 | 能模仿 `map().toList()`，还需要练习说出每一步的类型 |
| 事务与并发 | 正在形成 | 能完成 `@Transactional` 和 `@Version` 代码，但事务步骤仍需要拆解提示 |
| 自动化测试 | 正在形成 | 能在提示下完成 Mockito 和 MockMvc 的成功与失败分支，但仍需主动检查断言、数据库状态和跳过数量 |
| 独立验证 | 需要加强 | 经常写完后询问“现在对了吗”，还未形成固定自测流程 |

## 最近暴露出的关键卡点

### 1. 容器类型和元素类型容易混在一起

最近先后混淆过 `Optional<CategoryEntity>` 和 `List<CategoryEntity>`。这两个类型不是写法不同，而是业务含义不同：

```text
CategoryEntity           一个分类
Optional<CategoryEntity> 可能找到一个分类，也可能没有
List<CategoryEntity>     零个、一个或多个分类
Page<ProductEntity>      当前页的多个商品 + 总数、页码等分页信息
```

判断时不要先看方法名，要先问查询最多会返回几个结果：

- 按唯一 ID 查询：通常是 `Optional<T>`。
- 查询全部或条件列表：通常是 `List<T>`。
- 分页列表：通常是 `Page<T>`。

Lesson 25 已经完成了这条数据流，但过程中仍需要提示才能稳定区分这些容器。后续遇到新 Repository 方法时继续先判断查询结果数量。

### 2. 方法调用前没有先核对签名

例如曾写过类似：

```java
repository.findAll(searchBy(keyword));
```

这里的问题不是括号或关键字，而是没有先确认：

```text
repository.findAll(...) 接受什么参数？
searchBy(keyword) 是谁的方法？返回什么类型？
题目真正要求调用的 Repository 方法名是什么？
```

以后调用陌生方法前固定检查三项：

```text
调用者的类型 -> 参数列表 -> 返回值类型
```

IntelliJ 中先使用参数提示或跳转到方法定义，比反复猜写法更快。

### 3. 会模仿 Stream，但类型流还不稳定

下面的代码已经见过多次：

```java
categories.stream()
        .map(CategoryResponse::from)
        .toList();
```

需要能独立说出完整过程：

```text
categories                         List<CategoryEntity>
categories.stream()                Stream<CategoryEntity>
map(CategoryResponse::from)        Stream<CategoryResponse>
toList()                           List<CategoryResponse>
```

方法引用看不懂时，先展开成：

```java
.map(category -> CategoryResponse.from(category))
```

确认 Lambda 输入和输出后，再缩写回方法引用。

### 4. DTO 转换不能使用占位值冒充真实数据

`ProductResponse.from(entity)` 的目标是转换，不是创建默认商品：

```text
entity.getId()     -> response.id
entity.getName()   -> response.name
entity.getPrice()  -> response.price
```

因此 `BigDecimal.ZERO`、`"TODO"`、`List.of()` 只能是未完成骨架中的占位值。程序能够编译不代表行为正确，必须检查每个响应字段来自哪里。

### 5. 对注解的理解容易停留在“是不是固定语法”

最近重点问过 `@ExceptionHandler`、`@EntityGraph` 和 `attributePaths`。应把注解拆成两层：

```text
@EntityGraph                     注解类型，名称由框架固定
attributePaths                   这个注解定义的参数名，名称固定
"products"                      参数值，不固定，必须对应 Entity 的 Java 属性名
```

学习注解不需要一次记住全部参数。先确认：这个注解贴在哪里、由谁读取、解决什么问题、当前填写的值指向什么。

### 6. 数据库服务器和项目配置的边界还需要巩固

Navicat 报 `2002 - Can't connect to server on '127.0.0.1'` 时，表示客户端没有连接到正在监听该地址和端口的数据库服务器，不是 Java 代码编译错误。

需要区分三部分：

```text
MySQL 服务             必须已经安装并启动
Navicat                使用 host、port、username、password 连接服务
Spring Boot 项目       在配置中使用 JDBC URL 和同一组账号连接服务
```

连接信息可以放在项目配置中，但真实密码不应提交到 Git。应使用环境变量或本机未跟踪配置。

### 7. 仍然过度依赖外部确认

最近多次在完成一小段代码后询问“现在对了吗”。及时确认可以避免走偏，但长期目标是先完成一轮自己的验收，再请求复核。

固定自检顺序：

1. 当前方法返回类型是否匹配？
2. 是否仍有占位值或未完成 `TODO`？
3. Repository 方法的参数和返回类型是否符合查询数量？
4. 正常接口的状态码和 JSON 是否精确正确？
5. 不存在、非法参数等错误分支是否正确？
6. 完整 `mvn test` 是否通过？

## 写每个 TODO 前的五个问题

1. 当前方法承诺返回什么类型？
2. 我要调用的方法属于哪个对象？
3. 这个方法需要什么参数，又返回什么类型？
4. 返回的是一个值、`Optional`、`List`，还是 `Page`？
5. 返回值中的每个字段最终来自哪里？

如果其中一项说不清，先把链式代码拆成带明确类型的局部变量，再继续写。

## Lesson 25 完成记录

本课已完成，实际表现如下：

- [x] 写出 Repository 方法的两个参数类型和 `Page<ProductEntity>` 返回类型。
- [ ] 用一句话解释为什么返回 `Page<ProductEntity>`，而不是 `List<ProductEntity>`。
- [x] 根据编译器提示识别 `result.getContent()` 是 `List<ProductEntity>`，并转换为 `List<ProductResponse>`。
- [x] 使用 `ProductResponse::from` 完成当前页 DTO 转换。
- [x] 用明确的 `PageRequest` 局部变量接住 `PageRequest.of(...)` 的返回值。
- [ ] 独立请求第一页、第二页、名称筛选和非法页码，并逐项核对结果。
- [ ] 在询问“对了吗”之前，先把自己的预期结果和实际结果进行一次比较。

本课反复出现的两个问题需要继续留意：调用方法后没有接住返回值，以及字段名称相近时只看“像不像”而没有核对语义。例如 `getNumberOfElements()` 是当前页数量，`getTotalElements()` 才是所有匹配记录数。

## Lesson 26 针对性训练

- [ ] 画出“查询库存 -> 扣减库存 -> 保存订单 -> 提交或回滚”的执行顺序。
- [ ] 解释为什么库存扣减和订单保存必须位于同一个事务。
- [ ] 分清 Java 对象中的 `stock` 变化、SQL 刷新和事务提交三个时刻。
- [ ] 使用重复 `orderId` 制造保存失败，并确认库存没有被扣减。
- [ ] 用一句话解释 `@Version` 如何阻止两个并发请求互相覆盖。

本课代码已经完成，但概念表达和自主验证仍可继续补强。实现过程中曾把库存不足条件写成相反方向；TODO 3 在拆成“查询、扣减、保存、响应”后才能继续。说明你可以完成每个局部步骤，但还需要练习先自行画出完整流程。

## Lesson 27 针对性训练

- [x] 使用 Stub（安排返回值）、Assertion（验证结果）和 Verify（验证调用）完成三个测试。
- [ ] 不依赖提示，用自己的话解释 Stub、Assertion 和 Verify 的职责差异。
- [x] 成功分支同时验证返回库存和两个 Repository 的保存调用。
- [x] 失败分支不仅断言异常，还验证没有发生写操作。
- [x] 确认最终测试结果为 `Skipped: 0`，避免空测试或禁用测试造成假绿色。

本课最终实现正确，但过程中出现了三个值得保留的提醒：Stub 使用 `p100` 而实际调用 `P100`，导致严格参数不匹配；测试一度没有断言却显示通过；完成代码后仍保留 `@Disabled` 或 `fail(...)`，造成跳过或无条件失败。说明已经能写出 Mockito API，但还需要形成“参数精确一致、结果与交互都验证、占位标记全部清理”的固定检查习惯。

## Lesson 28 针对性训练

- [ ] 区分 Mockito 单元测试中的 Mock Repository 与集成测试中的真实 H2 Repository。
- [x] 使用 Repository 准备数据，再用 MockMvc 发送 GET 请求并检查状态码和 JSON。
- [x] 完成 404 错误分支，核对错误码和消息，而不只检查请求没有抛异常。
- [x] 使用 MockMvc 发送 POST JSON，并再次查询 Repository 确认数据确实持久化。
- [x] 确认最终测试结果为 `Failures: 0`、`Errors: 0`、`Skipped: 0`。

本课最终实现正确，但过程中先后出现了静态调用实例方法、缺少 `get` 和 `MediaType` 导入、局部变量名不一致，以及只检查响应却忘记核对数据库状态等问题。说明已经能够顺着示例完成 MockMvc 链式断言，下一步要把“调用者是谁、静态导入来自哪里、HTTP 之后还要验证什么状态”变成固定自检步骤。

## Lesson 29 针对性训练

- [ ] 区分认证（你是谁）和授权（你能访问什么）。
- [ ] 为公开、登录用户和管理员接口分别配置访问规则。
- [ ] 使用 HTTP Basic 测试真实用户名和密码，而不只使用模拟用户。
- [ ] 允许 `http://localhost:5173` 的跨域预检请求，并解释浏览器为什么先发送 `OPTIONS`。
- [ ] 根据 `200`、`401`、`403` 判断请求到达了哪一层。

## 建议的复习方式

继续新课，不需要从 Lesson 01 全部重学。每次学习采用下面的短循环：

```text
读一个 TODO
    -> 标出输入类型和返回类型
    -> 只写这一小步
    -> 编译或请求接口
    -> 对比精确结果
    -> 用自己的话解释为什么
```

每完成一课，再从旧课中选择一个表达式展开。例如把方法引用展开为 Lambda，把链式调用拆成局部变量，或者把注解的参数名和值分开说明。

## 下一阶段目标

完成 Lesson 29 后，应达到：

- 能独立区分 `Optional<T>`、`List<T>` 和 `Page<T>`。
- 能沿着 `Controller -> Repository -> Entity -> DTO -> JSON` 说明类型变化。
- 能根据方法签名调用 Repository，而不是靠猜方法名和参数。
- 能解释常见 JPA 注解的作用，并区分固定参数名与业务属性值。
- 能独立验证分页、筛选、排序和至少一个错误分支。
- 能使用 Mockito 隔离 Repository，并验证 Service 的成功和失败行为。
- 能使用 MockMvc 验证 HTTP 状态码、JSON 响应和 H2 数据库状态。
- 能配置公开、已登录和指定角色三种访问规则，并区分 `401` 与 `403`。
- 能为前端开发服务器配置精确的 CORS 来源、方法和请求头。
- 能区分 Java 编译问题、Spring 接口问题和数据库服务连接问题。

达到这些标准后，再进入 JWT 时，就能理解令牌如何替代 HTTP Basic 携带用户身份。
