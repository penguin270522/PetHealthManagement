package com.example.pethealth.model;

import com.example.pethealth.enums.GenderPet;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pet extends BaseEntity{

    @Column(nullable = false, unique = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private GenderPet genderPet;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private TypePet typePet;

    private long weight;

    private Date birthDay;

    private String color;

    private String size;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;



}
