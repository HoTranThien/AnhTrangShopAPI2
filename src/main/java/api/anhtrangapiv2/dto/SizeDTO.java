package api.anhtrangapiv2.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SizeDTO {
    @NotEmpty(message="Name cannot be empty")
    private String name;
}
