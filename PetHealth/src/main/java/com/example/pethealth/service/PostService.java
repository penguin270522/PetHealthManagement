package com.example.pethealth.service;

import com.example.pethealth.components.GetPageableUtil;
import com.example.pethealth.dto.outputDTO.BaseDTO;
import com.example.pethealth.dto.outputDTO.PageDTO;
import com.example.pethealth.dto.outputDTO.SimpleResponese;
import com.example.pethealth.dto.postDTO.PostAll;
import com.example.pethealth.dto.postDTO.PostDetail;
import com.example.pethealth.dto.postDTO.PostInput;
import com.example.pethealth.enums.PostStatus;
import com.example.pethealth.exception.BadRequestException;
import com.example.pethealth.model.*;
import com.example.pethealth.repositories.CommentRepository;
import com.example.pethealth.repositories.ImageRepository;
import com.example.pethealth.repositories.PostRepository;
import com.example.pethealth.repositories.TypePostRepository;
import com.example.pethealth.service.parent.IPostService;
import com.example.pethealth.service.profile.ProfileService;
import com.example.pethealth.utils.ConverDateTime;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Service
public class PostService implements IPostService {
    private final ProfileService profileService;
    private final PostRepository postRepository;
    private final ImageRepository imageRepository;
    private final TypePostRepository typePostRepository;
    private final CommentRepository commentRepository;


    public PostService(ProfileService profileService, PostRepository postRepository, ImageRepository imageRepository, TypePostRepository typePostRepository, CommentRepository commentRepository) {
        this.profileService = profileService;
        this.postRepository = postRepository;
        this.imageRepository = imageRepository;
        this.typePostRepository = typePostRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public BaseDTO createPost(PostInput postInput) {
        User user = profileService.getLoggedInUser();
        TypePost typePost = typePostRepository.findById(postInput.getTypePostId()).orElseThrow(
                ()-> new BadRequestException("dont find type Post with id " + postInput.getTypePostId())
        );
        Post post = Post.builder()
                .title(postInput.getTitle())
                .user(user)
                .postStatus(PostStatus.PENDING)
                .content(postInput.getContent())
                .typePost(typePost)
                .build();
        postRepository.save(post);
        return BaseDTO.builder()
                .message("Bài viết được tạo thành công")
                .object(post)
                .result(true)
                .build();
    }

    @Override
    public BaseDTO getPostDetail(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new BadRequestException("Dont find by Post with Id = " + postId)
        );
        List<Image> urlImage = imageRepository.findByPostId(postId);
        List<String> imagePost = new ArrayList<>();
        for(Image item : urlImage){
            String url = "http://localhost:8080/uploads/" + item.getUrl();
            imagePost.add(url);
        }

        List<Comment> comments = commentRepository.findByPostId(postId);
        List<String> commentList = new ArrayList<>();
        for(Comment item : comments){
            String comment = item.getContent();
            commentList.add(comment);
        }
        PostDetail postDetail = PostDetail.builder()
                .title(post.getTitle())
                .status(post.getPostStatus().name())
                .urlImage(imagePost).comments(commentList)
                .content(post.getContent())
                .createDay(ConverDateTime.convertDatetime(String.valueOf(post.getCreatedDate())))
                .build();
        return BaseDTO.builder()
                .message("success").result(true).object(postDetail)
                .build();
    }
    @Override
    public BaseDTO updatePost(PostInput postInput, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new BadRequestException("Dont find by Post with Id = " + postId)
        );
        post = Post.builder()
                .postStatus(postInput.getPostStatus())
                .message(postInput.getMessage())
                .build();
        postRepository.save(post);
        return BaseDTO.builder()
                .message("success").result(true)
                .build();
    }

    @Override
    public BaseDTO deletePost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new BadRequestException("Dont find by Post with Id = " + postId)
        );
        postRepository.delete(post);
        return BaseDTO.builder()
                .result(true)
                .message("success")
                .build();
    }

    @Override
    public PageDTO getAllPost(Map<String, String> params) {
        Pageable pageable = GetPageableUtil.getPageable(params);
        SimpleResponese<Post> results = postRepository.getAllPost(params,pageable);
        List<PostAll> result = new ArrayList<>();
        for(Post items : results.results){
            PostAll postAll = PostAll.builder()
                    .id(items.getId())
                    .userCreate(items.getUser().getFullName())
                    .status(items.getPostStatus().getDisplayStatus())
                    .typePost(items.getTypePost().getName())
                    .title(items.getTitle())
                    .build();
            result.add(postAll);
        }
        SimpleResponese<PostAll> simpleResponese = SimpleResponese.<PostAll>builder()
                .limit(results.limit)
                .totalItem(results.totalItem)
                .page(results.page)
                .results(result)
                .build();
        return PageDTO.builder()
                .result(true)
                .message("success")
                .simpleResponese(simpleResponese)
                .build();
    }


}
