package com.ichtus.hotelmanagementsystem.exceptions;

import com.ichtus.hotelmanagementsystem.model.dto.error.ErrorDetail;
import com.ichtus.hotelmanagementsystem.model.dto.error.ValidationError;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.persistence.EntityExistsException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectDeletedException;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ExceptionController extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler
    public ResponseEntity<?> deletedObject(ObjectDeletedException exception) {
        return templateResponseException(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<?> notFoundObject(org.hibernate.ObjectNotFoundException exception) {
        return templateResponseException(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<?> badAuth(BadCredentialsException exception) {
        return templateResponseException(exception, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<?> alreadyExists(EntityExistsException exception) {
        return templateResponseException(
                "Object " + exception.getMessage() + " with such name already exists",
                exception,
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<?> badBookingDates(IncorrectDateForBookingException exception) {
        return templateResponseException("Incorrect period for booking", exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<?> noFreeDatesHandler(FreeDatesForRoomNotFountException exception) {
        return templateResponseException("Room already booked for this period", exception, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler
    public ResponseEntity<?> invalidToken(DefaultBadRequestException exception) {
        return templateResponseException(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<?> expiredJwtExceptionHandler(ExpiredJwtException exception) {
        return templateResponseException(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<?> accessDenied(AccessDeniedException exception) {
        return templateResponseException(exception, HttpStatus.FORBIDDEN);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, @NotNull HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ErrorDetail errorDetail = new ErrorDetail()
                                        .setTitle("Message Not Readable")
                                        .setStatus(status.value())
                                        .setTimeStamp(new Date().getTime())
                                        .setDetail(ex.getMessage())
                                        .setDeveloperMessage(ex.getClass().getName());
        return handleExceptionInternal(ex, errorDetail, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, @NotNull HttpHeaders headers, HttpStatusCode status, WebRequest request) {
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
                    .setMessage(messageSource.getMessage(fe, Locale.getDefault()));
            validationErrorList.add(validationError);
        }

        return handleExceptionInternal(ex, errorDetail, headers, status, request);
    }


    private ResponseEntity<?> templateResponseException(String message, Exception exception, HttpStatus status) {
        return ResponseEntity.status(status.value())
                .body(
                        new ErrorDetail()
                                .setTitle(message)
                                .setStatus(status.value())
                                .setTimeStamp(new Date().getTime())
                                .setDetail(exception.getMessage())
                                .setDeveloperMessage(exception.getClass().getName())
                );
    }

    private ResponseEntity<?> templateResponseException(Exception exception, HttpStatus status) {
        return templateResponseException(exception.getMessage(), exception, status);
    }

}
