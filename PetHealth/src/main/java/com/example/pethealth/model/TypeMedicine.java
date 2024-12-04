package com.example.pethealth.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TypeMedicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "typeMedicine", fetch = FetchType.EAGER,cascade = CascadeType.ALL, orphanRemoval = true)
    List<Medicine> medicineList;

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
