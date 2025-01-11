package com.example.pethealth.dto.invoiceDTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceOutPut {
    private Long id;
    private String code;
    private String fullNameClient;
    private String namePet;
    private String nameDoctor;
    private String createDate;
    private double totalPrice;
    private double totalAmountPaid;
    private double totalPriceDontPaid;
    private double discountAmount;
}
