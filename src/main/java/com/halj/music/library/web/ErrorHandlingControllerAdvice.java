package com.halj.music.library.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * The Class ErrorHandlingControllerAdvice.
 * Handler for validation errors to display more readable validation error messages
 */
@RestControllerAdvice
class ErrorHandlingControllerAdvice {

    /**
     * Handle validation exceptions.
     *
     * @param ex the exception
     * @return the map of fields not valid
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }

    /**
     * Handle data integrity exceptions.
     *
     * @return the string
     */
    @ResponseStatus(value = HttpStatus.CONFLICT) // 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleDataConflict() {
        return "Integrity constraint error: check that values in fields like 'email' are not already used";
    }
}
