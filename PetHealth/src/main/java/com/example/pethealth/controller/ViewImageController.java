package com.example.pethealth.controller;

import com.example.pethealth.dto.outputDTO.BaseDTO;
import com.example.pethealth.service.ImageService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/uploads")
public class ViewImageController {

    private final ImageService imageService;


    public ViewImageController(ImageService imageService) {
        this.imageService = imageService;
    }


    @GetMapping("/{filename:.+}")
    public ResponseEntity<?> viewImage(@PathVariable String filename){
        return imageService.viewImage(filename);
    }

    @PostMapping(value = "/create-avatar" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BaseDTO createFirstAvatar(@ModelAttribute("files") MultipartFile files){
        return imageService.createImageFirstCreateUser(files);
    }
}
