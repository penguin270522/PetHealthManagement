package com.example.pethealth.repositories.custom.post;

import com.example.pethealth.dto.outputDTO.SimpleResponese;
import com.example.pethealth.model.Post;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface PostRepositoryCustom {
    SimpleResponese<Post> getAllPost(Map<String,String> params, Pageable pageable);
}
