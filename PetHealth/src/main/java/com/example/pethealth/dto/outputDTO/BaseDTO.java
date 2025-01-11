package com.example.pethealth.dto.outputDTO;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseDTO {
    private String message;
    private boolean result;
    private String url;
    private List<?> results;
    private Object object;
    private long totalUser;
    private Long totalInvoice;
}
