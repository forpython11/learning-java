package org.example.lesson19;

// 统一描述接口的错误响应体，避免不同接口返回不同的错误结构。
// 序列化后的 JSON 形如：{"code":"PRODUCT_NOT_FOUND","message":"Product not found: P999"}。
public record ApiError(String code, String message) {
}
