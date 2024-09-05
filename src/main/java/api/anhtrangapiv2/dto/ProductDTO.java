package api.anhtrangapiv2.dto;

import lombok.Data;

@Data
public class ProductDTO {
    private String name;
    
    private Long cost;

    private Long sale_cost;

    private String description;

    private int quantity;

    private boolean isnew;

    private int collectionId;

    private int parent_categoryId;

    private int ChildrenCategoryId;

    private int sizeId;

    private int colorId;
}
