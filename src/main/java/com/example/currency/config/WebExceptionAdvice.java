package com.example.currency.config;

import com.example.currency.dto.HttpResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class WebExceptionAdvice {

    @ExceptionHandler(RuntimeException.class)
    public HttpResponseEntity handleRuntimeException(RuntimeException e) {

        log.error(e.toString(), e);
        return HttpResponseEntity.fail(e.getMessage());
    }
}
