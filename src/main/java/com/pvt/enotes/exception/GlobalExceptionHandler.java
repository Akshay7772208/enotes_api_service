package com.pvt.enotes.exception;

import com.pvt.enotes.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        log.error("GlobalExceptionHandler :: handleException ::", e.getMessage());
       // return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> handleNuLLPointerException(Exception e){
        log.error("GlobalExceptionHandler :: handleException :: NullPointerException", e.getMessage());
        //return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(Exception e){
        log.error("GlobalExceptionHandler :: handleResourceNotFoundException ::", e.getMessage());
        //return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        log.error("GlobalExceptionHandler :: handleMethodArgumentNotValidException ::", e.getMessage());
        //return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleValidationException(ValidationException e){
        log.error("GlobalExceptionHandler :: handleValidationException ::", e.getMessage());
        //return new ResponseEntity<>(e.getErrors(), HttpStatus.BAD_REQUEST);
        return CommonUtil.createErrorResponse(e.getErrors(), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(ExistDataException.class)
    public ResponseEntity<?> handleExistDataException(ExistDataException e){
        log.error("GlobalExceptionHandler :: handleExistDataException ::", e.getMessage());
        //return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        return CommonUtil.createErrorResponseMessage(e.getMessage(), HttpStatus.CONFLICT);

    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e){
        log.error("GlobalExceptionHandler :: handleHttpMessageNotReadableException ::", e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
