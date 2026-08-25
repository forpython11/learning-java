package org.example.lesson19;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // TODO 2: 声明这个方法处理 ProductNotFoundException。
    public ResponseEntity<ApiError> handleProductNotFound(ProductNotFoundException exception) {
        // TODO 3: 返回 404 和统一的 ApiError 响应体。
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
