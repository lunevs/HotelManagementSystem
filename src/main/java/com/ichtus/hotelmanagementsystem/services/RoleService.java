package com.ichtus.hotelmanagementsystem.services;

import com.ichtus.hotelmanagementsystem.exceptions.RoleNotFoundException;
import com.ichtus.hotelmanagementsystem.model.dictionaries.AccountRole;
import com.ichtus.hotelmanagementsystem.model.entities.Role;
import com.ichtus.hotelmanagementsystem.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role getUserRole() {
        return getRoleByName(AccountRole.USER.name());
    }

    public Role getRoleByName(String name) {
        return roleRepository.findByName("ROLE_" + name)
                .orElseThrow(() -> new RoleNotFoundException(name));
    }
}
