package org.example.lesson28;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProductExceptionHandler {
    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ProductErrorResponse handleNotFound(ProductNotFoundException exception) {
        return new ProductErrorResponse("PRODUCT_NOT_FOUND", exception.getMessage());
    }
}
