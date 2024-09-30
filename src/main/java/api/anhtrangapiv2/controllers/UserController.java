package api.anhtrangapiv2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.anhtrangapiv2.dtos.LoginDTO;
import api.anhtrangapiv2.dtos.UserDTO;
import api.anhtrangapiv2.responses.ResponseToClient;
import api.anhtrangapiv2.service.user.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path="${api.prefix}/user")
public class UserController {

    private final UserService userService;

    @PostMapping(path = "/getorders")
    public ResponseEntity<Object> getOrders(@RequestBody @NotBlank @NotNull String token){
        return ResponseEntity.ok(ResponseToClient.builder()
        .message("OK")
        .status(HttpStatus.OK)
        .data(userService.getOrders(token))
        .build());
    }
    @PostMapping(path = "/getusername")
    public ResponseEntity<Object> getUserName(@RequestBody @NotBlank @NotNull String token){
        return ResponseEntity.ok(ResponseToClient.builder()
        .message("OK")
        .status(HttpStatus.OK)
        .data(userService.getFullName(token))
        .build());
    }
    @PostMapping(path = "/getuserdetail")
    public ResponseEntity<Object> getUserDetail(@RequestBody @NotBlank @NotNull String token){
        return ResponseEntity.ok(ResponseToClient.builder()
        .message("OK")
        .status(HttpStatus.OK)
        .data(userService.getUserDetail(token))
        .build());
    }

    @PostMapping(path = "/register")
    public ResponseEntity<Object> createUser(@RequestBody @Valid UserDTO userDTO) throws Exception{
        return ResponseEntity.ok(ResponseToClient.builder()
        .message("OK")
        .status(HttpStatus.OK)
        .data(userService.createUser(userDTO))
        .build());
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable int id,@RequestBody UserDTO userDTO) throws Exception{
        return ResponseEntity.ok(ResponseToClient.builder()
        .message("OK")
        .status(HttpStatus.OK)
        .data(userService.updateUser(id,userDTO))
        .build());
    }
    @PostMapping(path = "/login")
    public ResponseEntity<Object> login(@RequestBody @Valid LoginDTO loginDTO) throws Exception{
        return ResponseEntity.ok(ResponseToClient.builder()
        .message("OK")
        .status(HttpStatus.OK)
        .data(userService.login(loginDTO))
        .build()); 
    }

    @PostMapping(path = "/getrole")
    public ResponseEntity<Object> getRole(@RequestBody @NotBlank @NotNull String token){
        return ResponseEntity.ok(ResponseToClient.builder()
        .message("OK")
        .status(HttpStatus.OK)
        .data(userService.getRole(token))
        .build());
    }
}
