package com.example.pethealth.controller;

import com.example.pethealth.dto.CommentInput;
import com.example.pethealth.dto.outputDTO.BaseDTO;
import com.example.pethealth.service.CommentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/createComment")
    public BaseDTO createComment(@RequestBody CommentInput commentInput){
        return commentService.createComment(commentInput);
    }

    @GetMapping("/getAllCommentQuestion/{id}")
    public BaseDTO getAllComment(@PathVariable long id){
        return commentService.getAllCommentQuestionId(id);
    }
}
