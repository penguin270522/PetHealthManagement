package com.example.pethealth.controller;

import com.example.pethealth.dto.QuestionDetailOutput;
import com.example.pethealth.dto.outputDTO.BaseDTO;
import com.example.pethealth.dto.outputDTO.PageDTO;
import com.example.pethealth.dto.outputDTO.QuestionInput;
import com.example.pethealth.service.QuestionService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/question")
public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("/create")
    public BaseDTO createQuestion(
            @RequestBody QuestionInput question){
        return questionService.createQuestion(question);
    }

    @GetMapping("/getAllQuestion")
    public PageDTO getQuestionAll(@RequestParam Map<String,String> prams){
        return questionService.getAllQuestion(prams);
    }

    @GetMapping("/getQuestionWithId/{id}")
    public QuestionDetailOutput findByIdQuestion(@PathVariable long id){
        return questionService.findByIdQuestion(id);
    }

    @DeleteMapping("/deleteQuestionId/{id}")
    public BaseDTO deleteQuestion(@PathVariable long id){
        return questionService.deleteQuestionWithId(id);
    }



}
