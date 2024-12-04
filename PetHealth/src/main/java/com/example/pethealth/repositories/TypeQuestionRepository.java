package com.example.pethealth.repositories;

import com.example.pethealth.model.TypeQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TypeQuestionRepository extends JpaRepository<TypeQuestion, Long> {
    Optional<TypeQuestion> findById(long id);
}
