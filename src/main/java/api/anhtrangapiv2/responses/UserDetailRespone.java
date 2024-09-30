package api.anhtrangapiv2.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailRespone {
    private int id;
    private String fullname;
    private String phoneNumber;
    private String email;
    private String address;
    private String role;
}
