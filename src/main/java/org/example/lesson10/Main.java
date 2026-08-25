package org.example.lesson10;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        PriceCalculator calculator = new PriceCalculator();

        BigDecimal unitPrice = new BigDecimal("99.90");
        int quantity = 3;
        BigDecimal discountRate = new BigDecimal("0.15");

        BigDecimal subtotal = calculator.calculateSubtotal(unitPrice, quantity);
        BigDecimal discountedTotal = calculator.applyDiscount(subtotal, discountRate);
        BigDecimal roundedTotal = calculator.roundMoney(discountedTotal);

        System.out.println("Subtotal: " + subtotal.toPlainString());
        System.out.println("Discounted total: " + roundedTotal.toPlainString());
    }
}
