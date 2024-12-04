package com.example.pethealth.service.parent;

import com.example.pethealth.dto.QuestionDetailOutput;
import com.example.pethealth.dto.output.QuestionOutput;
import com.example.pethealth.dto.outputDTO.BaseDTO;
import com.example.pethealth.dto.outputDTO.PageDTO;
import com.example.pethealth.dto.outputDTO.QuestionInput;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface IQuestionService {

    BaseDTO createQuestion(QuestionInput question);
    PageDTO getAllQuestion(Map<String,String> params);
    BaseDTO findTitleQuestion(String title);

    QuestionDetailOutput findByIdQuestion(long questionId);

    BaseDTO deleteQuestionWithId(long questionId);




}
