package com.example.pethealth.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentInput {
    private String content;
    private Long productId;
    private Long postId;
    private Long parentCommentId;
    private Long questionId;
}
