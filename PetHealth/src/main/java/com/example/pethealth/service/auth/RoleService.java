package com.example.pethealth.service.auth;

import com.example.pethealth.dto.outputDTO.BaseDTO;
import com.example.pethealth.exception.BadRequestException;
import com.example.pethealth.model.Role;
import com.example.pethealth.repositories.auth.RoleRepository;
import com.example.pethealth.service.parent.IRoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoleService implements IRoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role findByIdRole(long id) {
        return roleRepository.findById(id)
                .orElseThrow(()->new BadRequestException("dont find by id" + id));
    }

    @Override
    public Role findByCode(String code) {
        return roleRepository.findByCode(code)
                .orElseThrow(()-> new BadRequestException("dont find by String code" + code));
    }

    @Override
    public BaseDTO getAllRole() {
        List<Role> roleList = roleRepository.findAll();
        return BaseDTO.builder()
                .message("success")
                .result(true)
                .results(roleList)
                .build();
    }


}
