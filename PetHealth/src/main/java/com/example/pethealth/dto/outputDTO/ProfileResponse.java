package com.example.pethealth.dto.outputDTO;

import com.example.pethealth.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileResponse {
    private User user;
}
