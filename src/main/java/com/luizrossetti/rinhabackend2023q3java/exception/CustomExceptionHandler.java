package com.luizrossetti.rinhabackend2023q3java.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler({Exception.class})
    public final ResponseEntity<ApiError> handleException(Exception ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();


        if (ex instanceof ExecutionException) {
            log.debug("ExecutionException: {}", ex.getMessage());
            if (ex.getCause() instanceof DataIntegrityViolationException) {
                List<String> errors = Collections.singletonList(ex.getMessage());
                HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
                return handleExceptionInternal(ex, new ApiError(status, errors), headers, status, request);
            } else if (ex.getCause() instanceof DateTimeParseException) {
                List<String> errors = Collections.singletonList(ex.getMessage());
                HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
                return handleExceptionInternal(ex, new ApiError(status, errors), headers, status, request);
            }
        } else if (ex instanceof MethodArgumentNotValidException) {
            List<String> errors = ((MethodArgumentNotValidException) ex).getBindingResult()
                    .getFieldErrors()
                    .stream()
                    .map(x -> "Campo: " + x.getField() + " - " + x.getDefaultMessage())
                    .collect(Collectors.toList());

            HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
            ApiError apiError = new ApiError(status, errors);
            return handleExceptionInternal(ex, apiError, headers, status, request);
        } else if (ex instanceof MissingServletRequestParameterException) {
            List<String> errors = Collections.singletonList(ex.getMessage());
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return handleExceptionInternal(ex, new ApiError(status, errors), headers, status, request);
        }

        List<String> errors = Collections.singletonList(ex.getMessage());
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return handleExceptionInternal(ex, new ApiError(status, errors), headers, status, request);
    }

    /**
     * A single place to customize the response body of all Exception types.
     */
    protected ResponseEntity<ApiError> handleExceptionInternal(Exception ex, ApiError body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(body, headers, status);
    }

}
