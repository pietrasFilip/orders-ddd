package com.app.infrastructure.api.controllers;

import com.app.application.dto.error.AppErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler({IllegalStateException.class, IllegalArgumentException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public AppErrorDto handleException(RuntimeException runtimeException) {
        return new AppErrorDto(runtimeException.getMessage());
    }
}
