package com.example.pethealth.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TypePost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false, unique = true)
    private String name;

    @PrePersist
    @PreUpdate
    public void generateCode() {
        if (this.name != null && !this.name.isEmpty()) {
            this.code = this.name.trim()
                    .toUpperCase()
                    .replaceAll("[^A-Z0-9\\s]", "")
                    .replaceAll("\\s+", "-");
        }
    }
}
