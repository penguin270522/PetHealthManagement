package com.example.pethealth.dto;

import com.example.pethealth.dto.output.QuestionOutput;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionDetailOutput {
    private String message;
    private boolean result;
    private QuestionOutput questionOutput;
}
