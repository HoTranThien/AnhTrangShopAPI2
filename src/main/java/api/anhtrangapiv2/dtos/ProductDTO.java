package api.anhtrangapiv2.dtos;

import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductDTO {
    @NotEmpty(message="Name cannot be empty")
    private String name;

    @Min(value = 0,message = "Cost should be >= 0")
    @NotNull(message="Cost cannot be null")
    private Long cost;

    private Long sale_cost = 0l;

    private String description = "";

    private int quantity = 0;

    private boolean isnew = true;

    @Min(value = 0,message = "Colection Id should be >= 0")
    @NotNull(message="Colection Id cannot be null")
    private int collectionId;

    @Min(value = 0,message = "Parent Category Id should be >= 0")
    @NotNull(message="Parent Category Id cannot be null")
    private int parentCategoryId;

    @Min(value = 0,message = "Children Category Id should be >= 0")
    @NotNull(message="Children Category Id cannot be null")
    private int childrenCategoryId;
    
    private List<Integer> sizeIds;

    private List<Integer>  colorIds;
}
