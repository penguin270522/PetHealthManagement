package com.example.pethealth.service;

import com.example.pethealth.dto.outputDTO.BaseDTO;
import com.example.pethealth.model.TypeQuestion;
import com.example.pethealth.repositories.TypeQuestionRepository;
import com.example.pethealth.service.parent.ITypeQuestionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class TypeQuestionService implements ITypeQuestionService {
    private final TypeQuestionRepository typeQuestionRepository;
    @Override
    public TypeQuestion createQuestion(TypeQuestion typeQuestion) {
        return typeQuestionRepository.save(typeQuestion);
    }
    @Override
    public BaseDTO getAllTypeQuesiton() {
        List<TypeQuestion> results = typeQuestionRepository.findAll();
        return BaseDTO.builder()
                .results(results)
                .result(true)
                .message("success")
                .build();
    }
}
