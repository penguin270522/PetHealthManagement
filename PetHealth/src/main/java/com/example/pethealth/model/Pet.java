package com.example.pethealth.model;

import com.example.pethealth.enums.GenderPet;
import com.example.pethealth.enums.StatusPet;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pet extends BaseEntity{

    @Column(nullable = false, unique = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private GenderPet genderPet;

    private String typePet;

    private long weight;

    private Date birthDay;

    private String color;

    private String size;

    private Date adoptive;

    private String crystal;

    private StatusPet statusPet;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> imagePet;
    @OneToMany(mappedBy = "pet" , fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Appointment> appointments;
    @OneToMany(mappedBy = "pet", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<MedicalReport> medicalReports;
}
