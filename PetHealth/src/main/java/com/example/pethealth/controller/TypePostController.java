package com.example.pethealth.controller;

import com.example.pethealth.dto.outputDTO.BaseDTO;
import com.example.pethealth.model.TypePost;
import com.example.pethealth.service.TypePostService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/typePost")
public class TypePostController {
    private final TypePostService typePostService;

    public TypePostController(TypePostService typePostService) {
        this.typePostService = typePostService;
    }

    @GetMapping("/getAllTypePost")
    public BaseDTO getAllTypePost(){
        return typePostService.getAllTypePost();
    }

    @PostMapping("/createPost")
    public BaseDTO createTypePost(@RequestBody TypePost typePost){
        return typePostService.createTypePost(typePost);
    }

    @DeleteMapping("/deleteTypePost/{id}")
    public BaseDTO deleteTypePost(@PathVariable("id") Long typePostId){
        return typePostService.deleteTypePost(typePostId);
    }
}
