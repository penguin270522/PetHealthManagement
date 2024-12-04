package com.example.pethealth.dto.outputDTO;

import com.example.pethealth.enums.GenderUser;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateUserDTO {
    private String fullName;
    private String numberPhone;
    private String cmnd;
    private GenderUser genderUser;
    private String address;
}
