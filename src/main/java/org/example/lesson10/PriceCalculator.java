package org.example.lesson10;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PriceCalculator {
    public BigDecimal calculateSubtotal(BigDecimal unitPrice, int quantity) {
        // TODO 1: 使用 unitPrice 乘以 quantity。
        return BigDecimal.ZERO;
    }

    public BigDecimal applyDiscount(BigDecimal subtotal, BigDecimal discountRate) {
        // TODO 2: 使用 subtotal 乘以 (1 - discountRate)。
        return BigDecimal.ZERO;
    }

    public BigDecimal roundMoney(BigDecimal amount) {
        // TODO 3: 使用 HALF_UP 将 amount 保留两位小数。
        return BigDecimal.ZERO;
    }
}
