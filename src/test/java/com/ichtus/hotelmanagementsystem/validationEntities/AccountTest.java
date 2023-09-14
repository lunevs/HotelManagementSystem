package com.ichtus.hotelmanagementsystem.validationEntities;

import com.ichtus.hotelmanagementsystem.model.anotations.WithMockAdmin;
import com.ichtus.hotelmanagementsystem.model.entities.Account;
import com.ichtus.hotelmanagementsystem.model.entities.Role;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@ContextConfiguration
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AccountTest {


    @Autowired
    private TestEntityManager entityManager;

    Account baseAccount = new Account()
            .setAccountName("test_account")
            .setAccountPassword("123456")
            .setAccountEmail("ya@ya.ru")
            .setRole(new Role().setId(1).setName("ROLE_ADMIN"));

    @Test
    @WithMockAdmin
    void whenAccountNameIsInvalid_thenThrowsException() {
        Account curAccount = baseAccount.setAccountName(null);

        assertThrows(ConstraintViolationException.class, () -> {
            entityManager.persist(curAccount);
            entityManager.flush();
        });
    }

    @Test
    @WithMockAdmin
    void whenAccountPasswordIsInvalid_thenThrowsException() {
        Account curAccount = baseAccount.setAccountPassword("");

        assertThrows(ConstraintViolationException.class, () -> {
            entityManager.persist(curAccount);
            entityManager.flush();
        });
    }

    @Test
    @WithMockAdmin
    void whenAccountEmailIsInvalid_thenThrowsException() {
        Account curAccount = baseAccount.setAccountEmail("gmail.com");

        assertThrows(ConstraintViolationException.class, () -> {
            entityManager.persist(curAccount);
            entityManager.flush();
        });
    }

    @Test
    @WithMockAdmin
    void whenAccountCorrect_thenOk() {
        assertThatCode(() -> {
            entityManager.persist(baseAccount);
            entityManager.flush();
        }).doesNotThrowAnyException();
    }

}
