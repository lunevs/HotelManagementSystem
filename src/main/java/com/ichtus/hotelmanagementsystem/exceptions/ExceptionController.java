package com.ichtus.hotelmanagementsystem.exceptions;

import com.ichtus.hotelmanagementsystem.model.dto.error.ErrorDetail;
import com.ichtus.hotelmanagementsystem.model.dto.error.ValidationError;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.persistence.EntityExistsException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectDeletedException;
import org.hibernate.ObjectNotFoundException;
import org.springframework.context.MessageSource;
import org.springframework.data.crossstore.ChangeSetPersister;
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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Custom exception handler class. It handles all application exceptions
 * @author smlunev
 */
@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionController extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler({ObjectDeletedException.class, DefaultBadRequestException.class})
    public ResponseEntity<?> deletedObject(Exception exception) {
        return templateResponseException(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ExpiredJwtException.class})
    public ResponseEntity<?> tokenExpired(Exception exception) {
        return templateResponseException("Token expired", exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ChangeSetPersister.NotFoundException.class, ObjectNotFoundException.class, HttpClientErrorException.NotFound.class})
    public ResponseEntity<?> notFoundObject(Exception exception) {
        return templateResponseException(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<?> badAuth(BadCredentialsException exception) {
        return templateResponseException(exception, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<?> accessDenied(AccessDeniedException exception) {
        return templateResponseException(exception, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleMaxUploadSizeExceeded(MaxUploadSizeExceededException exception) {
        return templateResponseException("File size exceeds the limit", exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleMultipartException(MultipartException exception) {
        return templateResponseException("Error occurred during file upload", exception, HttpStatus.INTERNAL_SERVER_ERROR);
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

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            @NotNull NoHandlerFoundException ex,
            @NotNull HttpHeaders headers,
            @NotNull HttpStatusCode status,
            @NotNull WebRequest request) {
        ErrorDetail errorDetail = createDefaultErrorDetail("Handler Not Found", ex, status.value());
        return handleExceptionInternal(ex, errorDetail, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            @NotNull HttpMessageNotReadableException ex,
            @NotNull HttpHeaders headers, HttpStatusCode status,
            @NotNull WebRequest request) {
        ErrorDetail errorDetail = createDefaultErrorDetail("Message Not Readable", ex, status.value());
        return handleExceptionInternal(ex, errorDetail, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            @NotNull MethodArgumentNotValidException ex,
            @NotNull HttpHeaders headers,
            @NotNull HttpStatusCode status,
            @NotNull WebRequest request) {
        ErrorDetail errorDetail = createDefaultErrorDetail("Validation failed", ex, status.value());
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        for (FieldError fe : fieldErrors) {
            List<ValidationError> validationErrorList = errorDetail
                    .getErrors()
                    .computeIfAbsent(fe.getField(), k -> new ArrayList<>());
            ValidationError validationError = new ValidationError()
                    .setCode(fe.getCode())
                    .setMessage(messageSource.getMessage(fe, Locale.getDefault()));
            validationErrorList.add(validationError);
        }

        return handleExceptionInternal(ex, errorDetail, headers, status, request);
    }


    private ResponseEntity<?> templateResponseException(String message, Exception exception, HttpStatus status) {
        return ResponseEntity.status(status.value())
                .body(createDefaultErrorDetail(message, exception, status.value()));
    }

    private ResponseEntity<?> templateResponseException(Exception exception, HttpStatus status) {
        return templateResponseException(exception.getMessage(), exception, status);
    }

    private ErrorDetail createDefaultErrorDetail(String message, Exception exception, int statusValue) {
        return new ErrorDetail()
                .setTitle(message)
                .setStatus(statusValue)
                .setTimeStamp(new Date().getTime())
                .setDetail(exception.getMessage())
                .setDeveloperMessage(exception.getClass().getName());
    }

}
