package com.example.pethealth.controller;

import com.example.pethealth.dto.outputDTO.BaseDTO;
import com.example.pethealth.dto.outputDTO.ProfileResponse;
import com.example.pethealth.dto.outputDTO.UpdateUserDTO;
import com.example.pethealth.service.ImageService;
import com.example.pethealth.service.profile.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {
    private final ImageService imageService;
    private final ProfileService profileService;

    public ProfileController(ImageService imageService, ProfileService profileService) {
        this.imageService = imageService;
        this.profileService = profileService;
    }

    @PostMapping("/change-avatar")
    @PreAuthorize("hasAnyRole('DOCTOR','USER','ADMIN','COLLABORATOR')")
    public ResponseEntity<BaseDTO> changeAvatar(@RequestParam("file")MultipartFile file){
        BaseDTO response = imageService.createImageUser(file);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profile_user")
    public ProfileResponse getProfile(){
        return profileService.getMyProfile();
    }

    @PostMapping("/update_profile_user")
    public BaseDTO updateProfileUser(@RequestBody UpdateUserDTO userDTO){
        return profileService.updateProfileUser(userDTO);
    }

}
