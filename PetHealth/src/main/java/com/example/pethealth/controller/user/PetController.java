package com.example.pethealth.controller.user;

import com.example.pethealth.dto.outputDTO.BaseDTO;
import com.example.pethealth.dto.outputDTO.PageDTO;
import com.example.pethealth.dto.outputDTO.PetDTO;
import com.example.pethealth.dto.output.CreatePetOutPut;
import com.example.pethealth.dto.output.PetDetails;
import com.example.pethealth.dto.output.PetWithStatus;
import com.example.pethealth.service.PetService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/pets")
public class PetController {
    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping("/createPet")
    public CreatePetOutPut createPetOutPut(@RequestBody PetDTO petDTO){
        return petService.createPet(petDTO);
    }

    @GetMapping("/getAll")
    public PageDTO createPetOutPut(@RequestParam Map<String, String> params){
        return petService.getAllPet(params);
    }

    @GetMapping("/{id}")
    public PetDetails getPetWithId(@PathVariable("id") Long petId){
        return petService.findByIdPet(petId);
    }
    @GetMapping("/getPetWithStatusActive")
    public PetWithStatus getPetWithStatus(@RequestParam Map<String,String> params){
        return petService.getPetWithStatusAppointmentACTIVE(params);
    }

    @GetMapping("/getPetHaveMedicalReport")
    public PetWithStatus getPetHaveMedicalReport(@RequestParam Map<String,String>params){
        return petService.getPetWithMedicalReport(params);
    }

    @PostMapping("/updatePet/{id}")
    public BaseDTO updatePetWithId(@PathVariable("id") Long petId,
                                   @RequestBody PetDTO petDTO){
        return petService.updatePet(petId,petDTO);
    }
    @DeleteMapping("/deletePet/{id}")
    public BaseDTO deletePet(@PathVariable("id") Long petId){
        return petService.deletePet(petId);
    }
}
