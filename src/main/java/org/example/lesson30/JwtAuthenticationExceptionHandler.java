package org.example.lesson30;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class JwtAuthenticationExceptionHandler {
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public AuthErrorResponse handleAuthenticationException() {
        return new AuthErrorResponse(
                "INVALID_CREDENTIALS",
                "Username or password is incorrect"
        );
    }
}
