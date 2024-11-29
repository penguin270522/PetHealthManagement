package com.example.pethealth.service;

import com.example.pethealth.components.GetPageableUtil;
import com.example.pethealth.components.MapperDateUtil;
import com.example.pethealth.dto.QuestionDetailOutput;
import com.example.pethealth.dto.output.QuestionOutput;
import com.example.pethealth.dto.outputDTO.BaseDTO;
import com.example.pethealth.dto.outputDTO.PageDTO;
import com.example.pethealth.dto.outputDTO.QuestionInput;
import com.example.pethealth.dto.outputDTO.SimpleResponese;
import com.example.pethealth.enums.QuestionStatus;
import com.example.pethealth.exception.BadRequestException;
import com.example.pethealth.model.Comment;
import com.example.pethealth.model.Question;
import com.example.pethealth.model.TypeQuestion;
import com.example.pethealth.model.User;
import com.example.pethealth.repositories.CommentRepository;
import com.example.pethealth.repositories.QuestionRepository;
import com.example.pethealth.repositories.TypeQuestionRepository;
import com.example.pethealth.service.auth.UserService;
import com.example.pethealth.service.parent.IQuestionService;
import com.example.pethealth.service.profile.ProfileService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class QuestionService implements IQuestionService {
    private final QuestionRepository questionRepository;
    private final ProfileService profileService;
    private final UserService userService;
    private final TypeQuestionRepository typeQuestionRepository;
    private final CommentRepository commentRepository;

    @Override
    public BaseDTO createQuestion(QuestionInput question) {
        try {
            User user = profileService.getLoggedInUser();
            User doctor = userService.findByIdUser(question.getDoctorId());
            TypeQuestion typeQuestion = typeQuestionRepository.findById(question.getTypeQuestion())
                    .orElseThrow(()-> new BadRequestException("dont find TypeQuestion With id typeQuestion"));
            Question newQuestion = Question.builder()
                    .QuestionStatus(QuestionStatus.PENDING)
                    .typeQuestion(typeQuestion)
                    .content(question.getContent())
                    .doctor(doctor)
                    .user(user)
                    .title(question.getTitle())
                    .build();
            questionRepository.save(newQuestion);
            return BaseDTO.builder()
                    .message("success")
                    .result(true)
                    .build();
        }catch (BadRequestException e){
            return BaseDTO.builder()
                    .message("false")
                    .result(false)
                    .build();
        }
    }

    @Override
    public PageDTO getAllQuestion(Map<String, String> params) {
        Pageable pageable = GetPageableUtil.getPageable(params);
        SimpleResponese<Question> responese = questionRepository.getAllQuestion(params,pageable);
        List<QuestionOutput> questionOutputsList = new ArrayList<>();
        for(Question items: responese.results){
            String status;
            if(items.getQuestionStatus() == QuestionStatus.ACTIVE){
                status = "Đã trả lời!";
            }else{
                status = "Chưa trả lời";
            }

            QuestionOutput questionOutput = QuestionOutput.builder()
                    .id(items.getId())
                    .urlUser("http://localhost:8080/uploads/" + items.getUser().getImage().getUrl())
                    .statusQuestion(status)
                    .title(items.getTitle())
                    .content(items.getContent())
                    .codeQuestion(items.getCode())
                    .typeQuestion(items.getTypeQuestion().getName())
                    .fullName(items.getUser().getFullName())
                    .createQuestionDate(MapperDateUtil.getDate(items.getCreatedDate()))
                    .build();
            questionOutputsList.add(questionOutput);
        }
        SimpleResponese<QuestionOutput> questionOutputSimpleResponese = SimpleResponese.<QuestionOutput>builder()
                .limit(responese.limit)
                .page(responese.page)
                .totalPage(responese.totalPage)
                .totalItem(responese.totalItem)
                .message(responese.message)
                .results(questionOutputsList)
                .build();
        return PageDTO.builder()
                .message("sucess")
                .result(true)
                .simpleResponese(questionOutputSimpleResponese)
                .build();
    }
    @Override
    public BaseDTO findTitleQuestion(String title) {

        return null;
    }

    @Override
    public QuestionDetailOutput findByIdQuestion(long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(
                        ()-> new BadRequestException("dont find question with id = " +  questionId)
                );
        String status;
        if(question.getQuestionStatus() == QuestionStatus.ACTIVE){
            status = "Đã trả lời!";
        }else{
            status = "Chưa trả lời";
        }
        QuestionOutput questionOutput = QuestionOutput.builder()
                .id(question.getId())
                .urlUser("http://localhost:8080/uploads/" + question.getUser().getImage().getUrl())
                .statusQuestion(status)
                .title(question.getTitle())
                .content(question.getContent())
                .codeQuestion(question.getCode())
                .typeQuestion(question.getTypeQuestion().getName())
                .fullName(question.getUser().getFullName())
                .build();
        return QuestionDetailOutput.builder()
                .message("success")
                .result(true)
                .questionOutput(questionOutput)
                .build();
    }

    @Override
    public BaseDTO deleteQuestionWithId(long questionId) {
        Question question = questionRepository.findById(questionId).orElseThrow(
                ()-> new BadRequestException("dont find by question with id" + questionId)
        );
        List<Comment> commentParen = commentRepository.findByQuestionId(questionId);
        if(commentParen != null){
            for(Comment items : commentParen){
                List<Comment> commentChildren = commentRepository.findByParentCommentId(items.getId());
                if(commentChildren != null){
                    commentRepository.deleteAll(commentChildren);
                }
                commentRepository.delete(items);
            }
        }
        questionRepository.delete(question);
        return BaseDTO.builder()
                .message("success")
                .result(true)
                .build();
    }
}
