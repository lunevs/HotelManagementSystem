package com.ichtus.hotelmanagementsystem.units.validationEntities;

import com.ichtus.hotelmanagementsystem.utils.anotations.WithMockAdmin;
import com.ichtus.hotelmanagementsystem.model.entities.Account;
import com.ichtus.hotelmanagementsystem.model.entities.Hotel;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureTestEntityManager
@Transactional
public class HotelTest {

    @Autowired
    private TestEntityManager entityManager;

    Account account = new Account()
            .setAccountName("test account")
            .setAccountPassword("123456")
            .setAccountEmail("ya@ya.com");
    Hotel hotel = new Hotel()
            .setHotelName("test hotel")
            .setHotelDescription("sd fsdf sd fsd fsd fs dfs dfs dfjhsd fsd fs dfsd fs d")
            .setHotelCity("London")
            .setHotelAdmin(account);


    @BeforeEach
    void setup() {
        entityManager.persist(account);
        entityManager.flush();
    }

    @Test
    @WithMockAdmin
    void whenHotel_isCorrect() {
        assertThatCode(() -> {
            entityManager.persist(hotel);
            entityManager.flush();
        }).doesNotThrowAnyException();
    }

    @Test
    @WithMockAdmin
    void whenHotelName_isIncorrect() {
        Hotel curHotel = hotel.setHotelName("");
        assertThrows(ConstraintViolationException.class, () -> {
            entityManager.persist(curHotel);
            entityManager.flush();
        });
    }

    @Test
    @WithMockAdmin
    void whenHotelCity_isIncorrect() {
        Hotel curHotel = hotel.setHotelCity("");
        assertThrows(ConstraintViolationException.class, () -> {
            entityManager.persist(curHotel);
            entityManager.flush();
        });
    }
}
