package org.example.lesson26;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TransactionExceptionHandler {
    @ExceptionHandler(InventoryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public TransactionErrorResponse handleNotFound(InventoryNotFoundException exception) {
        return new TransactionErrorResponse("INVENTORY_NOT_FOUND", exception.getMessage());
    }

    @ExceptionHandler(InsufficientStockException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public TransactionErrorResponse handleInsufficientStock(InsufficientStockException exception) {
        return new TransactionErrorResponse("INSUFFICIENT_STOCK", exception.getMessage());
    }

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public TransactionErrorResponse handleConcurrentUpdate(ObjectOptimisticLockingFailureException exception) {
        return new TransactionErrorResponse("CONCURRENT_UPDATE", "Inventory changed; retry the request");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public TransactionErrorResponse handleDuplicateOrder(DataIntegrityViolationException exception) {
        return new TransactionErrorResponse("DUPLICATE_ORDER", "Order ID already exists");
    }
}
