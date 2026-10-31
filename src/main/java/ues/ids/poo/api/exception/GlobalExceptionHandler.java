package ues.ids.poo.api.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import ues.ids.poo.api.dto.response.ApiErrorResponse;

import java.time.Instant;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationException(
        MethodArgumentNotValidException exception,
        HttpServletRequest request
    ) {
        String message = exception.getBindingResult().getAllErrors().stream()
            .map(ObjectError::getDefaultMessage)
            .filter(Objects::nonNull)
            .findFirst()
            .orElse("La solicitud contiene errores de validación");

        ApiErrorResponse body = new ApiErrorResponse(
            Instant.now(),
            HttpStatus.BAD_REQUEST.value(),
            HttpStatus.BAD_REQUEST.getReasonPhrase(),
            message,
            request.getRequestURI()
        );

        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiErrorResponse> handleResponseStatusException(
        ResponseStatusException exception,
        HttpServletRequest request
    ) {
        HttpStatusCode status = exception.getStatusCode();
        HttpStatus knownStatus = HttpStatus.resolve(status.value());
        String error = knownStatus != null ? knownStatus.getReasonPhrase() : "HTTP " + status.value();
        String message = exception.getReason() != null ? exception.getReason() : error;

        ApiErrorResponse body = new ApiErrorResponse(
            Instant.now(),
            status.value(),
            error,
            message,
            request.getRequestURI()
        );

        return ResponseEntity.status(status).body(body);
    }
}
