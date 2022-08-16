package com.buyern.buyern.exception;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"unchecked", "rawtypes"})
@ControllerAdvice(basePackages = {"com.buyern.buyern.Controllers"})
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    public static final org.slf4j.Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest webRequest) {
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());

        logger.error("Exception stacktrace: {}", ex.getMessage());
        return new ResponseEntity<>(new ErrorResponse("91", "Error!", details), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public final ResponseEntity<Object> handleRecordNotFoundException(RecordNotFoundException ex, WebRequest webRequest) {

        logger.error("RecordNotFoundException stacktrace: {}", ex.getMessage());
        return new ResponseEntity<>(new ErrorResponse("05", ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public final ResponseEntity<Object> handleEntityAlreadyExistsException(EntityAlreadyExistsException ex, WebRequest webRequest) {

        logger.error("EntityAlreadyExistsException stacktrace: {}", ex.getMessage());
        return new ResponseEntity<>(new ErrorResponse("04", ex.getMessage()), HttpStatus.CONFLICT);
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public final ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
//        Map<String, String> errors = new HashMap<>();
//        ex.getBindingResult().getAllErrors().forEach((error) -> {
//            String fieldName = ((FieldError) error).getField();
//            String errorMessage = error.getDefaultMessage();
//            errors.put(fieldName, errorMessage);
//        });
//        logger.error("MethodArgumentNotValidException stacktrace: {}", ex.getMessage());
//        return new ResponseEntity<>(new ErrorResponse("04", ex.getMessage(), errors), HttpStatus.BAD_REQUEST);
//    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        logger.error("MethodArgumentNotValidException stacktrace: {}", ex.getMessage());

        List<String> details = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }
        return new ResponseEntity<>(new ErrorResponse("03", "Bad Request", details), HttpStatus.BAD_REQUEST);
    }

}
