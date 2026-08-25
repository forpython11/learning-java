package org.example.lesson09;

public record CreateProductRequest(String name, double price) {
    public CreateProductRequest {
        // TODO 1: name 为 null 或空白时抛出 IllegalArgumentException。

        // TODO 2: price 小于或等于 0 时抛出 IllegalArgumentException。
    }
}
