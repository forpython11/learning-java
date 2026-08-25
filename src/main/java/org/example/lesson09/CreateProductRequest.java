package org.example.lesson09;

public record CreateProductRequest(String name, double price) {
    public CreateProductRequest {
        // DONE 1: name 为 null 或空白时抛出 IllegalArgumentException。
        if(name==null||name.isBlank()){
            throw new IllegalArgumentException("name must not be null or empty");
        }
        // DONE 2: price 小于或等于 0 时抛出 IllegalArgumentException。
        else if(price<=0){
            throw new IllegalArgumentException("Price must be greater than 0");
        }

    }
}
