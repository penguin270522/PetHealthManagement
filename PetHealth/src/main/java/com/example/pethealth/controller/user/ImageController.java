package com.example.pethealth.controller.user;

import com.example.pethealth.dto.outputDTO.BaseDTO;
import com.example.pethealth.service.ImageService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/image")
public class ImageController {
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping(value = "/createImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BaseDTO createImage(@ModelAttribute("file") MultipartFile file){
        return imageService.createImage(file);
    }

    @PostMapping(value = "/user/upload/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('DOCTOR')")
    public BaseDTO uploadImageUser(
            @PathVariable("id") Long userId,
            @ModelAttribute("files") List<MultipartFile> files
            ) throws IOException {
        return imageService.createImageUser(userId,files);
    }

    @PostMapping(value = "/question/upload/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BaseDTO uploadImageQuestion(
            @PathVariable("id") Long questionId,
            @ModelAttribute("files") MultipartFile files
    ) {
        return imageService.createImageQuestion(questionId,files);
    }

    @PostMapping(value = "/pet/upload/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('DOCTOR','USER','ADMIN','COLLABORATOR')")
    public BaseDTO uploadImagePet(
            @PathVariable("id") Long petId,
            @ModelAttribute("files") MultipartFile files
    ){
        return imageService.createImagePet(petId,files);
    }

    @PostMapping(value = "/post/upload/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BaseDTO uploadImagePost(
            @PathVariable("id") Long postId,
            @RequestParam("files") List<MultipartFile> files
    ){
        return imageService.createImagePost(files,postId);
    }

    @DeleteMapping("/post/delete/{id}")
    private BaseDTO deleteImagePost(@PathVariable("id") Long postId){
        return imageService.deleteImagePost(postId);
    }
    @DeleteMapping("/pet/delete/{id}")
    public BaseDTO deleteImagePet(@PathVariable("id") Long petId){
        return imageService.deleteImagePet(petId);
    }

}
