package com.example.pethealth.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseDTO {
    private String message;
    private boolean result;
}
