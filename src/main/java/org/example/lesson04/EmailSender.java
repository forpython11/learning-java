package org.example.lesson04;

public class EmailSender implements NotificationSender {
    @Override
    public void send(String recipient, String message) {
        // DONE 1: 按照讲义中的格式打印邮件通知。
        System.out.println("[Email] TO " +recipient+": "+message);
    }
}
