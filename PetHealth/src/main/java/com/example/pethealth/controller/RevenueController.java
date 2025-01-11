package com.example.pethealth.controller;

import com.example.pethealth.dto.outputDTO.BaseDTO;
import com.example.pethealth.service.RevenueService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/revenue")
public class RevenueController {
    private final RevenueService revenueService;

    public RevenueController(RevenueService revenueService) {
        this.revenueService = revenueService;
    }

    @GetMapping("/doctor")
    public BaseDTO getRevenueOfDoctor(){
        return revenueService.getRevenueOfDoctor();
    }

    @GetMapping("/year")
    public BaseDTO getRevenueOfYear(@RequestParam int year){
        return revenueService.getRevenueOfYear(year);
    }

    @GetMapping("/month")
    public BaseDTO getRevenueOfMonth(@RequestParam int month,
                                     @RequestParam int year){
        return revenueService.getRevenueOfMonth(month,year);
    }
}
