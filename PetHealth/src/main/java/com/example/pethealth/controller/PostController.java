package com.example.pethealth.controller;

import com.example.pethealth.dto.outputDTO.BaseDTO;
import com.example.pethealth.dto.outputDTO.PageDTO;
import com.example.pethealth.dto.postDTO.PostInput;
import com.example.pethealth.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/createPost")
    public BaseDTO createPost(@RequestBody PostInput postInput){
        return postService.createPost(postInput);
    }

    @GetMapping("/getPostDetails/{id}")
    public BaseDTO getPostDetails(@PathVariable("id") Long postId){
        return postService.getPostDetail(postId);
    }

    @PutMapping("/updatePost/{id}")
    public BaseDTO updatePost(@PathVariable("id") Long postId, @RequestBody
                              PostInput postInput){
        return postService.updatePost(postInput,postId);
    }

    @DeleteMapping("/deletePost/{id}")
    public BaseDTO deletePost(@PathVariable("id") Long postID){
        return postService.deletePost(postID);
    }

    @GetMapping("/getAllPost")
    public PageDTO getAllPost(@RequestParam Map<String,String> params){
        return postService.getAllPost(params);
    }

    @GetMapping("/searchTitle")
    public PageDTO searchTitlePost(@RequestBody String title){
        return null;
    }
}

