package com.epassi.topkoccurence.controller;

import com.epassi.topkoccurence.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.epassi.topkoccurence.dto.enumeration.TopKErrorCodes.UNEXPECTED_EXCEPTION;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    protected ResponseEntity<ErrorResponse> handleException(Throwable e) {
        logger.error(e.getMessage(), e);
        ErrorResponse errorResponse = new ErrorResponse("top-k-service",
                UNEXPECTED_EXCEPTION.getErrorCode(),
                UNEXPECTED_EXCEPTION.getTitle(),
                e.getMessage(),
                e.getCause().getMessage(),
                null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse);
    }

}
