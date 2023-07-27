package com.ichtus.hotelmanagementsystem.exceptions;

import com.ichtus.hotelmanagementsystem.model.dto.error.ErrorDetail;
import com.ichtus.hotelmanagementsystem.model.dto.error.ValidationError;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionController extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler
    public ResponseEntity<?> locationNotFound(ResourceNotFoundException exception) {
        return ResponseEntity.badRequest()
                .body(
                        new ErrorDetail()
                                .setTitle("Location not found")
                                .setStatus(HttpStatus.NOT_FOUND.value())
                                .setTimeStamp(new Date().getTime())
                                .setDetail(exception.getMessage())
                                .setDeveloperMessage(exception.getClass().getName())
                );
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ErrorDetail errorDetail = new ErrorDetail()
                                        .setTitle("Message Not Readable")
                                        .setStatus(status.value())
                                        .setTimeStamp(new Date().getTime())
                                        .setDetail(ex.getMessage())
                                        .setDeveloperMessage(ex.getClass().getName());
        return handleExceptionInternal(ex, errorDetail, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ErrorDetail errorDetail = new ErrorDetail()
                                        .setTitle("Validation failed")
                                        .setStatus(status.value())
                                        .setTimeStamp(new Date().getTime())
                                        .setDetail(ex.getMessage())
                                        .setDeveloperMessage(ex.getClass().getName());

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        for (FieldError fe : fieldErrors) {
            List<ValidationError> validationErrorList = errorDetail.getErrors().computeIfAbsent(fe.getField(), k -> new ArrayList<>());
            ValidationError validationError = new ValidationError()
                    .setCode(fe.getCode())
                    .setMessage(messageSource.getMessage(fe, null));
            validationErrorList.add(validationError);
        }

        return handleExceptionInternal(ex, errorDetail, headers, status, request);
    }



}