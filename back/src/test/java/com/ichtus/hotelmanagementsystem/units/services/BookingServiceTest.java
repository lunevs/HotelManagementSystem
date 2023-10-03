package com.ichtus.hotelmanagementsystem.units.services;

import com.ichtus.hotelmanagementsystem.model.dictionaries.BookingStatus;
import com.ichtus.hotelmanagementsystem.model.dto.booking.RequestNewBooking;
import com.ichtus.hotelmanagementsystem.model.dto.booking.ResponseBooking;
import com.ichtus.hotelmanagementsystem.model.entities.Account;
import com.ichtus.hotelmanagementsystem.model.entities.Booking;
import com.ichtus.hotelmanagementsystem.model.entities.Hotel;
import com.ichtus.hotelmanagementsystem.model.entities.Room;
import com.ichtus.hotelmanagementsystem.repository.BookingRepository;
import com.ichtus.hotelmanagementsystem.services.AccountService;
import com.ichtus.hotelmanagementsystem.services.BookingService;
import com.ichtus.hotelmanagementsystem.services.RoomService;
import com.ichtus.hotelmanagementsystem.utils.anotations.WithMockAdmin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class BookingServiceTest {

    @Autowired
    private BookingService bookingService;

    @MockBean
    private BookingRepository bookingRepository;

    @MockBean
    private RoomService roomService;

    @MockBean
    private AccountService accountService;

    private final static Long MILLS_IN_5_DAYS = (long) (24*60*60*5);

    Account baseAccount = new Account()
            .setId(0)
            .setAccountName("test account")
            .setAccountPassword("123456")
            .setAccountEmail("ya@ya.com");
    Hotel baseHotel = new Hotel()
            .setId(0)
            .setHotelName("test hotel")
            .setHotelDescription("sd fsdf sd fsd fsd fs dfs dfs dfjhsd fsd fs dfsd fs d")
            .setHotelCity("London")
            .setHotelAdmin(baseAccount);
    Room baseRoom = new Room()
            .setId(0)
            .setRoomName("test room")
            .setRoomPrice(BigDecimal.valueOf(100))
            .setRoomCapacity(5)
            .setHotel(baseHotel);
    Booking baseBooking = new Booking()
            .setId(0)
            .setStartDate(Date.from(Instant.now().plusMillis(MILLS_IN_5_DAYS)))
            .setEndDate(Date.from(Instant.now().plusMillis(MILLS_IN_5_DAYS*2)))
            .setBookingStatus(BookingStatus.ACTIVE)
            .setRoom(baseRoom)
            .setAccount(baseAccount);


    @Test
    @WithMockAdmin
    void whenGetMyBookings() {
        given(bookingRepository.findAllByDeleted(false)).willReturn(Collections.singletonList(baseBooking));

        List<ResponseBooking> responseBookings = bookingService.getMyBookings();

        assertThat(responseBookings).isNotNull();
        assertThat(responseBookings).hasSize(1);
        assertThat(responseBookings.get(0).getAccountId()).isEqualTo(baseAccount.getId());
        assertThat(responseBookings.get(0).getBookedRoom().getId()).isEqualTo(baseRoom.getId());
    }

    @Test
    void whenDoBooking() {
        given(bookingRepository.checkBookingsForRoom(any(), any(), any())).willReturn(Collections.emptyList());
        given(roomService.findById(any())).willReturn(baseRoom);
        given(accountService.findAccountByName(any())).willReturn(baseAccount);
        given(bookingRepository.save(any())).willReturn(baseBooking);

        ResponseBooking myBooking = bookingService.doBooking(
                new RequestNewBooking()
                        .setRoomId(baseRoom.getId())
                        .setHotelId(baseHotel.getId())
                        .setStartDate(baseBooking.getStartDate())
                        .setEndDate(baseBooking.getEndDate())
                        .setNumberOfGuests(baseBooking.getNumberOfGuests()),
                baseAccount.getAccountName()
        );

        assertThat(myBooking).isNotNull();
        assertThat(myBooking.getAccountName()).isEqualTo(baseAccount.getAccountName());
        assertThat(myBooking.getBookedRoom().getRoomName()).isEqualTo(baseRoom.getRoomName());

    }
}
