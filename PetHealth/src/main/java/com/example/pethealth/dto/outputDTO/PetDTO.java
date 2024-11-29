package com.example.pethealth.dto.outputDTO;

import com.example.pethealth.enums.GenderPet;
import com.example.pethealth.enums.StatusPet;
import com.example.pethealth.model.TypePet;
import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PetDTO {
    private String name;
    private String typePet;
    private GenderPet genderPet;
    private Date birthDay;
    private Date adoptive;
    private Long weight;
    private String furColor;
    private String crystal;
    private StatusPet statusPet;
}
