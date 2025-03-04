package com.example.pethealth.service.parent;

import com.example.pethealth.dto.outputDTO.BaseDTO;
import com.example.pethealth.model.Role;

public interface IRoleService {
    Role findByIdRole(long id);
    Role findByCode(String code);

    BaseDTO getAllRole();
}
