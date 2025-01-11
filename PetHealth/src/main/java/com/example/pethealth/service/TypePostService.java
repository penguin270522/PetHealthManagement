package com.example.pethealth.service;

import com.example.pethealth.dto.outputDTO.BaseDTO;
import com.example.pethealth.exception.BadRequestException;
import com.example.pethealth.model.TypePost;
import com.example.pethealth.repositories.TypePostRepository;
import com.example.pethealth.service.parent.ITypePostService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypePostService implements ITypePostService {
    private final TypePostRepository typePostRepository;
    public TypePostService(TypePostRepository typePostRepository) {
        this.typePostRepository = typePostRepository;
    }
    @Override
    public BaseDTO createTypePost(TypePost typePost) {
        typePostRepository.save(typePost);
        return BaseDTO.builder()
                .message("Mục bài viết đã được tạo").result(true)
                .build();
    }
    @Override
    public BaseDTO getAllTypePost() {
        List<TypePost> results = typePostRepository.findAll();
        return BaseDTO.builder()
                .results(results).result(true)
                .build();
    }

    @Override
    public BaseDTO deleteTypePost(Long typePostId) {
        TypePost typePost = typePostRepository.findById(typePostId).orElseThrow(
                ()-> new BadRequestException("dont find by type Post with id = " + typePostId)
        );
        typePostRepository.delete(typePost);
        return BaseDTO.builder()
                .message("success")
                .result(true)
                .build();
    }
}
