package org.example.lesson05;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DiscountCalculatorTest {
    private final DiscountCalculator calculator = new DiscountCalculator();

    @Test
    void shouldKeepOriginalPriceWhenDiscountIsZero() {
        double actualPrice = calculator.calculate(200.0, 0);

        assertEquals(200.0,actualPrice,0.001);
    }

    @Test
    void shouldApplyTwentyPercentDiscount() {
        double actualPrice = calculator.calculate(200.0, 20);

       assertEquals(160.0,actualPrice,0.001);
    }

    @Test
    void shouldRejectDiscountAboveOneHundredPercent() {
        assertThrows(
                IllegalArgumentException.class,
                () -> calculator.calculate(100.0, 120)
        );
    }
}
