package api.anhtrangapiv2.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ParentCategoryDTO {
    @NotEmpty(message="Name cannot be empty")
    private String name;
}
