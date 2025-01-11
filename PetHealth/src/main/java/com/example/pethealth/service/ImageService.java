package com.example.pethealth.service;

import com.example.pethealth.components.ImageAvatarUserUtils;
import com.example.pethealth.components.PictureHandle;
import com.example.pethealth.dto.outputDTO.BaseDTO;
import com.example.pethealth.dto.outputDTO.ImageDTO;
import com.example.pethealth.exception.BadRequestException;
import com.example.pethealth.model.*;
import com.example.pethealth.repositories.ImageRepository;
import com.example.pethealth.repositories.PetRepository;
import com.example.pethealth.repositories.PostRepository;
import com.example.pethealth.repositories.QuestionRepository;
import com.example.pethealth.repositories.auth.UserRepository;
import com.example.pethealth.service.parent.IImageService;
import com.example.pethealth.service.profile.ProfileService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ImageService implements IImageService {
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final PictureHandle pictureHandle;
    private final ProfileService profileService;
    private final ResourceLoader resourceLoader;
    private final ImageAvatarUserUtils imageAvatarUserUtils;
    private final PetRepository petRepository;
    private final QuestionRepository questionRepository;
    private final PostRepository postRepository;

    @Override
    public BaseDTO createImage(MultipartFile files) {
        pictureHandle.validateImageInput(files);
        String nameFile = pictureHandle.storeFile(files);
        Image image = Image.builder()
                .url(nameFile)
                .build();
        imageRepository.save(image);
        return BaseDTO.builder()
                .message("success")
                .result(true)
                .build();

    }

    @Override
    public BaseDTO createImageQuestion(long questionId, MultipartFile files) {
        Question question = questionRepository.findById(questionId).orElseThrow(
                ()-> new BadRequestException("dont find by question with id" + questionId)
        );
        pictureHandle.validateImageInput(files);
        String nameFile = pictureHandle.storeFile(files);
        Image image = Image.builder()
                .question(question)
                .url(nameFile)
                .build();
        imageRepository.save(image);
        return BaseDTO.builder()
                .message("success")
                .result(true)
                .build();
    }

    @Override
    public BaseDTO createImageUser(long userId, List<MultipartFile> files)
            {
        try{
            User user = userRepository.findById(userId)
                    .orElseThrow(()-> new BadRequestException("dont find by id: "+ userId));
            List<Image> imagesUser = new ArrayList<>();
            files = files == null ? new ArrayList<>() : files;
            for (MultipartFile file : files){
                pictureHandle.validateImageInput(file);
                String filename = pictureHandle.storeFile(file);
                Image image = createImage(user.getId(), ImageDTO.builder()
                        .user_id(user.getId())
                        .url(filename)
                        .build());
                imagesUser.add(image);
            }
            return BaseDTO.builder()
                    .message("success")
                    .result(true)
                    .build();
        }catch (BadRequestException e){
            return BaseDTO.builder()
                    .message(e.getMessage())
                    .result(false)
                    .build();
        }
            }

    @Override
    public BaseDTO createImagePet(long petId, MultipartFile files) {
        try{
            Pet pet = petRepository.findById(petId)
                    .orElseThrow(()-> new BadRequestException("dont find by Pet with id = " + petId));
            pictureHandle.validateImageInput(files);
            String nameFile = pictureHandle.storeFile(files);
            Image image = Image.builder()
                    .pet(pet)
                    .url(nameFile)
                    .build();
            imageRepository.save(image);
            return BaseDTO.builder()
                    .message("success")
                    .result(true)
                    .build();
        }catch (BadRequestException e){

        }
        return null;
    }

    @Override
    public BaseDTO createImageProductId(long productId, List<MultipartFile> files) {
        return null;
    }

    @Override
    public BaseDTO createImageComment(long id, List<MultipartFile> files) {
        return null;
    }

    @Override
    public Image createImage(long userId, ImageDTO imageDTO) {
        User user = userRepository.findById(imageDTO.getUser_id())
                .orElseThrow(()-> new BadRequestException("cannot find user with id = " + imageDTO.getUser_id()));
        Image image = Image.builder()
                .user(user)
                .url(imageDTO.getUrl())
                .build();
        int size = imageRepository.findByUserId(userId).size();
        return imageRepository.save(image);
    }

    @Override
    public BaseDTO createImageUser(MultipartFile file) {
        try {
            User user = profileService.getLoggedInUser();
            pictureHandle.validateImageInput(file);
            String fileName = pictureHandle.storeFile(file);

            Image image = createImage(user.getId(),ImageDTO.builder()
                    .user_id(user.getId()).url(fileName)
                    .build());
            user.setImage(image);
            userRepository.save(user);

            return BaseDTO.builder()
                    .message("Ảnh đại diện đã được thay đổi")
                    .result(true)
                    .url(image.getUrl())
                    .build();
        }catch (BadRequestException e){
            return BaseDTO.builder()
                    .message(e.getMessage())
                    .result(false)
                    .build();
        }
    }
    @Override
    public BaseDTO createImageFirstCreateUser(MultipartFile file) {
        try {
            pictureHandle.validateImageInput(file);
            String fileName = pictureHandle.storeFile(file);
            Image image = Image.builder()
                    .url(fileName)
                    .build();
            imageRepository.save(image);
            return BaseDTO.builder()
                    .message("success").result(true)
                    .build();
        }catch (BadRequestException e){
            return BaseDTO.builder()
                    .message(e.getMessage()).result(false)
                    .build();
        }
    }

    @Override
    public Image findByUrl(String url) {
        return imageRepository.findByUrl(url)
                .orElseThrow(()-> new BadRequestException("dont find img"));
    }

    @Override
    public BaseDTO deleteImagePet(long petId) {
        try {
            List<Image> images = imageRepository.findByPetId(petId);
            if (images.isEmpty()) {
                return BaseDTO.builder()
                        .result(false)
                        .message("No images found for the pet")
                        .build();
            }
            imageRepository.deleteAll(images);
            return BaseDTO.builder()
                    .result(true)
                    .message("Success")
                    .build();
        } catch (Exception e) {
            return BaseDTO.builder()
                    .result(false)
                    .message("Fail: " + e.getMessage())
                    .build();
        }
    }

    @Override
    public BaseDTO deleteImagePost(long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new BadRequestException("dont find by Post with id " + postId)
        );
        List<Image> imageListPost = post.getListImage();
        if(imageListPost.isEmpty()){
            imageRepository.deleteAll(imageListPost);
        }
        return BaseDTO.builder()
                .result(true)
                .message("Success")
                .build();
    }

    @Override
    public BaseDTO createImagePost(List<MultipartFile> files, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new BadRequestException("dont find by Post with id " + postId)
        );
        List<Image> imagePost = new ArrayList<>();
        files = files == null ? new ArrayList<>() : files;
        for (MultipartFile file : files) {
            pictureHandle.validateImageInput(file);
            String filename = pictureHandle.storeFile(file);
            Image image = Image.builder()
                    .url(filename).post(post)
                    .build();
            imagePost.add(image);
            imageRepository.save(image);
        }
        return BaseDTO.builder()
                .message("success")
                .result(true)
                .build();
    }

    @Override
    public ResponseEntity<?> viewImage(String url) {
        try {
           java.nio.file.Path imagePath = Paths.get("uploads/"+url);
            UrlResource resource = new UrlResource(imagePath.toUri());
            if(resource.exists()){
                return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(resource);
            }else{
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
           return ResponseEntity.notFound().build();
        }
    }

    @Override
    public BaseDTO createImageLogo(MultipartFile files) {
        return null;
    }




}


