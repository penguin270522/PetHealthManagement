package com.example.pethealth.service;

import com.example.pethealth.dto.outputDTO.BaseDTO;
import com.example.pethealth.dto.revenueDTO.RevenueOfDoctorOutPut;
import com.example.pethealth.exception.BadRequestException;
import com.example.pethealth.model.Invoice;
import com.example.pethealth.model.Role;
import com.example.pethealth.model.User;
import com.example.pethealth.repositories.InvoiceRepository;
import com.example.pethealth.repositories.auth.RoleRepository;
import com.example.pethealth.repositories.auth.UserRepository;
import com.example.pethealth.service.auth.UserService;
import com.example.pethealth.service.parent.IRevenueService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Service
public class RevenueService implements IRevenueService {
    private final UserRepository userRepository;
    private final InvoiceRepository invoiceRepository;
    private final RoleRepository roleRepository;

    public RevenueService(UserRepository userRepository, InvoiceRepository invoiceRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.invoiceRepository = invoiceRepository;
        this.roleRepository = roleRepository;
    }


    @Override
    public BaseDTO getRevenueOfDoctor() {
       List<Invoice> getAllInvoice = invoiceRepository.findAll();
       Long totalInvoice = invoiceRepository.getCountInvoice();
       Long totalUser = userRepository.getTotalUser();
       double sumInvoice = 0;

       for(Invoice items : getAllInvoice){
           if(items.getTotal() < items.getAmountReceived()){
               sumInvoice += items.getTotal();
           }else{
               sumInvoice += items.getAmountReceived();
           }
       }
       Role role = roleRepository.findByCode("ROLE_DOCTOR").orElseThrow(
               ()-> new BadRequestException("dont find by role")
       );
       List<User> doctorList = userRepository.findByRole(role);
       List<RevenueOfDoctorOutPut> revenueOfDoctorOutPuts = new ArrayList<>();
       for(User doctor : doctorList){
           Long totalAmountReceivedByUserId = 0L;
           if(invoiceRepository.calculateTotalAmountReceivedByDoctorId(doctor.getId()) != null){
               totalAmountReceivedByUserId = invoiceRepository.calculateTotalAmountReceivedByDoctorId(doctor.getId());
           }
           RevenueOfDoctorOutPut items = RevenueOfDoctorOutPut.builder()
                   .imageDoctor("http://localhost:8080/uploads/" + doctor.getImage().getUrl())
                   .percentageOfSales((long) (totalAmountReceivedByUserId / sumInvoice * 100))
                   .nameDoctor(doctor.getFullName())
                   .build();
           revenueOfDoctorOutPuts.add(items);
       }
        return BaseDTO.builder()
                .result(true).results(revenueOfDoctorOutPuts)
                .object(sumInvoice)
                .totalInvoice(totalInvoice)
                .totalUser(totalUser)
                .build();
    }

    @Override
    public BaseDTO getRevenueOfYear(int year) {
        Long totalRevenueOfYear = invoiceRepository.getTotalAmountReceivedOfYear(year);
        return BaseDTO.builder()
                .result(true)
                .object(totalRevenueOfYear)
                .build();
    }

    @Override
    public BaseDTO getRevenueOfMonth(int month,int year) {
        Long totalRevenueOfMonthAndOfYear = 0L;
        List<Invoice> results = invoiceRepository.getInvoiceWithYearAndMonth(year, month);
        for(Invoice items : results){
            if(items.getAmountReceived() >= items.getTotal()){
                totalRevenueOfMonthAndOfYear += items.getTotal();
            }else{
                totalRevenueOfMonthAndOfYear += items.getAmountReceived();
            }
        }
        return BaseDTO.builder()
                .result(true)
                .object(totalRevenueOfMonthAndOfYear)
                .build();
    }
}
