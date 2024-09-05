package api.anhtrangapiv2.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import api.anhtrangapiv2.responses.ResponseToClient;

@ControllerAdvice
public class GlobalRuntimeExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseToClient> handleRuntimeExceptions(RuntimeException ex) {
        return ResponseEntity.badRequest().body(ResponseToClient.builder()
        .message("error")
        .status(HttpStatus.BAD_REQUEST)
        .data(ex.getMessage()).build());
    }
}
