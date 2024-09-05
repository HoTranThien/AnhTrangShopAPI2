package api.anhtrangapiv2.responses;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResponseToClient {
    private String message;
    private HttpStatus status;
    private Object data;
}
