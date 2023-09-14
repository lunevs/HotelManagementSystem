package com.ichtus.hotelmanagementsystem.units.services;

import com.ichtus.hotelmanagementsystem.model.dictionaries.BookingStatus;
import com.ichtus.hotelmanagementsystem.model.dto.search.RequestHotelsSearch;
import com.ichtus.hotelmanagementsystem.model.entities.Account;
import com.ichtus.hotelmanagementsystem.model.entities.Booking;
import com.ichtus.hotelmanagementsystem.model.entities.Hotel;
import com.ichtus.hotelmanagementsystem.model.entities.Room;
import com.ichtus.hotelmanagementsystem.repository.BookingRepository;
import com.ichtus.hotelmanagementsystem.repository.HotelRepository;
import com.ichtus.hotelmanagementsystem.repository.RoomRepository;
import com.ichtus.hotelmanagementsystem.services.SearchService;
import org.junit.jupiter.api.BeforeEach;
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
public class SearchServiceTest {

    @Autowired
    private SearchService searchService;

    @MockBean
    private HotelRepository hotelRepository;

    @MockBean
    private RoomRepository roomRepository;

    @MockBean
    private BookingRepository bookingRepository;

    private final static Long MILLS_IN_5_DAYS = (long) (24*60*60*5);

    Account account = new Account()
            .setAccountName("test account")
            .setAccountPassword("123456")
            .setAccountEmail("ya@ya.com");
    Hotel hotel = new Hotel()
            .setHotelName("test hotel")
            .setHotelDescription("sd fsdf sd fsd fsd fs dfs dfs dfjhsd fsd fs dfsd fs d")
            .setHotelCity("London")
            .setAmenities(Collections.emptyList())
            .setHotelAdmin(account);
    Room room = new Room()
            .setRoomName("test room")
            .setRoomPrice(BigDecimal.valueOf(100))
            .setHotel(hotel);
    Booking booking = new Booking()
            .setStartDate(Date.from(Instant.now().plusMillis(MILLS_IN_5_DAYS)))
            .setEndDate(Date.from(Instant.now().plusMillis(MILLS_IN_5_DAYS*2)))
            .setBookingStatus(BookingStatus.ACTIVE)
            .setRoom(room)
            .setAccount(account);

    @BeforeEach
    void setup() {
        hotel.setRoomsList(List.of(room));
        given(roomRepository.findAllByRoomCapacityGreaterThanEqualAndRoomPriceBetween(anyInt(), any(), any()))
                .willReturn(Collections.singletonList(room));
        given(roomRepository.findAllByRoomCapacityGreaterThanEqualAndRoomPriceGreaterThanEqual(anyInt(), any()))
                .willReturn(Collections.singletonList(room));
    }

    @Test
    void findAllRoomsByCapacityAndPriceTest() {

        given(bookingRepository.findAllByParameters(any(), any())).willReturn(Collections.emptyList());
        given(hotelRepository.findAllByHotelCity(any())).willReturn(Collections.singletonList(hotel));
        given(hotelRepository.findAll()).willReturn(Collections.singletonList(hotel));

        RequestHotelsSearch hotelsSearch = new RequestHotelsSearch()
                .setStartDate(Date.from(Instant.now().plusMillis(MILLS_IN_5_DAYS)))
                .setEndDate(Date.from(Instant.now().plusMillis(MILLS_IN_5_DAYS*2)))
                .setSearchCity("London")
                .setMinPrice(10);

        assertThat(searchService.findAllHotelsByParameters(hotelsSearch)).hasSize(1);
    }

    @Test
    void findAllRoomsByCapacityAndPriceTest_NoFreeRooms() {

        given(bookingRepository.findAllByParameters(any(), any())).willReturn(Collections.singletonList(booking));
        given(hotelRepository.findAllByHotelCity(any())).willReturn(Collections.singletonList(hotel));
        given(hotelRepository.findAll()).willReturn(Collections.singletonList(hotel));

        RequestHotelsSearch hotelsSearch = new RequestHotelsSearch()
                .setStartDate(Date.from(Instant.now().plusMillis(MILLS_IN_5_DAYS)))
                .setEndDate(Date.from(Instant.now().plusMillis(MILLS_IN_5_DAYS*3)))
                .setSearchCity("London")
                .setMinPrice(10);

        assertThat(searchService.findAllHotelsByParameters(hotelsSearch)).hasSize(0);
    }

    @Test
    void findAllRoomsByCapacityAndPriceTest_NoCity() {

        given(bookingRepository.findAllByParameters(any(), any())).willReturn(Collections.emptyList());
        given(hotelRepository.findAllByHotelCity(any())).willReturn(Collections.emptyList());
        given(hotelRepository.findAll()).willReturn(Collections.emptyList());

        RequestHotelsSearch hotelsSearch = new RequestHotelsSearch()
                .setStartDate(Date.from(Instant.now().plusMillis(MILLS_IN_5_DAYS)))
                .setEndDate(Date.from(Instant.now().plusMillis(MILLS_IN_5_DAYS*2)))
                .setSearchCity("Paris")
                .setMinPrice(10);

        assertThat(searchService.findAllHotelsByParameters(hotelsSearch)).hasSize(0);
    }

}
