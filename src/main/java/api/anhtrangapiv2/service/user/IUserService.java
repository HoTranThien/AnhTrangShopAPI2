package api.anhtrangapiv2.service.user;

import java.util.List;

import api.anhtrangapiv2.dtos.*;
import api.anhtrangapiv2.models.User;
import api.anhtrangapiv2.responses.*;

public interface IUserService {
    String createUser(UserDTO userDTO) throws Exception;
    String login(LoginDTO loginDTO) throws Exception;
    User updateUser(int id, UserDTO userDTO) throws Exception;
    List<UserOrdersResponse> getOrders(String token);
    String getFullName(String token);
    UserDetailRespone getUserDetail(String token);
    String getRole(String token);
}
