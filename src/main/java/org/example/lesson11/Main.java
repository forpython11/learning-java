package org.example.lesson11;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        OrderTimeService service = new OrderTimeService();

        LocalDate orderDate = LocalDate.of(2026, 8, 25);
        LocalDateTime createdAt = LocalDateTime.of(2026, 8, 25, 9, 30);
        LocalDateTime expiresAt = LocalDateTime.of(2026, 8, 25, 10, 0);
        LocalDateTime now = LocalDateTime.of(2026, 8, 25, 10, 30);

        System.out.println("Delivery date: " + service.calculateDeliveryDate(orderDate, 3));
        System.out.println("Created at: " + service.formatCreatedAt(createdAt));
        System.out.println("Expired: " + service.isExpired(expiresAt, now));
    }
}
