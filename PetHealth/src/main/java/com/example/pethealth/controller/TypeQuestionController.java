package com.example.pethealth.controller;

import com.example.pethealth.dto.outputDTO.BaseDTO;
import com.example.pethealth.model.TypeQuestion;
import com.example.pethealth.service.TypeQuestionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/typeQuestion")
public class TypeQuestionController {
    private final TypeQuestionService typeQuestionService;

    public TypeQuestionController(TypeQuestionService typeQuestionService) {
        this.typeQuestionService = typeQuestionService;
    }

    @PostMapping("/createTypeQuestion")
    public TypeQuestion createQuestion(@RequestBody TypeQuestion typeQuestion){
        return typeQuestionService.createQuestion(typeQuestion);
    }

    @GetMapping("/getAllTypeQuestion")
    public BaseDTO getAllQuestion(){
        return typeQuestionService.getAllTypeQuesiton();
    }

}
