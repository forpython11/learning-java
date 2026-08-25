package org.example.lesson11;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class OrderTimeService {
    public LocalDate calculateDeliveryDate(LocalDate orderDate, int shippingDays) {
        // TODO 1: 在 orderDate 上增加 shippingDays 天。
        return orderDate;
    }

    public String formatCreatedAt(LocalDateTime createdAt) {
        // TODO 2: 使用 yyyy-MM-dd HH:mm 格式返回创建时间。
        return "";
    }

    public boolean isExpired(LocalDateTime expiresAt, LocalDateTime now) {
        // TODO 3: 判断 expiresAt 是否早于 now。
        return false;
    }
}
