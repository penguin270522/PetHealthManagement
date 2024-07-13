package com.example.pethealth.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name;

    @Column(unique = false, nullable = false)
    private Date EXP;

    @Column(unique = false, nullable = false)
    private Date MFG;

    @Column(unique = false, nullable = false)
    private float price;

    @Column(unique = false, nullable = false)
    private String note;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "type_product_id")
    private TypeProduct typeProduct;

    @Column(unique = false, nullable = true)
    private Long quantity;

}
