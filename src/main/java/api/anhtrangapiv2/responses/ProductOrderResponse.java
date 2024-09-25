package api.anhtrangapiv2.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductOrderResponse {
    private int id;
    private String size;
    private String color;
    private int quantity;
    private ProductForOrderResponse product;
}
