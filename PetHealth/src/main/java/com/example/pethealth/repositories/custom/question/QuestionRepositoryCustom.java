package com.example.pethealth.repositories.custom.question;

import com.example.pethealth.dto.outputDTO.SimpleResponese;
import com.example.pethealth.model.Question;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface QuestionRepositoryCustom {
    SimpleResponese<Question> getAllQuestion(Map<String,String> params, Pageable pageable);
}
