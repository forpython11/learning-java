package org.example.lesson04;

public class Main {
    public static void main(String[] args) {
        NotificationService emailService = new NotificationService(new EmailSender());
        NotificationService smsService = new NotificationService(new SmsSender());

        emailService.notifyUser("ada@example.com", "Your order has shipped");
        smsService.notifyUser("13800000000", "Your verification code is 9527");
    }
}
