package com.ichtus.hotelmanagementsystem.exceptions;

import com.ichtus.hotelmanagementsystem.model.dto.error.ErrorDetail;
import com.ichtus.hotelmanagementsystem.model.dto.error.ValidationError;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
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
@Slf4j
public class ExceptionController extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler
    public ResponseEntity<?> handleAuthenticationException(AuthenticationException ex) {
        return badRequestTemplateResponse(
                "Authentication failed at controller advice",
                ex,
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<?> signatureExceptionHandler(SignatureException exception) {
        return badRequestTemplateResponse("Invalid token", exception, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<?> malformedJwtExceptionHandler(MalformedJwtException exception) {
        return badRequestTemplateResponse("Invalid token", exception, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<?> expiredJwtExceptionHandler(ExpiredJwtException exception) {
        return badRequestTemplateResponse("Token expired", exception, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<?> unsupportedJwtExceptionHandler(UnsupportedJwtException exception) {
        return badRequestTemplateResponse("Invalid token", exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<?> illegalArgumentExceptionHandler(IllegalArgumentException exception) {
        return badRequestTemplateResponse("Invalid token", exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<?> accessDenied(AccessDeniedException exception) {
        return badRequestTemplateResponse("Access Denied", exception, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<?> locationNotFound(HotelNotFoundException exception) {
        return badRequestTemplateResponse("Location not found", exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<?> roleNotFound(RoleNotFoundException exception) {
        return badRequestTemplateResponse("Role not found", exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<?> accountNotFound(AccountNotFoundException exception) {
        return badRequestTemplateResponse("User not found", exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<?> notUniqField(DataIntegrityViolationException exception) {
        return badRequestTemplateResponse("Duplicate name found", exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<?> badAuth(BadAuthException exception) {
        return badRequestTemplateResponse("Incorrect login or password", exception, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<?> userAlreadyExists(AccountAlreadyExists exception) {
        return badRequestTemplateResponse("User with such name already exists", exception, HttpStatus.BAD_REQUEST);
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


    private ResponseEntity<?> badRequestTemplateResponse(String message, Exception exception, HttpStatus status) {
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


}
