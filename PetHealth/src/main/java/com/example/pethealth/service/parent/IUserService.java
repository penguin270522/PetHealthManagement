package com.example.pethealth.service.parent;

import com.example.pethealth.dto.outputDTO.BaseDTO;
import com.example.pethealth.dto.outputDTO.UserResponse;
import com.example.pethealth.dto.authDTO.JWT.JwtLoginDTO;
import com.example.pethealth.dto.authDTO.LoginDTO;
import com.example.pethealth.dto.authDTO.RegisterDTO;
import com.example.pethealth.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public interface IUserService {

    BaseDTO createUser(RegisterDTO request);
    User findByIdUser(long id);

    UserDetails loadUserByUsername(String username);

    JwtLoginDTO loginUser(LoginDTO request);

    UserResponse findByUserRole(String role);

    BaseDTO getAllUser();

    BaseDTO findUserName(String fullName);
    BaseDTO findUserWithCode(String code);

    BaseDTO registerUser(RegisterDTO request);

    BaseDTO updateUser(RegisterDTO registerDTO, Long userId);

    BaseDTO findByUserWithId(Long userId);
}
