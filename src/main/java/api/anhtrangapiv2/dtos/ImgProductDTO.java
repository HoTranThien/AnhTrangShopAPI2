package api.anhtrangapiv2.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ImgProductDTO {
    @NotEmpty(message="link cannot be empty")
    private String link;
    @NotEmpty(message="productId cannot be empty")
    @Min(value = 0,message="productId must be a number")
    private Integer productId;
}
