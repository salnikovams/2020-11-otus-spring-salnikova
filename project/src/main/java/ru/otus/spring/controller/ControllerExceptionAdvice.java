package ru.otus.spring.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.otus.spring.dto.ErrorResponse;
import ru.otus.spring.exception.InputParameterException;


@ControllerAdvice
public class ControllerExceptionAdvice {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(InputParameterException e) {

        ErrorResponse loadErrorResponse = ErrorResponse.builder()
                .errorCode(HttpStatus.BAD_REQUEST.value())
                .errorDescription(e.getMessage())
                .build();
        return ResponseEntity.badRequest().body(loadErrorResponse);
    }
}
