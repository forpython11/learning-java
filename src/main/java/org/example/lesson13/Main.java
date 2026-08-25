package org.example.lesson13;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) throws JsonProcessingException {
        JsonConverter converter = new JsonConverter();
        Product product = new Product("P100", "Keyboard", new BigDecimal("299.90"));

        String json = converter.toJson(product);
        System.out.println("JSON: " + json);

        String incomingJson = "{\"id\":\"P101\",\"product_name\":\"Mouse\",\"price\":159.50}";
        Product restored = converter.fromJson(incomingJson);
        System.out.println("Restored: " + restored.id() + " - " + restored.name() + " - " + restored.price());
    }
}
