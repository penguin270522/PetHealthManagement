package com.example.pethealth.dto.invoiceDTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceMedicineListDTO {
    private String name;
    private Long fee;
    private Long quality;
    private Long totalFee;
}
