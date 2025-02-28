package com.kolown.porring.common;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RuntimeExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(RuntimeExceptionHandler.class);

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleException(RuntimeException ex, HttpServletRequest request) {

        log.error("Runtime 에러가 발생했습니다. {} {}", request.getRequestURI(), ex.getMessage());

        Map<String, String> map = new HashMap<String, String>();
        map.put("error", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        map.put("statusCode", String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        map.put("message", ex.getMessage());
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
