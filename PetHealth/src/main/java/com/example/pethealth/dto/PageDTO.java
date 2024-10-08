package com.example.pethealth.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageDTO {
    private String message;
    private boolean result;
    private SimpleResponese simpleResponese;
}
