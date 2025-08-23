package com.ean.api_weather.exceptions;

import com.ean.api_weather.DTOS.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final Counter notFoundCounter;
    private final Counter badRequestCounter;
    private final Counter internalServerErrorCounter;

    public GlobalExceptionHandler(MeterRegistry registry) {
        this.notFoundCounter = Counter.builder("api_errors_total")
                .description("Total de erros 404 Not Found")
                .tag("error_type", "not_found")
                .register(registry);

        this.badRequestCounter = Counter.builder("api_errors_total")
                .description("Total de erros 400 Bad Request")
                .tag("error_type", "bad_request")
                .register(registry);

        this.internalServerErrorCounter = Counter.builder("api_errors_total")
                .description("Total de erros 500 Internal Server Error")
                .tag("error_type", "internal_server_error")
                .register(registry);
    }


    //404
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceNotFound(ResourceNotFoundException ex, WebRequest request) {
        ErrorResponseDTO errorResponse = buildErrorResponse(
                HttpStatus.NOT_FOUND,
                "Not Found",
                ex.getMessage(),
                request
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // 400
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponseDTO> handleBadRequest(BadRequestException ex, WebRequest request) {
        ErrorResponseDTO errorResponse = buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Bad Request",
                ex.getMessage(),
                request
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // 500 - genérico para erros não tratados
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGlobalException(Exception ex, WebRequest request) {
        ErrorResponseDTO errorResponse = buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal Server Error",
                ex.getMessage(),
                request
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ErrorResponseDTO buildErrorResponse(HttpStatus status, String error, String message, WebRequest request) {
        return new ErrorResponseDTO(
                Instant.now(),
                status.value(),
                error,
                message,
                request.getDescription(false).replace("uri=", "")
        );
    }
}
