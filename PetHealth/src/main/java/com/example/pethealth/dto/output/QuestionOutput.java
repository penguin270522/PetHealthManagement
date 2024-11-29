package com.example.pethealth.dto.output;

import lombok.*;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class QuestionOutput {
    private long id;
    private String fullName;
    private String urlUser;
    private String statusQuestion;
    private String typeQuestion;
    private String title;
    private String content;
    private CommentOutput commentOutput;
    private List<String> urlImage;
    private String codeQuestion;
    private String createQuestionDate;
}
