package com.example.pethealth.repositories;

import com.example.pethealth.dto.output.QuestionOutput;
import com.example.pethealth.model.Question;
import com.example.pethealth.repositories.custom.question.QuestionRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> , QuestionRepositoryCustom {
    @Query("select u from Question u where u.title like %?1%")
    List<Question> findQuestionByTitle(String title);

    Optional<Question> findById(long questionId);

}
