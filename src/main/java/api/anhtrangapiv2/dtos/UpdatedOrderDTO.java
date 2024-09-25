package api.anhtrangapiv2.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import api.anhtrangapiv2.models.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdatedOrderDTO {

    private Status status = Status.NEW_ORDER;

    @JsonProperty("customer_name")
    private String customerName;

    @JsonProperty("customer_tel")
    private String customerTel;

    @JsonProperty("customer_address")
    private String customerAddress;

    @JsonProperty("customer_note")
    private String customerNote;

    private String note;
}
