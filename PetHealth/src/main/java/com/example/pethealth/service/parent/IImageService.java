package com.example.pethealth.service.parent;

import com.example.pethealth.dto.outputDTO.BaseDTO;
import com.example.pethealth.dto.outputDTO.ImageDTO;
import com.example.pethealth.model.Image;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IImageService {
    BaseDTO createImage(MultipartFile files);
    BaseDTO createImageQuestion(long questionId, MultipartFile files);
    BaseDTO createImageUser(long userId, List<MultipartFile> files) throws IOException;
    BaseDTO createImagePet(long petId, MultipartFile files);
    BaseDTO createImageProductId(long productId, List<MultipartFile> files);
    BaseDTO createImageComment(long id, List<MultipartFile> files);
    Image createImage(long id, ImageDTO imageDTO);
    BaseDTO createImageUser( MultipartFile file);
    ResponseEntity<?> viewImage(String url);
    BaseDTO createImageLogo(MultipartFile files);
    BaseDTO createImageFirstCreateUser(MultipartFile file);
    Image findByUrl(String url);

    BaseDTO deleteImagePet(long petId);
    BaseDTO deleteImagePost(long postId);
    BaseDTO createImagePost(List<MultipartFile> files, Long postId);
}
