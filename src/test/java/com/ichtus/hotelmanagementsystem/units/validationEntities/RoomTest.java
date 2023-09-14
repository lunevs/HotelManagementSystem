package com.ichtus.hotelmanagementsystem.units.validationEntities;

import com.ichtus.hotelmanagementsystem.utils.anotations.WithMockAdmin;
import com.ichtus.hotelmanagementsystem.model.entities.Account;
import com.ichtus.hotelmanagementsystem.model.entities.Hotel;
import com.ichtus.hotelmanagementsystem.model.entities.Room;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@AutoConfigureTestEntityManager
@Transactional
public class RoomTest {

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
    Room room = new Room()
            .setRoomName("test room")
            .setRoomPrice(BigDecimal.valueOf(100))
            .setHotel(hotel);


    @BeforeEach
    void setup() {
        entityManager.persist(account);
        entityManager.persist(hotel);
        entityManager.flush();
    }

    @Test
    @WithMockAdmin
    void whenRoom_isCorrect() {
        assertThatCode(() -> {
            entityManager.persist(room);
            entityManager.flush();
        }).doesNotThrowAnyException();
    }

    @Test
    @WithMockAdmin
    void whenRoomName_isIncorrect() {
        Room curRoom = room.setRoomName("");
        assertThrows(ConstraintViolationException.class, () -> {
           entityManager.persist(curRoom);
           entityManager.flush();
        });
    }

    @Test
    @WithMockAdmin
    void whenRoomPrice_isIncorrect() {
        Room curRoom = room.setRoomPrice(BigDecimal.valueOf(-5));
        assertThrows(ConstraintViolationException.class, () -> {
            entityManager.persist(curRoom);
            entityManager.flush();
        });
    }
}
