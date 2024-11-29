package com.example.pethealth.repositories;

import com.example.pethealth.model.Image;
import com.example.pethealth.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image,Long> {
    List<Image> findByUserId(long id);
    Optional<Image> findByUrl(String url);
    List<Image> findByPetId(long id);
}
