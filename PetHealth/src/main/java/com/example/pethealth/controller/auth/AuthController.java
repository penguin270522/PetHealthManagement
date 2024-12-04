package com.example.pethealth.controller.auth;

import com.example.pethealth.dto.outputDTO.BaseDTO;
import com.example.pethealth.dto.authDTO.JWT.JwtLoginDTO;
import com.example.pethealth.dto.authDTO.LoginDTO;
import com.example.pethealth.dto.authDTO.RegisterDTO;
import com.example.pethealth.service.auth.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public BaseDTO createUser(@RequestBody RegisterDTO request){
        return userService.createUser(request);
    }


    @PostMapping("/login")
    public JwtLoginDTO loginUser(@RequestBody LoginDTO request){
        return userService.loginUser(request);
    }
}
