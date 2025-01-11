package com.example.pethealth.service.profile;

import com.example.pethealth.dto.outputDTO.BaseDTO;
import com.example.pethealth.dto.outputDTO.ProfileResponse;
import com.example.pethealth.dto.outputDTO.UpdateUserDTO;
import com.example.pethealth.exception.BadRequestException;
import com.example.pethealth.model.User;
import com.example.pethealth.repositories.auth.UserRepository;
import com.example.pethealth.service.parent.IProfileService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ProfileService implements IProfileService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public ProfileService(ModelMapper modelMapper, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    public ProfileResponse getMyProfile(){
        User user = getLoggedInUser();
        return ProfileResponse.builder()
                .user(user)
                .build();
    }
    @Override
    public BaseDTO updateProfileUser(UpdateUserDTO userDTO) {
        try {
            User user = getLoggedInUser();
            modelMapper.map(userDTO, user);
            userRepository.save(user);
            return BaseDTO.builder()
                    .result(true).message("success!!")
                    .build()  ;
        }catch (BadRequestException e){
            return BaseDTO.builder()
                    .result(false).message("false")
                    .build();
        }
    }

    @Override
    public BaseDTO getTotalPet() {
        User user = getLoggedInUser();
        int totalPet = 0;
        if(user.getPet() != null){
            totalPet = user.getPet().size();
        }
        return BaseDTO.builder()
                .result(true).object(totalPet)
                .build();
    }

    public  User getLoggedInUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


}
