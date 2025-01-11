package com.example.pethealth.service.parent;

import com.example.pethealth.dto.outputDTO.BaseDTO;
import com.example.pethealth.dto.outputDTO.PageDTO;
import com.example.pethealth.dto.postDTO.PostDetail;
import com.example.pethealth.dto.postDTO.PostInput;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface IPostService {
    BaseDTO createPost(PostInput postInput);
    BaseDTO getPostDetail(Long postId);
    BaseDTO updatePost(PostInput postInput, Long postId);
    BaseDTO deletePost(Long postId);
    PageDTO getAllPost(Map<String,String> params);


}
