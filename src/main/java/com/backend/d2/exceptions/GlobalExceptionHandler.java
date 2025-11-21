package com.backend.d2.exceptions;

import com.backend.d2.dtos.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

// Esta clase "atrapa" las excepciones lanzadas por CUALQUIER controlador
// y las convierte en una respuesta JSON limpia.

@RestControllerAdvice // "Advice" para todos los RestControllers
public class GlobalExceptionHandler {

    // Clase interna simple para la respuesta de error
    private record ErrorResponse(String message, HttpStatus status, LocalDateTime timestamp) {}

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(BadRequestException ex) {

        ErrorResponse errorResponse = new ErrorResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {

        ErrorResponse errorResponse = new ErrorResponse(
                ex.getMessage(),
                HttpStatus.NOT_FOUND,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // Un manejador genérico por si se nos escapa algo
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {

        ErrorResponse errorResponse = new ErrorResponse(
                "An unexpected error occurred: " + ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorDTO> handleBusinessException(BusinessException ex){
        ErrorDTO error = new ErrorDTO(ex.getCode(), ex.getMessage());
        // Interesante
        HttpStatus status;
        switch (ex.getCode()) {
            case "BAD_REQUEST":
                status = HttpStatus.BAD_REQUEST;
                break;
            case "CONFLICT":
                status = HttpStatus.CONFLICT;
                break;
            case "NOT_FOUND":
                status = HttpStatus.NOT_FOUND;
                break;
            default:
                status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(status).body(error);
    }
}