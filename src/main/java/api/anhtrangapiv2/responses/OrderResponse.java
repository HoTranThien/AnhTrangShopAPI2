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
public class OrderResponse {
    private int id;
    private Status status = Status.NEW_ORDER;
    private String customerName;
    private String customerTel;
    private String customerAddress;
    private String customerNote;
    private String note;
    private long total;
    private Delivery delivery;
    private LocalDateTime createdAt;
    List<ProductOrderResponse> productOrder;
}
