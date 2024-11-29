package com.example.pethealth.service.parent;

import com.example.pethealth.dto.CommentInput;
import com.example.pethealth.dto.outputDTO.BaseDTO;
import org.springframework.stereotype.Service;

@Service
public interface ICommentService {
    BaseDTO createComment(CommentInput commentInput);
    BaseDTO getAllCommentQuestionId(long questionId);

    BaseDTO deleteComment(long commentId);
}
