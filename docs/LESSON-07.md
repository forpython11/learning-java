# Lesson 07: 使用 Optional 处理查询结果

> 状态：已完成（2026-08-25）

查询数据时可能找到对象，也可能什么都没有。`Optional<T>` 用类型明确表达“结果可能不存在”，避免直接返回 `null`。

## 运行入口

打开 `src/main/java/org/example/lesson07/Main.java`，运行其中的 `main` 方法。

## 任务

按顺序完成三个 `TODO`：

1. `BookRepository.findByIsbn`：用 `Optional.ofNullable` 包装 Map 查询结果。
2. `BookService.findTitleOrDefault`：找到书时返回标题，否则返回 `Unknown book`。
3. `BookService.requireBook`：找到书时返回对象，否则抛出 `IllegalArgumentException`。

完成后的预期输出：

```text
Found title: Clean Code
Missing title: Unknown book
Required lookup failed: Book not found: 999
```

## TypeScript 与 Java 对照

TypeScript：

```typescript
const book: Book | undefined = books.get(isbn);
const title = book?.title ?? "Unknown book";
```

Java：

```java
Optional<Book> book = repository.findByIsbn(isbn);
String title = book.map(Book::getTitle).orElse("Unknown book");
```

## 常用方法

| 方法 | 作用 |
| --- | --- |
| `Optional.ofNullable(value)` | value 可以是对象，也可以是 `null` |
| `optional.map(fn)` | 有值时转换，没有值时保持为空 |
| `optional.orElse(value)` | 没有值时提供默认值 |
| `optional.orElseThrow(fn)` | 没有值时抛出异常 |

## 提示

- Map 查询：`books.get(isbn)`。
- 获取标题的方法引用：`Book::getTitle`。
- 创建异常的 Lambda：`() -> new IllegalArgumentException(...)`。
