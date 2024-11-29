package com.example.pethealth.dto.authDTO.JWT;

import com.example.pethealth.dto.outputDTO.BaseDTO;

import com.example.pethealth.model.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtLoginDTO {
    private BaseDTO baseDTO;
    private String token;
    private String url;
    private String role;
    private User user;
}
