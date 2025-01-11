package com.example.pethealth.service;

import com.example.pethealth.components.GetPageableUtil;
import com.example.pethealth.components.MapperDateUtil;
import com.example.pethealth.dto.appointmentDTO.AppointmentOutPut;
import com.example.pethealth.dto.outputDTO.BaseDTO;
import com.example.pethealth.dto.outputDTO.PageDTO;
import com.example.pethealth.dto.outputDTO.PetDTO;
import com.example.pethealth.dto.outputDTO.SimpleResponese;
import com.example.pethealth.dto.output.*;
import com.example.pethealth.enums.AppointmentStatus;
import com.example.pethealth.enums.GenderPet;
import com.example.pethealth.exception.BadRequestException;
import com.example.pethealth.model.*;
import com.example.pethealth.repositories.ImageRepository;
import com.example.pethealth.repositories.PetRepository;
import com.example.pethealth.repositories.TypePetRepository;
import com.example.pethealth.service.parent.IPetService;
import com.example.pethealth.service.profile.ProfileService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PetService implements IPetService {
    private final ProfileService profileService;
    private final PetRepository petRepository;
    private final ImageRepository imageRepository;

    public PetService(ProfileService profileService,
                      PetRepository petRepository,
                      TypePetRepository typePetRepository, MapperDateUtil mapperDateUtil, ImageRepository imageRepository) {
        this.profileService = profileService;
        this.petRepository = petRepository;
        this.imageRepository = imageRepository;
    }

    @Override
    public CreatePetOutPut createPet(PetDTO petDTO) {
       try {
           User user = profileService.getLoggedInUser();
           Pet newPet = Pet.builder()
                   .name(petDTO.getName())
                   .genderPet(petDTO.getGenderPet())
                   .typePet(petDTO.getTypePet())
                   .birthDay(petDTO.getBirthDay())
                   .color(petDTO.getFurColor())
                   .weight(petDTO.getWeight())
                   .adoptive(petDTO.getAdoptive())
                   .user(user)
                   .crystal(petDTO.getCrystal())
                   .statusPet(petDTO.getStatusPet())
                   .build();
           newPet.setCreateBy(user.getFullName());
           petRepository.save(newPet);
           return CreatePetOutPut.builder()
                   .message("success!!")
                   .petId(newPet.getId())
                   .results(true)
                   .build();
       }catch (BadRequestException e) {
           return CreatePetOutPut.builder()
                   .results(false)
                   .message(e.getMessage())
                   .build();
       }
    }

    @Override
    public PageDTO getAllPet(Map<String, String> params) {
        try{
            Pageable pageable = GetPageableUtil.getPageable(params);
            SimpleResponese<Pet> petSimpleResponese = petRepository.getAllPet(params, pageable);
            return PageDTO.builder()
                    .result(true)
                    .message("success")
                    .simpleResponese(petSimpleResponese)
                    .build();
        }catch (BadRequestException e){
           return PageDTO.builder()
                   .result(false).message("false")
                   .build();
        }
    }

    @Override
    public PetDetails findByIdPet(long petId) {
        Pet pet = petRepository.findById(petId).orElseThrow(
                ()-> new BadRequestException("dont find pet with id = " + petId)
        );
        String genderPet;
        if(pet.getGenderPet().equals(GenderPet.DUC)){
            genderPet = "Đực";
        }else{
            genderPet = "Cái";
        }
        StringBuilder url = new StringBuilder("http://localhost:8080/uploads/");
        List<Image> image = imageRepository.findByPetId(pet.getId());
        if(!image.isEmpty()){
            for(Image item: image){
                url.append(item.getUrl());
            }
        }
        return PetDetails.builder()
                .colorPet(pet.getColor())
                .namePet(pet.getName())
                .genderPet(genderPet)
                .oldPet(getOldPet(pet.getBirthDay()))
                .crystal(pet.getCrystal())
                .adoptive(MapperDateUtil.formatTimestamp(pet.getAdoptive()))
                .birthDay(MapperDateUtil.formatTimestamp(pet.getBirthDay()))
                .urlImagePet(url.toString())
                .totalAppointment(pet.getAppointments().size())
                .totalMedicalReport(pet.getMedicalReports().size())
                .listAppointmentOfPet(getAllAppointmentOutPut(pet.getAppointments()))
                .build();
    }

    @Override
    public PetWithStatus getPetWithStatusAppointmentACTIVE(Map<String, String> params) {
        try{
            Pageable pageable = GetPageableUtil.getPageable(params);
            SimpleResponese<Pet> petSimpleResponese = petRepository.getPetWithStatus(params, pageable);
            List<Pet> results = new ArrayList<>();
            for(Pet items: petSimpleResponese.results){
                for(Appointment appointment : items.getAppointments()){
                    if(appointment.getAppointmentStatus() == AppointmentStatus.ACTIVE){
                        results.add(items);
                        break;
                    }
                }
            }

            return PetWithStatus.builder()
                    .results(true)
                    .message("success")
                    .petList(results)
                    .totalAppointmentActive(results.size())
                    .build();
        }catch (BadRequestException e){
            return PetWithStatus.builder()
                    .results(false).message("false")
                    .build();
        }
    }

    @Override
    public PetWithStatus getPetWithMedicalReport(Map<String, String> params) {
        try{
            Pageable pageable = GetPageableUtil.getPageable(params);
            SimpleResponese<Pet> petSimpleResponese = petRepository.getAllPet(params,pageable);
            Set<Pet> results = petSimpleResponese.results.stream()
                    .filter(pet -> pet.getMedicalReports().stream()
                            .anyMatch(medicalReport -> medicalReport.getFollowSchedule() != null))
                    .collect(Collectors.toSet());
            return PetWithStatus.builder()
                    .results(true)
                    .message("success")
                    .totalMedicalActive(results.size())
                    .petList(new ArrayList<>(results))
                    .build();

        }catch (BadRequestException e){
            return PetWithStatus.builder()
                    .message(e.getMessage()).results(false)
                    .build();
        }

    }

    @Override
    public BaseDTO updatePet(Long petId, PetDTO petDTO) {
        Pet pet = petRepository.findById(petId).orElseThrow(
                () -> new BadRequestException("Pet not found with id = " + petId)
        );
        if (petDTO.getName() != null && !petDTO.getName().isEmpty()) {
            pet.setName(petDTO.getName());
        }

        if (petDTO.getGenderPet() != null) {
            pet.setGenderPet(petDTO.getGenderPet());
        }

        if (petDTO.getFurColor() != null && !petDTO.getFurColor().isEmpty()) {
            pet.setColor(petDTO.getFurColor());
        }

        if (petDTO.getAdoptive() != null) {
            pet.setAdoptive(petDTO.getAdoptive());
        }

        if (petDTO.getBirthDay() != null) {
            pet.setBirthDay(petDTO.getBirthDay());
        }

        if (petDTO.getCrystal() != null && !petDTO.getCrystal().isEmpty()) {
            pet.setCrystal(petDTO.getCrystal());
        }
        petRepository.save(pet);
        return BaseDTO.builder()
                .result(true)
                .message("Pet updated successfully")
                .build();
    }

    @Override
    public BaseDTO deletePet(Long petId) {
        Pet pet = petRepository.findById(petId).orElseThrow(
                ()-> new BadRequestException("dont find by Id " + petId)
        );
        petRepository.delete(pet);
        return BaseDTO.builder()
                .result(true).message("Pet had deleted!!")
                .build();
    }

    private int getOldPet(Date birthDay){
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(birthDay);
        int birthDayPet  = calendar.get(Calendar.YEAR);
        int birthMonth = calendar.get(Calendar.MONTH);
        int birthDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.setTime(date);
        int yearNow = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int age = yearNow - birthDayPet;
        if (currentMonth < birthMonth || (currentMonth == birthMonth && currentDayOfMonth < birthDayOfMonth)) {
            age--;
        }
        return age;
    }

    private List<AppointmentOutPut> getAllAppointmentOutPut(List<Appointment> appointment){
        List<AppointmentOutPut> results = new ArrayList<>();
        for(Appointment item: appointment){
            String status = "";
            if (item.getAppointmentStatus() == AppointmentStatus.ACTIVE) {
                status = "Đã được duyệt";
            }
            if(item.getAppointmentStatus() == AppointmentStatus.PENDING){
                status = "Đang chờ duyệt";
            }
            if(item.getAppointmentStatus() == AppointmentStatus.DISABLE){
                status = "Đã bị hủy";
            }
            AppointmentOutPut appointmentOutPut = AppointmentOutPut.builder()
                    .code(item.getCode())
                    .doctor(item.getDoctorInCharge().getUsername())
                    .startDate(MapperDateUtil.formatLocalDateTime(item.getStartDate()))
                    .status(status)
                    .message("")
                    .namePet(item.getNamePet())
                    .build();
            results.add(appointmentOutPut);
        }
        return results;
    }
}
