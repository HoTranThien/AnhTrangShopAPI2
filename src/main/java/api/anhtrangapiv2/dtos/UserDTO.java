package api.anhtrangapiv2.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDTO {
    @NotBlank(message="Phone Number cannot be blank")
    @NotNull(message= "Phone Number cannot be null")
    private String phoneNumber;
    @NotBlank(message="Password cannot be blank")
    @NotNull(message= "Password cannot be null")
    private String password;

    @NotBlank(message="Password cannot be blank")
    @NotNull(message= "Password cannot be null")
    private String fullname;

    private int roleId = 1;
}
