package com.enterprise.exchange.exception;

import com.enterprise.exchange.dto.response.ApiResponse;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ExchangeException.class)
    public ResponseEntity<ApiResponse<Void>> exchange(ExchangeException e) {
        return ResponseEntity.badRequest().body(ApiResponse.failure(e.getErrorCode().getCode(), e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> validation(MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest().body(ApiResponse.failure(ErrorCode.VALIDATION_ERROR.getCode(), ErrorCode.VALIDATION_ERROR.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> unexpected(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.failure(ErrorCode.INTERNAL_ERROR.getCode(), ErrorCode.INTERNAL_ERROR.getMessage()));
    }
}
