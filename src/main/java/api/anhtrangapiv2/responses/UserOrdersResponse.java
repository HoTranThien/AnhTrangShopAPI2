package api.anhtrangapiv2.responses;

import java.time.LocalDateTime;
import java.util.List;


import api.anhtrangapiv2.models.Delivery;
import api.anhtrangapiv2.models.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserOrdersResponse {
    private int id;
    private Status status;
    private String customerName;
    private String customerTel;
    private String customerAddress;
    private long total;
    private String delivery;
    private String code;
    private LocalDateTime createdAt;
    private List<ProductOrderResponse> productOrder;
}
