package org.example.lesson10;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PriceCalculator {
    public BigDecimal calculateSubtotal(BigDecimal unitPrice, int quantity) {
        // DONE 1: 使用 unitPrice 乘以 quantity。

        return unitPrice.multiply(new BigDecimal(quantity));
    }

    public BigDecimal applyDiscount(BigDecimal subtotal, BigDecimal discountRate) {
        // DONE 2: 使用 subtotal 乘以 (1 - discountRate)。
        return subtotal.multiply(BigDecimal.ONE.subtract(discountRate));
    }

    public BigDecimal roundMoney(BigDecimal amount) {
        // DONE 3: 使用 HALF_UP 将 amount 保留两位小数。
        return amount.setScale(2,RoundingMode.HALF_UP);
    }
}
