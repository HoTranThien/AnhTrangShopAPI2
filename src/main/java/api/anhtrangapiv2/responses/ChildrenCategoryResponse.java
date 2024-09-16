package api.anhtrangapiv2.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChildrenCategoryResponse {
    private String name;
    private Integer pacaId;
    private int id;
}
