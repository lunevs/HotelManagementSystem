package com.ichtus.hotelmanagementsystem.validationEntities;

import com.ichtus.hotelmanagementsystem.model.entities.Account;
import com.ichtus.hotelmanagementsystem.services.RoleService;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@ContextConfiguration
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AccountTest {


    @MockBean
    private RoleService roleService;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void whenAccountNameIsInvalid_thenThrowsException() {
        Account curAccount = new Account()
                .setAccountName(null)
                .setAccountPassword("123456")
                .setAccountEmail("ya@ya.ru")
                .setRole(roleService.getUserRole());

        assertThrows(ConstraintViolationException.class, () -> {
            entityManager.persist(curAccount);
            entityManager.flush();
        });
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void whenAccountPasswordIsInvalid_thenThrowsException() {
        Account curAccount = new Account()
                .setAccountName("test_user")
                .setAccountPassword("")
                .setAccountEmail("ya@ya.ru")
                .setRole(roleService.getUserRole());

        assertThrows(ConstraintViolationException.class, () -> {
            entityManager.persist(curAccount);
            entityManager.flush();
        });
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void whenAccountEmailIsInvalid_thenThrowsException() {
        Account curAccount = new Account()
                .setAccountName("test_user")
                .setAccountPassword("123456")
                .setAccountEmail("gmail.com")
                .setRole(roleService.getUserRole());

        assertThrows(ConstraintViolationException.class, () -> {
            entityManager.persist(curAccount);
            entityManager.flush();
        });
    }

}
