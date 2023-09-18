package com.ichtus.hotelmanagementsystem.units.validationEntities;

import com.ichtus.hotelmanagementsystem.utils.anotations.WithMockAdmin;
import com.ichtus.hotelmanagementsystem.model.dictionaries.BookingStatus;
import com.ichtus.hotelmanagementsystem.model.entities.Account;
import com.ichtus.hotelmanagementsystem.model.entities.Booking;
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
import java.time.Instant;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureTestEntityManager
@Transactional
public class BookingTest {

    @Autowired
    private TestEntityManager entityManager;

    private final static Long MILLS_IN_5_DAYS = (long) (24*60*60*5);

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
    Booking baseBooking = new Booking()
            .setStartDate(Date.from(Instant.now().plusMillis(MILLS_IN_5_DAYS)))
            .setEndDate(Date.from(Instant.now().plusMillis(MILLS_IN_5_DAYS*2)))
            .setBookingStatus(BookingStatus.ACTIVE)
            .setRoom(room)
            .setAccount(account);

    @BeforeEach
    void saveRoomAndAccount() {
        entityManager.persist(account);
        entityManager.persist(hotel);
        entityManager.persist(room);
        entityManager.flush();
    }

    @Test
    @WithMockAdmin
    void whenBooking_isCorrect() {
        System.out.println(baseBooking);
        assertThatCode(() -> {
            entityManager.persist(baseBooking);
            entityManager.flush();
        }).doesNotThrowAnyException();
    }

    @Test
    @WithMockAdmin
    void whenBookingStartDate_isIncorrect() {
        Booking curBooking = baseBooking.setStartDate(Date.from(Instant.now().minusMillis(MILLS_IN_5_DAYS)));
        assertThrows(ConstraintViolationException.class, () -> {
            entityManager.persist(curBooking);
            entityManager.flush();
        });
    }

    @Test
    @WithMockAdmin
    void whenBookingEndDate_isIncorrect() {
        Booking curBooking = baseBooking.setEndDate(Date.from(Instant.now().minusMillis(MILLS_IN_5_DAYS)));
        assertThrows(ConstraintViolationException.class, () -> {
            entityManager.persist(curBooking);
            entityManager.flush();
        });
    }

    @Test
    @WithMockAdmin
    void whenBookingNumberOfGuests_isIncorrect() {
        Booking curBooking = baseBooking.setNumberOfGuests(1000);
        assertThrows(ConstraintViolationException.class, () -> {
            entityManager.persist(curBooking);
            entityManager.flush();
        });
    }


}
