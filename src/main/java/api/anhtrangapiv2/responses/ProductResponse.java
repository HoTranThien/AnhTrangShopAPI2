package api.anhtrangapiv2.responses;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {
    private int id;
    private String name;
    private Long cost;
    private Long sale_cost;
    private String description;
    private int quantity;
    private boolean isnew;
    private CollectionResponse collection;
    private ParentCategoryResponse parentCategory;
    private ChildrenCategoryResponse childrenCategory;
    private List<SizeResponse> productSize;
    private List<ColorResponse> productColor;
    private List<ImgProductResponse> imgProduct;
}
