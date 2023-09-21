package com.ichtus.hotelmanagementsystem.services;

import com.ichtus.hotelmanagementsystem.model.dictionaries.AccountRole;
import com.ichtus.hotelmanagementsystem.model.entities.Role;
import com.ichtus.hotelmanagementsystem.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Defines services to interact with account role
 * @author smlunev
 */
@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    /**
     * Get roles dictionary
     * @return list of Roles
     */
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    /**
     * Return default role with USER permissions
     * @return role object
     */
    public Role getUserRole() {
        return getRoleByName(AccountRole.USER.name());
    }

    /**
     * Find role by name
     * @param name string with role name
     * @return role object
     */
    public Role getRoleByName(String name) {
        Optional<Role> role = roleRepository.findByName("ROLE_" + name);
        if (role.isPresent()) {
            return role.get();
        } else {
            throw  new ObjectNotFoundException((Object) name, Role.class.getName());
        }
    }
}
