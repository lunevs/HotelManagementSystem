package com.ichtus.hotelmanagementsystem.units.services;

import com.ichtus.hotelmanagementsystem.model.entities.Role;
import com.ichtus.hotelmanagementsystem.model.entities.Room;
import com.ichtus.hotelmanagementsystem.repository.RoleRepository;
import com.ichtus.hotelmanagementsystem.services.RoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RoleServiceTest {

    @Autowired
    private RoleService roleService;

    @MockBean
    private RoleRepository roleRepository;

    Role baseRole = new Role()
            .setId(0)
            .setName("ROLE_USER");


    @Test
    void whenGetAllRoles() {

        given(roleRepository.findAll())
                .willReturn(
                        Collections.singletonList(baseRole)
                );

        List<Role> roleList = roleService.getAllRoles();
        assertThat(roleList.size()).isEqualTo(1);
        assertThat(roleList.get(0).getName()).isEqualTo(baseRole.getName());
    }

    @Test
    void whenGetRoleByName() {
        given(roleRepository.findByName(any())).willReturn(Optional.of(baseRole));
        assertThat(roleService.getRoleByName("USER").getName()).isEqualTo(baseRole.getName());
    }
}
