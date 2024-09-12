package com.harmlessprince.todomanagerapplication.exceptions;

import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Component
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) throws Exception {
        logger.info("Exception: E enter here ooooooooooooo");
        BaseCustomException baseCustomException = new BaseCustomException(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(baseCustomException, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CustomException.class)
    public final ResponseEntity<Object> handleAllCustomException(Exception ex, WebRequest request) throws Exception {

//        ex.printStackTrace();
        BaseCustomException baseCustomException = new BaseCustomException(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(baseCustomException, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<Object> handleAllNotFoundException(Exception ex, WebRequest request) throws Exception {

//        ex.printStackTrace();
        BaseCustomException baseCustomException = new BaseCustomException(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(baseCustomException, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(BadCredentialsException.class)
    public final ResponseEntity<Object> handleBadCredentialsException(Exception ex, WebRequest request) throws Exception {

        BaseCustomException baseCustomException = new BaseCustomException(
                LocalDateTime.now(),
                "Invalid credentials",
                request.getDescription(false));
        return new ResponseEntity<>(baseCustomException, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public final ResponseEntity<Object> handleAccessDeniedException(Exception ex, WebRequest request) throws Exception {
        logger.info("AccessDeniedException: E enter here ooooooooooooo");
        BaseCustomException baseCustomException = new BaseCustomException(
                LocalDateTime.now(),
                "forbidden",
                request.getDescription(false));
        return new ResponseEntity<>(baseCustomException, HttpStatus.FORBIDDEN);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        BaseCustomException baseCustomException = new BaseCustomException(
                LocalDateTime.now(), ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(baseCustomException, HttpStatus.BAD_REQUEST);
    }
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        BaseCustomException baseCustomException = new BaseCustomException(
                LocalDateTime.now(), "Validation Failed",
                request.getDescription(true), errors);

        return new ResponseEntity<>(baseCustomException, HttpStatus.UNPROCESSABLE_ENTITY);

    }

//    @Override
//    protected ResponseEntity<Object> handBad


}
