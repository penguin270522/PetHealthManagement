package com.example.pethealth.controller.auth;

import com.example.pethealth.dto.outputDTO.BaseDTO;
import com.example.pethealth.dto.authDTO.JWT.JwtLoginDTO;
import com.example.pethealth.dto.authDTO.LoginDTO;
import com.example.pethealth.dto.authDTO.RegisterDTO;
import com.example.pethealth.service.auth.RoleService;
import com.example.pethealth.service.auth.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final RoleService roleService;

    public AuthController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @PostMapping("/register")
    public BaseDTO registerUser(@RequestBody RegisterDTO request){
        return userService.createUser(request);
    }


    @PostMapping("/createUser")
    public BaseDTO createUser(@RequestBody RegisterDTO request){
        return userService.registerUser(request);
    }

    @PostMapping("/login")
    public JwtLoginDTO loginUser(@RequestBody LoginDTO request){
        return userService.loginUser(request);
    }

    @GetMapping("/getAllUser")
    public BaseDTO getAllUser(){
        return userService.getAllUser();
    }

    @GetMapping("/getUserWithCode")
    public BaseDTO getUserWithRole(@RequestParam String code){
        return userService.findUserWithCode(code);
    }

    @GetMapping("/findUserWithName")
    public BaseDTO findUserWithName(@RequestParam String fullName){
        return userService.findUserName(fullName);
    }

    @GetMapping("/getAllRole")
    public BaseDTO findAllRole(){
        return roleService.getAllRole();
    }

    @PutMapping("/updateUser/{id}")
    public BaseDTO updateUser(
            @PathVariable("id") Long userId
            ,@RequestBody RegisterDTO request){
        return userService.updateUser(request,userId);
    }

    @GetMapping("/getUserWithId/{id}")
    public BaseDTO getUserWithId(@PathVariable("id") Long userId){
        return userService.findByUserWithId(userId);
    }

    @GetMapping("/getInformationUser")
    public BaseDTO getInformation(){
        return null;
    }
}
