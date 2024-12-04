package com.example.pethealth.service.parent;

import com.example.pethealth.dto.outputDTO.BaseDTO;
import com.example.pethealth.model.TypeQuestion;

import java.util.List;

public interface ITypeQuestionService {
    TypeQuestion createQuestion(TypeQuestion typeQuestion);
    BaseDTO getAllTypeQuesiton();
}
