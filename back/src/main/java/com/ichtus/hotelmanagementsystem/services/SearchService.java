package com.ichtus.hotelmanagementsystem.services;

import com.ichtus.hotelmanagementsystem.model.dto.room.ResponseRoomData;
import com.ichtus.hotelmanagementsystem.model.dto.search.RequestHotelsSearch;
import com.ichtus.hotelmanagementsystem.model.dto.hotels.ResponseHotelData;
import com.ichtus.hotelmanagementsystem.model.dto.search.ResponseSearchResults;
import com.ichtus.hotelmanagementsystem.model.entities.Booking;
import com.ichtus.hotelmanagementsystem.model.entities.Hotel;
import com.ichtus.hotelmanagementsystem.model.entities.Room;
import com.ichtus.hotelmanagementsystem.repository.BookingRepository;
import com.ichtus.hotelmanagementsystem.repository.HotelRepository;
import com.ichtus.hotelmanagementsystem.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Defines services to interact with hotels and rooms search
 * @author smlunev
 */
@Service
@RequiredArgsConstructor
public class SearchService {

    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;

    /**
     * find free Rooms with capacity more or equal getNeededCapacity
     * and with room price between minPrice and maxPrice
     * @param roomCapacity max required room capacity
     * @param minPrice min required price
     * @param maxPrice max required price
     * @return list of rooms found
     */
    private List<Room> findAllRoomsByCapacityAndPrice(int roomCapacity, BigDecimal minPrice, BigDecimal maxPrice) {
        if (maxPrice == null) {
            return roomRepository.findAllByRoomCapacityGreaterThanEqualAndRoomPriceGreaterThanEqualAndDeleted(roomCapacity, minPrice, false);
        }
        return roomRepository.findAllByRoomCapacityGreaterThanEqualAndRoomPriceBetweenAndDeleted(roomCapacity, minPrice, maxPrice, false);
    }

    /**
     * find all non deleted hotels in a given city
     * @param cityName required city name
     * @return list of hotels found
     */
    private List<Hotel> findAllHotelsByCity(String cityName) {
        if (cityName.length() > 0) {
            return hotelRepository.findAllByHotelCityAndDeleted(cityName, false);
        }
        return hotelRepository.findAllByDeleted(false);
    }

    /**
     * find all bookings in a given time period
     * @param start period start date
     * @param end period end date
     * @return list of bookings found
     */
    private List<Booking> findAllBookingsForPeriod(Date start, Date end) {
        if (start != null && end != null) {
            return bookingRepository.findAllByParameters(start, end);
        }
        return Collections.emptyList();
    }

    /**
     * internal method to construct object with search results
     * @param hotels hotels list
     * @param freeRooms free rooms list
     * @return list of ResponseSearchResults
     */
    private List<ResponseSearchResults> constructSearchResults(List<Hotel> hotels, List<Room> freeRooms) {
        List<ResponseSearchResults> results = new ArrayList<>();
        hotels.forEach(hotel -> {
            List<Room> curHotelFreeRooms = freeRooms.stream()
                    .filter(room -> room.getHotel().getId() == hotel.getId()).toList();
            if (curHotelFreeRooms.size() > 0) {
                ResponseSearchResults curHotel = ResponseSearchResults.of(hotel);
                curHotel.setFreeRoomsList(curHotelFreeRooms.stream().map(ResponseRoomData::of).toList());
                results.add(curHotel);
            }
        });
        return results;
    }

    /**
     * search hotels and rooms by given parameters.
     * @param searchParameters hotels and rooms filter
     * @return list of ResponseSearchResults with free rooms
     */
    public List<ResponseSearchResults> findAllHotelsByParameters(RequestHotelsSearch searchParameters) {

        List<Hotel> hotels = findAllHotelsByCity(searchParameters.getSearchCity());
        Set<Long> hotelIds = hotels.stream().map(Hotel::getId).collect(Collectors.toSet());
        List<Room> freeRooms = findAllRoomsByCapacityAndPrice(
                searchParameters.getNeededCapacity(),
                BigDecimal.valueOf(searchParameters.getMinPrice()),
                (searchParameters.getMaxPrice() < 0.1) ? null : BigDecimal.valueOf(searchParameters.getMaxPrice())
        );
        List<Booking> bookings = findAllBookingsForPeriod(
                searchParameters.getStartDate(),
                searchParameters.getEndDate()
        );

        Set<Long> bookedRoomsIds = bookings.stream().map(booking -> booking.getRoom().getId()).collect(Collectors.toSet());
        freeRooms = freeRooms.stream()
                .filter(room -> hotelIds.contains(room.getHotel().getId()))
                .filter(room -> !bookedRoomsIds.contains(room.getId()))
                .toList();

        return constructSearchResults(hotels, freeRooms);
    }
}
