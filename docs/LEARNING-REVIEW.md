# Java 学习阶段复盘与查漏补缺

> 复盘日期：2026-08-26
>
> 当前进度：已完成 Lesson 01 - Lesson 24，Lesson 25 进行中
>
> 当前阶段：已经能在提示下完成 Spring Boot + JPA 小型接口，正在从“看懂局部代码”过渡到“独立组织完整数据流”

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
| `Optional` / `List` / `Page` | 重点训练中 | 容易混淆“可能有一个”“有多个”和“带分页信息的多个” |
| Stream 数据流 | 重点训练中 | 能模仿 `map().toList()`，还需要练习说出每一步的类型 |
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

Lesson 25 的首要目标就是把 `List<T>` 和 `Page<T>` 区分稳定。

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

## Lesson 25 针对性训练

本课不要急着一次写完三个 TODO，按下面顺序练习：

- [ ] 写 Repository 方法前，说出两个参数类型和返回类型。
- [ ] 用一句话解释为什么返回 `Page<ProductEntity>`，而不是 `List<ProductEntity>`。
- [ ] 把 `result.getContent()` 的类型写在纸上或注释中。
- [ ] 先用 Lambda 完成商品 DTO 转换，再尝试方法引用。
- [ ] 说出 `PageRequest.of(page, size, sort)` 的返回类型。
- [ ] 分别请求第一页、第二页、名称筛选和非法页码。
- [ ] 在询问“对了吗”之前，先把自己的预期结果和实际结果进行一次比较。

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

完成 Lesson 25 和 Lesson 26 后，应达到：

- 能独立区分 `Optional<T>`、`List<T>` 和 `Page<T>`。
- 能沿着 `Controller -> Repository -> Entity -> DTO -> JSON` 说明类型变化。
- 能根据方法签名调用 Repository，而不是靠猜方法名和参数。
- 能解释常见 JPA 注解的作用，并区分固定参数名与业务属性值。
- 能独立验证分页、筛选、排序和至少一个错误分支。
- 能区分 Java 编译问题、Spring 接口问题和数据库服务连接问题。

达到这些标准后，再进入事务与并发更新时，重点就可以放在业务一致性，而不是继续被基础类型流打断。
