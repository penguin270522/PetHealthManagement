package com.example.pethealth.service.parent;

import com.example.pethealth.dto.outputDTO.BaseDTO;
import com.example.pethealth.model.TypePost;
import org.springframework.stereotype.Service;

@Service
public interface ITypePostService {
    BaseDTO createTypePost(TypePost typePost);
    BaseDTO getAllTypePost();

    BaseDTO deleteTypePost(Long typePostId);
}
