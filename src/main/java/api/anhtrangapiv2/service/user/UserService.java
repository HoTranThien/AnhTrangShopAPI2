package api.anhtrangapiv2.service.user;

import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import api.anhtrangapiv2.components.JwtTokenUtil;
import api.anhtrangapiv2.dtos.LoginDTO;
import api.anhtrangapiv2.dtos.UserDTO;
import api.anhtrangapiv2.models.Order;
import api.anhtrangapiv2.models.Role;
import api.anhtrangapiv2.models.User;
import api.anhtrangapiv2.repositories.OrderRepository;
import api.anhtrangapiv2.repositories.RoleRepository;
import api.anhtrangapiv2.repositories.UserRepository;
import api.anhtrangapiv2.responses.ProductForOrderResponse;
import api.anhtrangapiv2.responses.ProductOrderResponse;
import api.anhtrangapiv2.responses.UserDetailRespone;
import api.anhtrangapiv2.responses.UserOrdersResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final OrderRepository orderRepository;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    @Transactional
    public String createUser(UserDTO userDTO) throws Exception{
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
        .email(userDTO.getEmail())
        .address(userDTO.getAddress())
        .role(existingRole)
        .password(passwordEncoder.encode(userDTO.getPassword()))
        .build();
        userRepository.save(newUser);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            userDTO.getPhoneNumber(), 
            userDTO.getPassword(),
            newUser.getAuthorities()
        );

        //authenticate with Java Spring security
        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtil.generateToken(newUser);
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
        if(userDTO.getFullname()!=null)
        existingUser.setFullname(userDTO.getFullname());
        if(userDTO.getPhoneNumber()!=null)
        existingUser.setPhoneNumber(userDTO.getPhoneNumber());
        if(userDTO.getPassword()!=null)
        existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        if(userDTO.getAddress()!=null)
        existingUser.setAddress(userDTO.getAddress());
        userRepository.save(existingUser);
        return existingUser;
    }
    @Override
    public String getFullName(String token){
        String phoneNumber = jwtTokenUtil.extractPhoneNumber(token);
        Optional<User> existingUser = userRepository.findByPhoneNumber(phoneNumber);
        if(existingUser.isEmpty()) return null;
        return existingUser.get().getFullname();
    }
    @Override
    public List<UserOrdersResponse> getOrders(String token){
        String phoneNumber = jwtTokenUtil.extractPhoneNumber(token);
        Optional<User> existingUser = userRepository.findByPhoneNumber(phoneNumber);
        if(existingUser.isEmpty()) return null;
        List<Order> orders = orderRepository.findByUserId(existingUser.get().getId());
        List<UserOrdersResponse> result =  orders.stream()
        .map(order ->{
            List<ProductOrderResponse> productOrder = order.getProductOrder().stream()
            .map(po -> {
                ProductForOrderResponse product = ProductForOrderResponse.builder()
                .name(po.getProduct().getName())
                .img(po.getProduct().getImgProduct().get(0).getLink())
                .build();

                return ProductOrderResponse.builder()
                .color(po.getColor())
                .size(po.getSize())
                .quantity(po.getQuantity())
                .product(product)
                .build();
            }).toList();  

            return UserOrdersResponse.builder()
            .id(order.getId())
            .status(order.getStatus())
            .customerName(order.getCustomerName())
            .customerTel(order.getCustomerTel())
            .customerAddress(order.getCustomerAddress())
            .total(order.getTotal())
            .productOrder(productOrder)
            .delivery(order.getDelivery().getName())
            .code(order.getCode())
            .createdAt(order.getCreateAt())
            .build();
        })
        .toList();
        return result;
    }

    @Override
    public UserDetailRespone getUserDetail(String token){
        String phoneNumber = jwtTokenUtil.extractPhoneNumber(token);
        Optional<User> existingUser = userRepository.findByPhoneNumber(phoneNumber);
        if(existingUser.isEmpty()) return null;
        return UserDetailRespone.builder()
        .id(existingUser.get().getId())
        .fullname(existingUser.get().getFullname())
        .phoneNumber(existingUser.get().getPhoneNumber())
        .email(existingUser.get().getEmail())
        .address(existingUser.get().getAddress())
        .role(existingUser.get().getRole().getName())
        .build();
    }
    @Override
    public String getRole(String token){
        String phoneNumber = jwtTokenUtil.extractPhoneNumber(token);
        Optional<User> existingUser = userRepository.findByPhoneNumber(phoneNumber);
        if(existingUser.isEmpty()) return null;
        return existingUser.get().getRole().getName();
    }
}
