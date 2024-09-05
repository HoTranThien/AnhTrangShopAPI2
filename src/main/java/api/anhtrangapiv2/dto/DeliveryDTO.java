package api.anhtrangapiv2.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeliveryDTO {
    @NotEmpty(message="Name cannot be empty")
    private String name;
    @NotNull
    @Min(value = 0,message="Cost must be greater than or equal to 0")
    private Long cost;
}
