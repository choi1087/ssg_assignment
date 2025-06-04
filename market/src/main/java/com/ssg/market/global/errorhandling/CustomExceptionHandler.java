package com.ssg.market.global.errorhandling;

import com.ssg.market.global.errorhandling.exception.ItemDuplicatedException;
import com.ssg.market.global.errorhandling.exception.EntityIsNullException;
import com.ssg.market.global.errorhandling.exception.ItemStockNotEnoughException;
import com.ssg.market.global.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(EntityIsNullException.class)
    public ResponseEntity<ErrorResponse> handleItemNotFound(EntityIsNullException ex, HttpServletRequest req) {
        ErrorResponse response = ErrorResponse.of(ex.getErrorCode(), ex.getMessage(), req.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ItemStockNotEnoughException.class)
    public ResponseEntity<ErrorResponse> handleItemStockNotEnough(ItemStockNotEnoughException ex, HttpServletRequest req) {
        ErrorResponse response = ErrorResponse.of(ex.getErrorCode(), ex.getMessage(), req.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ItemDuplicatedException.class)
    public ResponseEntity<ErrorResponse> handleItemDuplicated(ItemDuplicatedException ex, HttpServletRequest req) {
        ErrorResponse response = ErrorResponse.of(ex.getErrorCode(), ex.getMessage(), req.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
