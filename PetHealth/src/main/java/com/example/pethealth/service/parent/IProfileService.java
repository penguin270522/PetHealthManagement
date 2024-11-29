package com.example.pethealth.service.parent;

import com.example.pethealth.dto.outputDTO.BaseDTO;
import com.example.pethealth.dto.outputDTO.ProfileResponse;
import com.example.pethealth.dto.outputDTO.UpdateUserDTO;

public interface IProfileService {
    ProfileResponse getMyProfile();

    BaseDTO updateProfileUser(UpdateUserDTO userDTO);

}
