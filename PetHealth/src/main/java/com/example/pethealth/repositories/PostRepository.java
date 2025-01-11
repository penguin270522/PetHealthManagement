package com.example.pethealth.repositories;

import com.example.pethealth.model.Appointment;
import com.example.pethealth.model.Post;
import com.example.pethealth.repositories.custom.post.PostRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> , PostRepositoryCustom {

    @Query("select u from Post u where u.title like %?1%")
    List<Post> findByTitle(String title);
}
