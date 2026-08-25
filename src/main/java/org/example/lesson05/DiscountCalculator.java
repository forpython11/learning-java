package org.example.lesson05;

public class DiscountCalculator {
    public double calculate(double originalPrice, int discountPercent) {
        if (originalPrice < 0) {
            throw new IllegalArgumentException("Original price cannot be negative");
        }

        if (discountPercent < 0 || discountPercent > 100) {
            throw new IllegalArgumentException("Discount percent must be between 0 and 100");
        }

        return originalPrice * (100 - discountPercent) / 100;
    }
}
