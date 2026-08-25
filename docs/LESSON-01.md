# Lesson 01: 从 TypeScript 走进 Java

> 状态：已完成（2026-08-24）

本课代码实现了一个简单的用户列表：筛选已启用的成年用户，然后统计人数。

## 先运行

在 IntelliJ IDEA 中打开 `Main.java`，点击 `main` 方法旁边的运行按钮。

预期输出：

```text
=== User Management ===
Ada (age 20)
James (age 32)
Active adult users: 2
```

## TypeScript 与 Java 对照

| TypeScript | Java | 含义 |
| --- | --- | --- |
| `const title = "Users"` | `String title = "Users"` | Java 通常明确写出类型 |
| `number` | `int` | 本例中的整数类型 |
| `boolean` | `boolean` | 布尔类型 |
| `User[]` | `List<User>` | 用户集合 |
| `new User(...)` | `new User(...)` | 创建对象 |
| `user.active` | `user.isActive()` | 通过方法读取私有字段 |
| `console.log(...)` | `System.out.println(...)` | 控制台输出 |

`public static void main(String[] args)` 是传统且通用的 Java 程序入口：

- `public`：JVM 可以从类外访问它。
- `static`：不创建 `Main` 对象也能调用它。
- `void`：方法没有返回值。
- `String[] args`：接收命令行参数。

## 动手练习

1. 新增一名年龄为 18 且已启用的用户，观察统计结果。
2. 把成年年龄 `adultAge` 改成 21，预测结果后再运行验证。
3. 进阶：给 `User` 增加 `email` 字段，并在 `printUser` 中输出。
