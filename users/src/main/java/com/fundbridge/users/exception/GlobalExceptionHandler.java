package com.fundbridge.users.exception;

import com.fundbridge.users.exception.InvalidCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .get(0)
                .getDefaultMessage();

        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", 400);
        response.put("error", "Bad Request");
        response.put("message", errorMessage);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Map<String,Object>>handleInvalidCredentials(InvalidCredentialsException ex){
        Map<String,Object>error=new HashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("status",401);
        error.put("error","Unauthorized");
        error.put("message",ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ResponseEntity<Map<String,Object>>handleResourceAlreadyExist(ResourceAlreadyExistException ex){
        Map<String,Object>error=new HashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("status",409);
        error.put("error",HttpStatus.CONFLICT.getReasonPhrase());
        error.put("message",ex.getMessage());
        return new ResponseEntity<>(error,HttpStatus.CONFLICT);
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String,Object>>handleResourceNotFound(ResourceNotFoundException ex){
        Map<String,Object>error=new HashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("status",404);
        error.put("error",HttpStatus.NOT_FOUND.getReasonPhrase());
        error.put("message",ex.getMessage());
        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(FileDeleteException.class)
    public ResponseEntity<Map<String,Object>>handleFileDelete(ResourceNotFoundException ex){
        Map<String,Object>error=new HashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("status",500);
        error.put("error",HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        error.put("message",ex.getMessage());
        return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Map<String,Object>>HandleUnauthorized(UnauthorizedException ex){
        Map<String,Object>error=new HashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("status",401);
        error.put("error",HttpStatus.UNAUTHORIZED.getReasonPhrase());
        error.put("message",ex.getMessage());
        return new ResponseEntity<>(error,HttpStatus.UNAUTHORIZED);
    }
}
