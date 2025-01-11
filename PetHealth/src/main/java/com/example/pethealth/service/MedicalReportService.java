
package com.example.pethealth.service;

import com.example.pethealth.components.GetPageableUtil;
import com.example.pethealth.dto.outputDTO.BaseDTO;
import com.example.pethealth.dto.outputDTO.MedicalReportDTO;
import com.example.pethealth.dto.outputDTO.PageDTO;
import com.example.pethealth.dto.outputDTO.SimpleResponese;
import com.example.pethealth.dto.output.MedicalReportOutput;
import com.example.pethealth.enums.AppointmentStatus;
import com.example.pethealth.exception.BadRequestException;
import com.example.pethealth.model.Appointment;
import com.example.pethealth.model.MedicalReport;
import com.example.pethealth.model.User;
import com.example.pethealth.repositories.AppointmentRepository;
import com.example.pethealth.repositories.MedicalRepository;
import com.example.pethealth.service.parent.IMedicalReportService;
import com.example.pethealth.service.profile.ProfileService;
import com.example.pethealth.utils.ConverDateTime;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class MedicalReportService implements IMedicalReportService {
    private final ModelMapper modelMapper;
    private final MedicalRepository medicalRepository;
    private final AppointmentRepository appointmentRepository;
    private final ProfileService profileService;
    @Override
    public BaseDTO createMedicalReport(MedicalReportDTO medicalReportDTO) {
        try {
            User doctor = profileService.getLoggedInUser();
            if(medicalReportDTO.getAppointmentId() != null && medicalReportDTO.getAppointmentId() > 0){
                Appointment appointment = appointmentRepository.findById(medicalReportDTO.getAppointmentId())
                        .orElseThrow(
                                ()-> new BadRequestException("dont find by id appointment with id = " + medicalReportDTO.getAppointmentId())
                        );
                appointment.setAppointmentStatus(AppointmentStatus.COMPLETE);
                appointmentRepository.save(appointment);
                MedicalReport medicalReport = MedicalReport.builder()
                        .doctor(doctor)
                        .namePet(medicalReportDTO.getNamePet())
                        .oldPet(medicalReportDTO.getOldPet())
                        .weightPet(medicalReportDTO.getWeightPet())
                        .genderPet(medicalReportDTO.getGenderPet())
                        .symptom(medicalReportDTO.getSymptom())
                        .numberPhone(medicalReportDTO.getNumberPhone())
                        .address(medicalReportDTO.getAddress())
                        .petOwner(medicalReportDTO.getPetOwner())
                        .followSchedule(medicalReportDTO.getFollowSchedule())
                        .diagnosed(medicalReportDTO.getDiagnosed())
                        .appointment(appointment)
                    .build();
                medicalRepository.save(medicalReport);
                return BaseDTO.builder()
                        .message("success")
                        .result(true)
                        .build();
            }
            MedicalReport medicalReport = MedicalReport.builder()
                    .doctor(doctor)
                    .namePet(medicalReportDTO.getNamePet())
                    .oldPet(medicalReportDTO.getOldPet())
                    .weightPet(medicalReportDTO.getWeightPet())
                    .genderPet(medicalReportDTO.getGenderPet())
                    .symptom(medicalReportDTO.getSymptom())
                    .numberPhone(medicalReportDTO.getNumberPhone())
                    .address(medicalReportDTO.getAddress())
                    .petOwner(medicalReportDTO.getPetOwner())
                    .followSchedule(medicalReportDTO.getFollowSchedule())
                    .diagnosed(medicalReportDTO.getDiagnosed())
                    .build();
            medicalRepository.save(medicalReport);
            return BaseDTO.builder()
                    .message("success")
                    .result(true)
                    .build();
        } catch (Exception e) {
            return BaseDTO.builder()
                    .message(e.getMessage())
                    .result(false)
                    .build();
        }
    }
    @Override
    public SimpleResponese<MedicalReportOutput> getAllMedicalReport(Map<String, String> params) {
            Pageable pageable = GetPageableUtil.getPageable(params);
            SimpleResponese<MedicalReport> reportSimpleResponese = medicalRepository.findByCondition(params,pageable);
            List<MedicalReportOutput> results = reportSimpleResponese.getResults().stream()
                    .map(report -> {
                        MedicalReportOutput reportDTO = modelMapper.map(report, MedicalReportOutput.class);
                        if(report.getCreatedDate() != null){
                            String createDate = ConverDateTime.convertDatetime(String.valueOf(report.getCreatedDate()));
                            reportDTO.setCreatedDate(createDate);
                        }
                        if (report.getFollowSchedule() != null) {
                            String formattedDate = ConverDateTime.convertDateTimeSchedule(String.valueOf(reportDTO.getFollowSchedule()));
                            reportDTO.setFollowSchedule(formattedDate);
                        }
                        if(report.getAppointment() != null && report.getAppointment().getPet() != null){
                            if(report.getAppointment().getPet().getImagePet() != null){
                                String urlPet = "http://localhost:8080/uploads/" + report.getAppointment().getPet().getImagePet().get(0).getUrl();
                                reportDTO.setUrlPet(urlPet);
                            }
                        }else{
                            String urlPet = "http://localhost:8080/uploads/222bf6eb-aa92-4d59-b687-55d760597b5a_Screenshot%202024-11-13%20021405.png";
                            reportDTO.setUrlPet(urlPet);
                        }
                        if(report.getPrescription()!=null){
                            reportDTO.setHavePrescription(false);
                        }
                        if(report.getInvoice()!=null){
                            reportDTO.setHaveInvoice(false);
                        }
                        return reportDTO;
                    }).toList();
            return SimpleResponese.<MedicalReportOutput>builder()
                    .results(results)
                    .limit(reportSimpleResponese.getLimit())
                    .page(reportSimpleResponese.getPage())
                    .totalItem(reportSimpleResponese.getTotalItem())
                    .totalPage(reportSimpleResponese.getTotalPage())
                    .build();
    }

    @Override
    public PageDTO searchMedicalReport(Map<String, String> params) {
        return null;
    }

    @Override
    public BaseDTO deleteMedicalReport(Long medicalReportId) {
        try {
            MedicalReport medicalReport = medicalRepository.findById(medicalReportId)
                    .orElseThrow(
                            () -> new BadRequestException("Không tìm thấy báo cáo y tế với ID = " + medicalReportId)
                    );
            if(medicalReport.getAppointment() == null){
                medicalRepository.delete(medicalReport);
            }else{
                Appointment appointment = medicalReport.getAppointment();
                appointment.setAppointmentStatus(AppointmentStatus.ACTIVE);
                appointmentRepository.save(appointment);
                medicalRepository.delete(medicalReport);
            }

            return BaseDTO.builder()
                    .message("Xóa thành công báo cáo y tế!")
                    .result(true)
                    .build();
        } catch (BadRequestException e) {
            return BaseDTO.builder()
                    .message(e.getMessage())
                    .result(false)
                    .build();
        } catch (DataIntegrityViolationException e) {
            return BaseDTO.builder()
                    .message("Không thể xóa báo cáo y tế vì đang được sử dụng ở nơi khác." + e.getMessage())
                    .result(false)
                    .build();
        } catch (Exception e) {
            return BaseDTO.builder()
                    .message("Đã xảy ra lỗi: " + e.getMessage())
                    .result(false)
                    .build();
        }
    }


}
