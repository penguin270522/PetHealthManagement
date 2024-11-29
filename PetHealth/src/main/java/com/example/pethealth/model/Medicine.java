package com.example.pethealth.model;

import com.example.pethealth.enums.UnitMedicine;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Medicine extends BaseEntity{
    private String name;

    private String information;

    @ManyToOne
    @JoinColumn(name = "type_Medicine_id")
    @JsonIgnore
    private TypeMedicine typeMedicine;

    private Long price;


    private Long countMedicine;

    @Enumerated(EnumType.STRING)
    private UnitMedicine unitMedicine;

}
