package com.example.pethealth.service;

import com.example.pethealth.dto.CommentInput;
import com.example.pethealth.dto.commentDTO.CommentChildOutput;
import com.example.pethealth.dto.commentDTO.CommentParentOutPut;
import com.example.pethealth.dto.outputDTO.BaseDTO;
import com.example.pethealth.enums.QuestionStatus;
import com.example.pethealth.exception.BadRequestException;
import com.example.pethealth.model.Comment;
import com.example.pethealth.model.Question;
import com.example.pethealth.model.User;
import com.example.pethealth.repositories.CommentRepository;
import com.example.pethealth.repositories.QuestionRepository;
import com.example.pethealth.service.parent.ICommentService;
import com.example.pethealth.service.profile.ProfileService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService implements ICommentService {
    private final CommentRepository commentRepository;
    private final ProfileService profileService;
    private final QuestionRepository questionRepository;

    public CommentService(CommentRepository commentRepository, ProfileService profileService, QuestionRepository questionRepository) {
        this.commentRepository = commentRepository;
        this.profileService = profileService;
        this.questionRepository = questionRepository;

    }

    @Override
    public BaseDTO createComment(CommentInput commentInput) {
        if(commentInput.getQuestionId() != null && commentInput.getQuestionId() > 0){
            User userCreateComment = profileService.getLoggedInUser();
            Question question = questionRepository.findById(commentInput.getQuestionId())
                    .orElseThrow(
                            ()-> new BadRequestException("dont find by Question with id" + commentInput.getQuestionId())
                    );
            if(userCreateComment.getRole().getCode().equals("ROLE_DOCTOR") || userCreateComment.getRole().getCode().equals("ROLE_COLLABORATOR")){
                if(question.getQuestionStatus().equals(QuestionStatus.PENDING)){
                    question.setQuestionStatus(QuestionStatus.ACTIVE);
                }
            };
            Comment comment = Comment.builder()
                    .user(userCreateComment)
                    .question(question)
                    .content(commentInput.getContent())
                    .build();
            commentRepository.save(comment);
        }
        if(commentInput.getParentCommentId() != null && commentInput.getParentCommentId() > 0 ){
            User userCreateComment = profileService.getLoggedInUser();
            Comment parentComment = commentRepository.findById(commentInput.getParentCommentId()).orElseThrow(
                    ()-> new BadRequestException("dont find by comment parent with id = " + commentInput.getParentCommentId())
            );
            Comment comment = Comment.builder()
                    .content(commentInput.getContent())
                    .parentComment(parentComment)
                    .user(userCreateComment)
                    .build();
            commentRepository.save(comment);
        }
        return BaseDTO.builder()
                .result(true)
                .message("success")
                .build();
    }

    @Override
    public BaseDTO getAllCommentQuestionId(long questionId) {
        List<Comment> results = commentRepository.findByQuestionId(questionId);
        List<CommentParentOutPut> commentParentOutPuts = results.stream()
                .map(items -> CommentParentOutPut.builder()
                        .id(items.getId())
                        .content(items.getContent())
                        .showChildComment(false)
                        .urlImageUser("http://localhost:8080/uploads/"+items.getUser().getImage().getUrl())
                        .nameUser(items.getUser().getFullName())
                        .listCommentChild(items.getReplies().stream()
                                .map(reply -> CommentChildOutput.builder()
                                        .id(reply.getId())
                                        .urlUser("http://localhost:8080/uploads/"+reply.getUser().getImage().getUrl())
                                        .nameUser(reply.getUser().getFullName())
                                        .content(reply.getContent())
                                        .build())
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
        return BaseDTO.builder()
                .message("success")
                .result(true)
                .results(commentParentOutPuts)
                .build();
    }


    @Override
    public BaseDTO deleteComment(long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()-> new BadRequestException("dont find by commnet with id" + commentId)
        );
        commentRepository.delete(comment);
        return BaseDTO.builder()
                .result(true)
                .message("success")
                .build();
    }
}
