package api.anhtrangapiv2.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ColorDTO {
    @NotEmpty(message="Name cannot be empty")
    private String name;
    @NotEmpty(message="Code cannot be empty")
    private String code;
}
