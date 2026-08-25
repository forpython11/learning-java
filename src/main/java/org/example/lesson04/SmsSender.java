package org.example.lesson04;

public class SmsSender implements NotificationSender {
    @Override
    public void send(String recipient, String message) {
        // DONE 2: 按照讲义中的格式打印短信通知。
        System.out.println("[SMS] To "+recipient+": "+message);
    }
}
