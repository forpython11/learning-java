package org.example.lesson13;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonConverter {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String toJson(Product product) throws JsonProcessingException {
        // TODO 2: 将 product 序列化为 JSON 字符串。
        return "";
    }

    public Product fromJson(String json) throws JsonProcessingException {
        // TODO 3: 将 JSON 字符串反序列化为 Product。
        return new Product("", "", null);
    }
}
