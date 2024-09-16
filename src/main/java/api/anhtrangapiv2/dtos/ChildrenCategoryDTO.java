package api.anhtrangapiv2.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ChildrenCategoryDTO {

    @NotEmpty(message="Name cannot be empty")
    private String name;
    @Min(value = 0, message = "pacaId must be >= 0")
    private Integer pacaId;
}
