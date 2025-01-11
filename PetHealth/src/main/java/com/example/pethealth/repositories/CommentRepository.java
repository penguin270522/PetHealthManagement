package com.example.pethealth.repositories;

import com.example.pethealth.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByQuestionId(long questionId);
    List<Comment> findByParentCommentId(long parentCommentId);
    List<Comment> findByPostId(long postId);
}
