package com.example.pethealth.controller.user;

import com.example.pethealth.dto.authDTO.UserPetWithMedicalReportOutPut;
import com.example.pethealth.dto.outputDTO.UserResponse;
import com.example.pethealth.service.auth.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/home")
public class HomeController {
    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping()
    public UserResponse getAllUser(@RequestParam String role){
        return userService.findByUserRole(role);
    }


}
