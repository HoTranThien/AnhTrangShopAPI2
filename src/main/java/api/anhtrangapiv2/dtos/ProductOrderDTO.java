package api.anhtrangapiv2.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductOrderDTO {

    @NotBlank(message="Size can't be blank")
    @NotNull(message="Size can't be null")
    private String size;
    @NotBlank(message="Color can't be blank")
    @NotNull(message="Color can't be null")
    private String color;
    @NotBlank(message="Quantity can't be blank")
    @Min(value = 1,message = "Quantity must be > 0")
    private int quantity;
    @NotBlank(message="Product Id can't be blank")
    @Min(value = 1,message = "Product Id must be > 0")
    private int productId;
}
