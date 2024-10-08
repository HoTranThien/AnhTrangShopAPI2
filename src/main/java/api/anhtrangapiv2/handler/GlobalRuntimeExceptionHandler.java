package api.anhtrangapiv2.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import api.anhtrangapiv2.responses.ResponseToClient;

@ControllerAdvice
public class GlobalRuntimeExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseToClient> handleRuntimeExceptions(RuntimeException ex) {
        System.out.println("\u001B[31m" + "-----Errors: " + ex.getMessage() + "\u001B[0m");
        return ResponseEntity.badRequest().body(ResponseToClient.builder()
        .message(ex.getMessage())
        .status(HttpStatus.BAD_REQUEST)
        .data(null).build());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ResponseToClient> handleAccessDeniedExceptions(AccessDeniedException ex) {
        System.out.println("\u001B[31m" + "-----Errors: " + ex.getMessage() + "\u001B[0m");
        return ResponseEntity.badRequest().body(ResponseToClient.builder()
        .message(ex.getMessage())
        .status(HttpStatus.FORBIDDEN)
        .data(null).build());
    }
}
