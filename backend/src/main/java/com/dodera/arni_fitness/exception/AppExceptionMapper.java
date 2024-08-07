package com.dodera.arni_fitness.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AppExceptionMapper extends ResponseEntityExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(AppExceptionMapper.class);

    @ExceptionHandler({ Exception.class })
    protected ResponseEntity<Object> handleException(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = ex.getMessage();
        logger.error("Exception: " + bodyOfResponse, ex);
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

}
