package com.example.pethealth.dto.revenueDTO;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RevenueOfDoctorOutPut {
    private Long percentageOfSales;
    private String imageDoctor;
    private String nameDoctor;
    
}
