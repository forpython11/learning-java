# Lesson 04: 接口与多态

> 状态：已完成（2026-08-24）

这一课使用统一的通知接口发送邮件和短信，理解 Java 的 `interface`、`implements` 和多态。

## 运行入口

打开 `src/main/java/org/example/lesson04/Main.java`，运行其中的 `main` 方法。

## 任务

按顺序完成三个 `TODO`：

1. 在 `EmailSender.send` 中打印邮件通知。
2. 在 `SmsSender.send` 中打印短信通知。
3. 在 `NotificationService.notifyUser` 中调用 `sender.send`。

完成后的预期输出：

```text
[Email] To ada@example.com: Your order has shipped
[SMS] To 13800000000: Your verification code is 9527
```

## TypeScript 与 Java 对照

TypeScript：

```typescript
interface NotificationSender {
  send(recipient: string, message: string): void;
}

class EmailSender implements NotificationSender {
  send(recipient: string, message: string): void {
    console.log(`[Email] To ${recipient}: ${message}`);
  }
}
```

Java：

```java
public interface NotificationSender {
    void send(String recipient, String message);
}

public class EmailSender implements NotificationSender {
    @Override
    public void send(String recipient, String message) {
        // 具体实现
    }
}
```

## 关键点

- `interface` 只规定能力，不关心具体如何实现。
- `implements` 表示一个类实现了接口规定的方法。
- `@Override` 表示当前方法正在实现或重写父类型的方法。
- `NotificationService` 依赖接口，因此可以同时配合邮件和短信实现工作。
