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
public class ParentCategoryResponse {
    private String name;
    private int id;
    private List<ChildrenCategoryResponse> childrenCategories;
}
