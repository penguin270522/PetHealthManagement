package com.example.pethealth.service.parent;

import com.example.pethealth.dto.outputDTO.BaseDTO;

import java.util.Map;

public interface IRevenueService {
    BaseDTO getRevenueOfDoctor();
    BaseDTO getRevenueOfYear(int year);
    BaseDTO getRevenueOfMonth(int month, int year);
}
