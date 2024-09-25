package api.anhtrangapiv2.service.user;

import org.springframework.security.core.userdetails.UserDetailsService;
import api.anhtrangapiv2.dtos.*;
import api.anhtrangapiv2.models.User;

public interface IUserService {
    User createUser(UserDTO userDTO) throws Exception;
    String login(LoginDTO loginDTO) throws Exception;
    User updateUser(int id, UserDTO userDTO) throws Exception;
}
