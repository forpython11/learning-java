package org.example.lesson08;

public class ApiResponse<T> {
    private final boolean success;
    private final T data;
    private final String message;

    private ApiResponse(boolean success, T data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
    }

    public static <T> ApiResponse<T> success(T data) {
        // TODO 1: 返回包含 data 的成功响应。
        return null;
    }

    public static <T> ApiResponse<T> failure(String message) {
        // TODO 2: 返回包含 message 的失败响应。
        return null;
    }

    public boolean isSuccess() {
        return success;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
