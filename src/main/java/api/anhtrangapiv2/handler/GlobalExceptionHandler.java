package api.anhtrangapiv2.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import api.anhtrangapiv2.responses.ResponseToClient;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseToClient> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> 
            errors.put(error.getField(), error.getDefaultMessage()));
        System.out.println("\u001B[31m" + "-----Errors: " + errors + "\u001B[0m");
        return ResponseEntity.badRequest().body(ResponseToClient.builder()
        .message("error")
        .status(HttpStatus.BAD_REQUEST)
        .data(errors).build());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<ResponseToClient> handleMissParameterExceptions(MissingServletRequestPartException ex) {
        String paramName = ex.getRequestPartName();
        String message = "Required parameter is missing: " + paramName;
        return ResponseEntity.badRequest().body(ResponseToClient.builder()
        .message("error")
        .status(HttpStatus.BAD_REQUEST)
        .data(message).build());
    }
}
