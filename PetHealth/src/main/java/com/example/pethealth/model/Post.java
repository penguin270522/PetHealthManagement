package com.example.pethealth.model;

import com.example.pethealth.enums.PostStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Post extends BaseEntity{
    @Lob
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private PostStatus postStatus;

    private String message;

    @ManyToOne
    @JoinColumn(name = "type_post_id")
    @JsonIgnore
    private TypePost typePost;

    @OneToMany(mappedBy = "post",cascade = CascadeType.REMOVE)
    private List<Comment> listComment;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Image> listImage;
}
