package com.ecommerce.facturation.exception.advice;

import com.ecommerce.facturation.exception.TransactionNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class AppExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String> handleInvalidArgument(MethodArgumentNotValidException exp){
        Map<String,String> errors = new HashMap<>();
        exp.getBindingResult().getFieldErrors().forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage())
        );
        return errors;
    }
    /*
    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<?> handleTransactionNotFound(TransactionNotFoundException exp){
        Map<String,String> errors = new HashMap<>();
        errors.put("errorMessage", exp.getMessage());
        return ResponseEntity.badRequest().body(errors);
    }*/

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFound(EntityNotFoundException exp){
        Map<String,String> errors = new HashMap<>();
        errors.put("errorMessage", exp.getMessage());
        return ResponseEntity.badRequest().body(errors);
    }
}
