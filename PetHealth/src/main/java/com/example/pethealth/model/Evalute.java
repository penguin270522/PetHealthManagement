package com.example.pethealth.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Evalute extends BaseEntity {

    private Long value;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Product product;


}
