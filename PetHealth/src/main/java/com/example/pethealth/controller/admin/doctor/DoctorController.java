package com.example.pethealth.controller.admin.doctor;

import com.example.pethealth.dto.appointmentDTO.DoctorRepair;
import com.example.pethealth.dto.output.ListAppointToDay;
import com.example.pethealth.dto.output.MedicalReportOutput;
import com.example.pethealth.dto.outputDTO.*;
import com.example.pethealth.model.Appointment;
import com.example.pethealth.service.DoctorService;
import com.example.pethealth.service.MedicalReportService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/doctor")
public class DoctorController {
    private final DoctorService doctorService;
    private final MedicalReportService medicalReportService;

    public DoctorController(DoctorService doctorService, MedicalReportService medicalReportService) {
        this.doctorService = doctorService;
        this.medicalReportService = medicalReportService;
    }
    /**
     * controller get
     *
     * **/
    @GetMapping("/getAppointmentAll")
    @ResponseStatus(HttpStatus.OK)
    public PageDTO getAppointmentAll(@RequestParam Map<String, String> params) {
        return doctorService.getAppoimentAll(params);
    }
    @GetMapping("/searchAppointmentUserName")
    public PageDTO searchAppointmentName(@RequestParam Map<String,String>params){
        return doctorService.searchNameUser(params);
    }

    @GetMapping("/getAppointmentDetails/{id}")
    public BaseDTO getAppointmentDetail(@PathVariable long id){
        return doctorService.getAppointmentDetails(id);
    }

    @GetMapping("/searchAppointmentStatus")
    public PageDTO searchAppointmentStatus(@RequestParam Map<String , String> params){
        return doctorService.findByStatus(params);
    }

    @GetMapping("/searchAppointmentCode")
    public AppointmentDTO searchAppointmentCode(@RequestBody String code){
        return doctorService.searchCodeAppointment(code);
    }

    @GetMapping("/getAllMedicalReport")
    @PreAuthorize("hasAnyRole('DOCTOR')")
    @ResponseStatus(HttpStatus.OK)
    public SimpleResponese<MedicalReportOutput> getAllMedicalReport(@RequestParam Map<String, String> params){
        return medicalReportService.getAllMedicalReport(params);
    }
    /**
     * Controller POST
     * **/
    @PostMapping("/createAppointment")
    public AppointmentDTO creatAppointment(
            @RequestBody AppointmentResponse appointment
            ){
        return doctorService.creatAppointment(appointment);
    }

    @PostMapping("/createHaveAccountAppointment")
    public AppointmentDTO createAppointmentHaveAccount(
            @RequestBody AppointmentResponse appointment
    ){
        return doctorService.createAppointmentWithAccount(appointment);
    }
    @PostMapping("/medicalReport")
    @PreAuthorize("hasAnyRole('DOCTOR')")
    public BaseDTO createMedicalReport(@RequestBody MedicalReportDTO medicalReportDTO){
        return medicalReportService.createMedicalReport(medicalReportDTO);
    }


    @DeleteMapping("/deleteAppoint/{id}")
    public AppointmentDTO deleteAppointment(   @PathVariable long id){
        return doctorService.deleteAppointment(id);
    }

    @PutMapping("/editAppointment/{id}")
    public AppointmentDTO editAppointment(@PathVariable long id,
                                          @RequestBody DoctorRepair appointment){
        return doctorService.repairAppointment(id,appointment);
    }

    @GetMapping("/appointments/today/{dateNow}")
    public ListAppointToDay getAppointmentWithDateNow(@PathVariable String dateNow,
                                                      @RequestParam Map<String,String> params) {
        LocalDate localDateNow = LocalDate.parse(dateNow);
        return doctorService.getAppointmentWithDateNow(params,localDateNow);
    }

    @DeleteMapping("/deleteMedicalReport/{id}")
    public BaseDTO deleteMedicalReport(@PathVariable Long id){
        return medicalReportService.deleteMedicalReport(id);
    }

    @GetMapping("/AppointmentWeek")
    public PageDTO getAppointmentActiveWithWeek(@RequestParam Map<String, String> params){
        return doctorService.getAppointmentActiveDaily(params);
    }




}
