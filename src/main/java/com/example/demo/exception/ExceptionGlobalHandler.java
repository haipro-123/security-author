package com.example.demo.exception;

import com.example.demo.dto.response.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.security.NoSuchAlgorithmException;
import java.util.Date;

@RestControllerAdvice
public class ExceptionGlobalHandler {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionGlobalHandler.class);
    // create error response template
    private ErrorResponse getErrorResponseTemplate(Exception ex, WebRequest request){
        ex.printStackTrace();
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(new Date());
        errorResponse.setPath(request.getDescription(false).replace("uri=",""));
        errorResponse.setMessage(ex.getMessage());
        return errorResponse;
    }
    //handle common exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex, WebRequest request){
        logger.error(ex.getMessage(),ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(getErrorResponseTemplate(ex, request));
    }
    //hanlde validate by anotation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getErrorResponseTemplate(ex, request));
    }
    //hanlde param is null in function jpa
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(getErrorResponseTemplate(ex, request));
    }
    //hanlde param is null in my function
    @ExceptionHandler(IllegalArgumentInMyFunctionException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentInMyFunctionException(IllegalArgumentInMyFunctionException ex, WebRequest request){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(getErrorResponseTemplate(ex, request));
    }
    //handle not found record
    @ExceptionHandler(NotFoundRecordInDataException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundRecordInDataException(NotFoundRecordInDataException ex, WebRequest request){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(getErrorResponseTemplate(ex, request));
    }
    // handle bad Credential
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex, WebRequest request){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(getErrorResponseTemplate(ex, request));
    }
    //handle security exception
    @ExceptionHandler(NoSuchAlgorithmException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchAlgorithmException(NoSuchAlgorithmException ex, WebRequest request){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(getErrorResponseTemplate(ex, request));
    }
}
