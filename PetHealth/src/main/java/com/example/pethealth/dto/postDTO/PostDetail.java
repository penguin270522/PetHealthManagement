package com.example.pethealth.dto.postDTO;

import com.example.pethealth.model.Comment;
import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDetail {
    private String title;
    private String content;
    private String status;
    private List<String> comments;
    private List<String> urlImage;
    private String createDay;
}
