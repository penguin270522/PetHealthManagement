package com.example.pethealth.dto.postDTO;

import com.example.pethealth.enums.PostStatus;
import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostInput {
    private String title;
    private String content;
    private PostStatus postStatus;
    private Long typePostId;
    private String message;
}
