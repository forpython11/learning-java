package org.example.lesson19;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// 对所有 Controller 生效的全局异常处理器，返回值会被转换为 JSON 响应体。
@RestControllerAdvice
public class GlobalExceptionHandler {
    // DONE 2: 声明这个方法处理 ProductNotFoundException。
    // ProductNotFoundException.class 表示要匹配的异常类型。
    @ExceptionHandler(ProductNotFoundException.class)
    // Spring 捕获异常后，会把异常对象传入 exception 参数。
    // ResponseEntity<ApiError> 同时描述 HTTP 状态码和 ApiError 类型的响应体。
    public ResponseEntity<ApiError> handleProductNotFound(ProductNotFoundException exception) {
        // DONE 3: 返回 404 和统一的 ApiError 响应体。
        // HttpStatus.NOT_FOUND 对应 HTTP 404。
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                // 创建错误响应体；Spring 会把 ApiError 自动序列化为 JSON。
                .body(new ApiError(
                        "PRODUCT_NOT_FOUND",
                        // 读取抛出异常时保存的具体消息。
                        exception.getMessage()
                ));
    }
}
