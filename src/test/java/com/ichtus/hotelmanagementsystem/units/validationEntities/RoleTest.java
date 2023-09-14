package com.ichtus.hotelmanagementsystem.units.validationEntities;

import com.ichtus.hotelmanagementsystem.utils.anotations.WithMockAdmin;
import com.ichtus.hotelmanagementsystem.model.entities.Role;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@AutoConfigureTestEntityManager
@Transactional
public class RoleTest {

    @Autowired
    private TestEntityManager entityManager;

    private final Role role = new Role()
            .setName("ROLE_TEST");

    @Test
    @WithMockAdmin
    void whenRole_isCorrect() {
        assertThatCode(() -> {
            entityManager.persist(role);
            entityManager.flush();
        }).doesNotThrowAnyException();
    }

    @Test
    @WithMockAdmin
    void whenRoleName_isIncorrect() {
        Role curRole = role.setName("");
        assertThrows(ConstraintViolationException.class, () -> {
            entityManager.persist(curRole);
            entityManager.flush();
        });
    }
}
