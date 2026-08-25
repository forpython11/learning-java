# Lesson 08: 使用泛型封装 API 响应

> 状态：已完成（2026-08-25）

泛型使用类型占位符编写可复用代码。这一课用 `ApiResponse<T>` 同时包装用户对象和角色列表。

## 运行入口

打开 `src/main/java/org/example/lesson08/Main.java`，完成三个 `TODO` 后运行 `main` 方法。

## 任务

1. `ApiResponse.success`：创建成功响应，保存传入的 `data`。
2. `ApiResponse.failure`：创建失败响应，保存传入的 `message`。
3. `Main.printResponse`：成功时打印数据，失败时打印错误消息。

完成后的预期输出：

```text
Success: U001 - Ada
Success: [ADMIN, EDITOR]
Failure: User not found
```

## TypeScript 与 Java 对照

TypeScript：

```typescript
interface ApiResponse<T> {
  success: boolean;
  data: T | null;
  message: string | null;
}
```

Java：

```java
public class ApiResponse<T> {
    private final boolean success;
    private final T data;
    private final String message;
}
```

`T` 是类型占位符：

```text
ApiResponse<UserProfile>  → T 是 UserProfile
ApiResponse<List<String>> → T 是 List<String>
```

## 提示

成功响应的构造参数：

```java
new ApiResponse<>(true, data, null)
```

失败响应的构造参数：

```java
new ApiResponse<>(false, null, message)
```

泛型方法需要在返回类型前声明 `<T>`：

```java
private static <T> void printResponse(ApiResponse<T> response)
```
