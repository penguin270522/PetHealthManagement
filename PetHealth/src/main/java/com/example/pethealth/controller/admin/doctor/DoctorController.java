package com.example.pethealth.controller.admin.doctor;

import com.example.pethealth.dto.AppointmentDTO;
import com.example.pethealth.dto.BaseDTO;
import com.example.pethealth.dto.MedicalReportDTO;
import com.example.pethealth.dto.PageDTO;
import com.example.pethealth.enums.AppointmentStatus;
import com.example.pethealth.model.Appointment;
import com.example.pethealth.service.DoctorService;
import com.example.pethealth.service.MedicalReportService;
import com.github.javafaker.Faker;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/doctor")
public class DoctorController {
    private final DoctorService doctorService;
    private final MedicalReportService medicalReportService;

    public DoctorController(DoctorService doctorService, MedicalReportService medicalReportService) {
        this.doctorService = doctorService;
        this.medicalReportService = medicalReportService;
    }

    @GetMapping("/getAppointmentAll")
    @ResponseStatus(HttpStatus.OK)
    public PageDTO getAppointmentAll(@RequestParam Map<String, String> params) {
        return doctorService.getAppoimentAll(params);
    }

    @PostMapping("/creatAppoinment")
    public AppointmentDTO creatAppointment(
            @RequestBody Appointment appointment
            ){
        return doctorService.creatAppointment(appointment);
    }

    @PostMapping("/generateFakeAppointments")
    public AppointmentDTO generateFakeAppointment(){

        Faker faker = new Faker();

        for (int i = 0; i < 100; i++) {
            String appointmentNameUser = faker.name().fullName();
            String appointmentNamePet = faker.animal().name();
            String numberPhone = faker.phoneNumber().phoneNumber();
            LocalDateTime startDate = faker.date().future(10, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();


            Appointment appointment = Appointment.builder()
                    .appointmentStatus(AppointmentStatus.PENDING)
                    .nameUser(appointmentNameUser)
                    .namePet(appointmentNamePet)
                    .numberPhone(numberPhone)
                    .startDate(startDate)
                    .build();

            doctorService.creatAppointment(appointment);
        }

        return AppointmentDTO.builder()
                .message("Generated 1,000,000 fake appointments successfully")
                .result(true)
                .build();
    }

    @DeleteMapping("/deleteAppoint/{id}")
    public AppointmentDTO deleteAppointment(   @PathVariable long id){
        return doctorService.deleteAppointment(id);
    }

    @PutMapping("/editAppointment/{id}")
    public AppointmentDTO editAppointment(@PathVariable long id,
                                          @RequestBody Appointment appointment){
        return doctorService.repairAppointment(id,appointment);
    }

    @GetMapping("/searchAppointmentUserName")
    public PageDTO searchAppointmentName(@RequestParam Map<String,String>params){
        return doctorService.searchNameUser(params);
    }

    @GetMapping("/searchAppointmentStatus")
    public PageDTO searchAppointmentStatus(@RequestParam Map<String , String> params){
        return doctorService.findByStatus(params);
    }

    @GetMapping("/searchAppointmentCode")
    public AppointmentDTO searchAppointmentCode(@RequestBody String code){
        return doctorService.searchCodeAppointment(code);
    }


    @PostMapping("/medicalReport")
    public BaseDTO createMedicalReport(@RequestBody MedicalReportDTO medicalReportDTO){
        return medicalReportService.createMedicalReport(medicalReportDTO);
    }
}
