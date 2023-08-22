package com.ichtus.hotelmanagementsystem.services;

import com.ichtus.hotelmanagementsystem.exceptions.RoleNotFoundException;
import com.ichtus.hotelmanagementsystem.model.dictionaries.AccountRole;
import com.ichtus.hotelmanagementsystem.model.entities.Role;
import com.ichtus.hotelmanagementsystem.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role getUserRole() {
        return getRoleByName("ROLE_" + AccountRole.USER.name());
    }

    public Role getModeratorRole() {
        return getRoleByName("ROLE_" + AccountRole.MODERATOR.name());
    }

    public Role getAdminRole() {
        return getRoleByName("ROLE_" + AccountRole.ADMIN.name());
    }

    private Role getRoleByName(String name) {
        Optional<Role> role = roleRepository.findByName(name);
        if (role.isPresent()) {
            return role.get();
        } else {
            throw new RoleNotFoundException(name);
        }
    }
}
