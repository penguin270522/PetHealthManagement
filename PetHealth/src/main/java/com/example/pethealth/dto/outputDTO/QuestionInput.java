package com.example.pethealth.dto.outputDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionInput {
    private long doctorId;
    private long typeQuestion;
    private String title;
    private String content;
}
