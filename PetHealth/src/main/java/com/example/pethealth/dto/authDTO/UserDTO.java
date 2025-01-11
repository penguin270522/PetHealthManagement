package com.example.pethealth.dto.authDTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private long id;
    private String username;
    private String fullName;
    private String role;
    private String address;
    private String gmail;
}
