package com.kolown.porring.common;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 모든 예외 처리 (일반적인 예외 처리)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(HttpServletRequest request, RuntimeException ex) {
        log.error("Runtime Error By Global Exception Handler\n {} {}\n {}",
                request.getRequestURI(), ex.getMessage(), ex.getStackTrace());

        Map<String, String> map = new HashMap<String, String>();

        map.put("error", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        map.put("statusCode", String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        map.put("message", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(map);
    }
}
