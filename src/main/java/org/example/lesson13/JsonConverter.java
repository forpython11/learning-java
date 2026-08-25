package org.example.lesson13;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonConverter {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String toJson(Product product) throws JsonProcessingException {
        // DONE 2: 将 product 序列化为 JSON 字符串。
        return objectMapper.writeValueAsString(product);
    }

    public Product fromJson(String json) throws JsonProcessingException {
        // DONE 3: 将 JSON 字符串反序列化为 Product。
        return objectMapper.readValue(json, Product.class);
    }
}
