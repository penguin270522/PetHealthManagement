package com.example.pethealth.dto.commentDTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentChildOutput {
    private long id;
    private String urlUser;
    private String nameUser;
    private String content;
}
