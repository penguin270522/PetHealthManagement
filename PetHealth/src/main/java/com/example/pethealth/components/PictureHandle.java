package com.example.pethealth.components;

import com.example.pethealth.exception.BadRequestException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
@Component
public class PictureHandle {
    public void validateImageInput(MultipartFile imageFile) {
        if(imageFile == null) {
            throw new BadRequestException("File cannot empty");
        }

        if (imageFile.getSize() > 300 * 1024 * 1024) {
            throw new BadRequestException("File is too large! Maximum size is 5MB");
        }

        String contentType = imageFile.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new BadRequestException("File must be an image");
        }
    }
    private boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }
    public String storeFile(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        String uniqueFilename = UUID.randomUUID().toString() + "_" + filename;
        java.nio.file.Path uploadDir = Paths.get("uploads");
        if (!Files.exists(uploadDir)) {
            try {
                Files.createDirectories (uploadDir);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        java.nio.file.Path destination = Paths.get(uploadDir.toString(), uniqueFilename);
        try {
            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return uniqueFilename;
    }
}