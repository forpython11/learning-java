package org.example.lesson11;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OrderTimeService {
    public LocalDate calculateDeliveryDate(LocalDate orderDate, int shippingDays) {
        // DONE 1: 在 orderDate 上增加 shippingDays 天。
        return orderDate.plusDays(shippingDays);
    }

    public String formatCreatedAt(LocalDateTime createdAt) {
        // DONE 2: 使用 yyyy-MM-dd HH:mm 格式返回创建时间。
        return createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public boolean isExpired(LocalDateTime expiresAt, LocalDateTime now) {
        // DONE 3: 判断 expiresAt 是否早于 now。
        return expiresAt.isBefore(now);
    }
}
