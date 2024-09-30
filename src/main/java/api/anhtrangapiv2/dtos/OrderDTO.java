package api.anhtrangapiv2.dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import api.anhtrangapiv2.models.Status;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderDTO {

    private Status status = Status.NEW_ORDER;

    @NotBlank(message="Customer name can't be blank")
    @NotNull(message="Customer name can't be null")
    @JsonProperty("customer_name")
    private String customerName;

    @NotBlank(message="Customer Tel can't be blank")
    @NotNull(message="Customer Tel can't be null")
    @JsonProperty("customer_tel")
    private String customerTel;

    @NotBlank(message="Customer Tel can't be blank")
    @NotNull(message="Customer Tel can't be null")
    @JsonProperty("customer_address")
    private String customerAddress;

    @JsonProperty("customer_note")
    private String customerNote;

    private String note;

    @Min(value = 1, message = "Total cost must be > 0")
    private long total;

    @Min(value = 1, message = "Delivery Id must be > 0")
    @JsonProperty("delivery_id")
    private int deliveryId;

    @Min(value = 1, message = "User Id must be > 0")
    @JsonProperty("user_id")
    private int userId;

    List<ProductOrderDTO> productOrder;

}
