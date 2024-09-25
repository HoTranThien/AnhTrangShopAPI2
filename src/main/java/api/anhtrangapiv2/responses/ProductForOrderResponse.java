package api.anhtrangapiv2.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductForOrderResponse {
    private int id;
    private String name;
    private String img;
}
