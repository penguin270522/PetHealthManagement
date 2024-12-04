package com.example.pethealth.dto.output;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatePetOutPut {
    private String message;
    private boolean results;
    private long petId;
}
