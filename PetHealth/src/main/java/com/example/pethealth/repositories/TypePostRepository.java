package com.example.pethealth.repositories;

import com.example.pethealth.model.TypePost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TypePostRepository extends JpaRepository<TypePost, Long> {
    Optional<TypePost> findById(Long TypePostId);
}
