package com.example.pethealth.repositories.custom.question;

import com.example.pethealth.constant.QueryConstant;
import com.example.pethealth.dto.outputDTO.SimpleResponese;
import com.example.pethealth.model.Question;
import com.example.pethealth.repositories.QuestionRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public class QuestionRepositoryCustomImpl implements  QuestionRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public SimpleResponese<Question> getAllQuestion(Map<String, String> params, Pageable pageable) {
        int limit = pageable.getPageSize();
        int page = pageable.getPageNumber() + 1;
        StringBuilder sqlQuery = new StringBuilder(buildSql(params))
                .append(QueryConstant.LIMIT).append(limit)
                .append(QueryConstant.OFFSET).append(pageable.getOffset());
        Query query = entityManager.createNativeQuery(sqlQuery.toString() , Question.class);
        List<Question> questionList = query.getResultList();
        int totalItem = countTotal(params);
        SimpleResponese<Question> results = new SimpleResponese<>();
        results.results = questionList;
        results.page = page;
        results.limit = limit;
        results.totalItem = totalItem;
        results.totalPage = (int) Math.ceil((double) totalItem / limit);
        return results;
    }

    private int countTotal(Map<String, String> params){
        Query queryRow = entityManager.createNativeQuery(buildSql(params));
        return queryRow.getResultList().size();
    }
    public String buildSql(Map<String , String> params){
        StringBuilder sql = new StringBuilder(QueryConstant.SELECT_FROM_QUESTION)
                .append(QueryConstant.WHERE_ONE_EQUAL_ONE);
        sql = filter(params,sql);
        return sql.toString();
    }

    public StringBuilder filter(Map<String,String> params, StringBuilder sql){
        String doctor = params.get("doctorId");
        String user = params.get("UserId");
        String typeQuestion = params.get("typeQuestion");
        String titleQuestion = params.get("titleQuestion");
        String questionStatus = params.get("questionStatus");
        if(questionStatus != null && !questionStatus.isEmpty()){
            sql.append(String.format(" and a.question_status = '%s'", questionStatus));
        }
        if(doctor != null && !doctor.isEmpty()){
            sql.append(String.format(" and a.doctor_id = '%s'", doctor));
        }
        if(user != null && !user.isEmpty()){
            sql.append(String.format(" and a.user_id = '%s'", user));
        }
        if(typeQuestion != null && !typeQuestion.isEmpty()){
            sql.append(String.format(" and a.type_question_id = '%s'", typeQuestion));
        }
        if(titleQuestion != null && !titleQuestion.isEmpty()){
            sql.append(String.format(" and a.title LIKE '%%%s%%'",titleQuestion));
        }
        return sql;
    }
}
