package com.example.pethealth.dto.postDTO;

import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostAll {
    private String title;
    private String typePost;
    private String userCreate;
    private String status;
    private Long id;
}
