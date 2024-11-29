package com.example.pethealth.dto.outputDTO;

import com.example.pethealth.dto.authDTO.UserDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private BaseDTO baseDTO;
    private List<UserDTO> userList;
}
