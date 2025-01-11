package com.example.pethealth.dto.authDTO;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterDTO {
    private String userName;
    private String password;
    private String phoneNumber;
    private String address;
    private String email;
    private String fullName;
    private String role;

}
