package api.anhtrangapiv2.service.user;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import api.anhtrangapiv2.components.JwtTokenUtil;
import api.anhtrangapiv2.dtos.LoginDTO;
import api.anhtrangapiv2.dtos.UserDTO;
import api.anhtrangapiv2.models.Role;
import api.anhtrangapiv2.models.User;
import api.anhtrangapiv2.repositories.RoleRepository;
import api.anhtrangapiv2.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    @Transactional
    public User createUser(UserDTO userDTO) throws Exception{
        if(userRepository.existsByPhoneNumber(userDTO.getPhoneNumber()))
            throw new RuntimeException("The Phone Number already exists");
        Role existingRole = roleRepository.findById(userDTO.getRoleId()).orElseThrow(
            ()-> new RuntimeException("Can't found the role with id: "+ userDTO.getRoleId())
        );
        if(existingRole.getName().toUpperCase() == "ADMIN")
            throw new RuntimeException("You can't create an account with Admin role");

        User newUser = User.builder()
        .fullname(userDTO.getFullname())
        .phoneNumber(userDTO.getPhoneNumber())
        .role(existingRole)
        .password(passwordEncoder.encode(userDTO.getPassword()))
        .build();
        userRepository.save(newUser);
        return newUser;
    }

    @Override
    public String login(LoginDTO loginDTO) throws Exception{
        User existingUser = userRepository.findByPhoneNumber(loginDTO.getPhoneNumber()).orElseThrow(
            () -> new RuntimeException("The Phone number/Password is incorrect")
        );
        if(!passwordEncoder.matches(loginDTO.getPassword(),existingUser.getPassword())){
            throw new RuntimeException("The Phone number/Password is incorrect");
        }
            
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginDTO.getPhoneNumber(), 
                loginDTO.getPassword(),
                existingUser.getAuthorities()
            );

        //authenticate with Java Spring security
        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtil.generateToken(existingUser);
    }

    @Override
    @Transactional
    public User updateUser(int id, UserDTO userDTO) throws Exception{
        User existingUser = userRepository.findById(id).orElseThrow(
            () -> new RuntimeException("Can't find user with id: " + id)
        );
        if(userRepository.existsByPhoneNumber(userDTO.getPhoneNumber())
            && !userDTO.getPhoneNumber().equals(existingUser.getPhoneNumber()))
            throw new RuntimeException("The Phone Number already exists");
        existingUser.setFullname(userDTO.getFullname());
        existingUser.setPhoneNumber(userDTO.getPhoneNumber());
        existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(existingUser);
        return existingUser;
    }
    

}
