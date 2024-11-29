package com.example.pethealth.dto.invoiceDTO;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceBase {
    private String message;
    private boolean result;
    private List<InvoiceOutPut> invoiceOutPut;
}
