package com.example.pethealth.components;

import com.example.pethealth.model.Image;
import com.example.pethealth.service.ImageService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class ImageAvatarUserUtils {
    @Value("${img.avatarUser}")
    private String url;

}
