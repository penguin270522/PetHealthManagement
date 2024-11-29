package com.example.pethealth.dto.commentDTO;

import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentParentOutPut {
    private long id;
    private String urlImageUser;
    private String nameUser;
    private String content;
    private List<CommentChildOutput> listCommentChild;
    private boolean showChildComment;

}
