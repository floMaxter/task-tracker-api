package com.projects.tasktracker.user.web.controller;

import com.projects.tasktracker.user.exception.UserNotFoundException;
import com.projects.tasktracker.user.support.ConstraintMessageResolver;
import com.projects.tasktracker.user.web.dto.response.ErrorResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final ConstraintMessageResolver constraintMessageResolver;

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        log.warn("[Handle] User not found (UserNotFoundException): {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.warn("[Handle] Validation exception (MethodArgumentNotValidException): {}", ex.getMessage());

        var message = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining("; "));

        return buildValidationErrorResponse(message);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        log.warn("[Handle] Validation exception (ConstraintViolationException): {}", ex.getMessage());

        var message = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining("; "));

        return buildValidationErrorResponse(message);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        log.warn("[Handle!!] Validation exception (DataIntegrityViolationException): {}", ex.getMessage());

        String message = constraintMessageResolver.resolveMessage(
                Optional.ofNullable(ex.getRootCause())
                        .map(Throwable::getMessage)
                        .orElse("")
        );

        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(message));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        log.warn("[Handle] Unexpected error (Exception): {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorResponse("Internal error"));
    }

    private ResponseEntity<ErrorResponse> buildValidationErrorResponse(String message) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorResponse(message));
    }
}
