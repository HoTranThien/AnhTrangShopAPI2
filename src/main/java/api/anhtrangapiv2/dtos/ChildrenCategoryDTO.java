package api.anhtrangapiv2.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ChildrenCategoryDTO {

    @NotEmpty(message="Name cannot be empty")
    private String name;
    @Positive(message="pacaID must be a number")
    private Integer pacaId;
}
