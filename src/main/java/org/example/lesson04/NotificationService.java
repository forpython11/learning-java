package org.example.lesson04;

public class NotificationService {
    private final NotificationSender sender;

    public NotificationService(NotificationSender sender) {
        this.sender = sender;
    }

    public void notifyUser(String recipient, String message) {
        // DONE 3: 调用 sender 的 send 方法。
        sender.send(recipient,message);
    }
}
