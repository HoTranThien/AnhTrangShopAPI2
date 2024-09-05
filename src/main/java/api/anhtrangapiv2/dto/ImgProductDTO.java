package api.anhtrangapiv2.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ImgProductDTO {
    @NotEmpty(message="link cannot be empty")
    private String link;
    @NotEmpty(message="productId cannot be empty")
    @Positive(message="productId must be a number")
    private Integer productId;
}
