package com.ichtus.hotelmanagementsystem.services;

import com.ichtus.hotelmanagementsystem.model.dictionaries.AccountRole;
import com.ichtus.hotelmanagementsystem.model.entities.Role;
import com.ichtus.hotelmanagementsystem.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Optional<Role> role = roleRepository.findByName("ROLE_" + name);
        if (role.isPresent()) {
            return role.get();
        } else {
            throw  new ObjectNotFoundException((Object) name, Role.class.getName());
        }
    }
}
