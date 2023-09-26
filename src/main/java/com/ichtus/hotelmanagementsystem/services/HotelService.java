package com.ichtus.hotelmanagementsystem.services;

import com.ichtus.hotelmanagementsystem.model.entities.*;
import com.ichtus.hotelmanagementsystem.model.dto.hotels.*;
import com.ichtus.hotelmanagementsystem.model.dto.room.*;
import com.ichtus.hotelmanagementsystem.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.*;

/**
 * Defines services to7 interact with hotels
 * @author smlunev
 */
@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepository hotelRepository;
    private final AccountService accountService;

    /**
     * Get all non deleted hotels list
     * @return list of ResponseHotelData with hotels information
     */
    public List<ResponseHotelData> getHotelsList() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String accountName = auth.getName();
        Stream<Hotel> hotelStream = hotelRepository.findAllByDeleted(false).stream();
        Stream<? extends GrantedAuthority> authStream = auth.getAuthorities().stream();

        if (authStream.anyMatch(el -> !el.getAuthority().contains("ADMIN"))) {
            hotelStream = hotelStream
                    .filter(
                            el -> el.getHotelAdmin().getAccountName().equals(accountName)
                    );
        }
        return hotelStream.map(ResponseHotelData::of).toList();
    }

    /**
     * Get list of hotel's cities
     * @return set with cities names
     */
    public Set<String> getHotelsCities() {
        return hotelRepository.findAllByDeleted(false).stream()
                .map(Hotel::getHotelCity)
                .collect(Collectors.toSet());
    }

    /**
     * Create new hotel
     * @param hotelChange dto with new hotel parameters
     * @param creatorName account name who will be hotel administrator
     * @return dto ResponseHotelData with created hotel data
     */
    public ResponseHotelData addHotel(RequestHotelChange hotelChange, String creatorName) {
        Account creator = accountService.findAccountByName(creatorName);
        Hotel newHotel = new Hotel()
                .setHotelName(hotelChange.getHotelName())
                .setHotelDescription(hotelChange.getHotelDescription())
                .setHotelCity(hotelChange.getHotelCity())
                .setRoomsList(Collections.emptyList())
                .setHotelAdmin(creator);
        return ResponseHotelData.of(hotelRepository.save(newHotel));
    }

    /**
     * find hotel information by hotel id
     * @param id searched hotel id
     * @return hotel data
     * @throws ObjectNotFoundException if not found hotel with such id
     */
    public Hotel findHotelById(Long id) throws ObjectNotFoundException {
        return hotelRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id, Hotel.class.getName()));
    }

    /**
     * find hotel information by hotel id
     * @param id searched hotel id
     * @return dto ResponseHotelData with hotel data
     */
    public ResponseHotelData getHotelInfo(Long id) {
        return ResponseHotelData.of(findHotelById(id));
    }

    /**
     * Update common hotel information
     * @param id updated hotel id
     * @param newHotelDto dto with new hotel information
     * @return dto ResponseHotelData with updated hotel
     */
    public ResponseHotelData updateHotelInfo(Long id, RequestHotelChange newHotelDto) {
        Hotel currentHotel = findHotelById(id)
                .setHotelName(newHotelDto.getHotelName())
                .setHotelDescription(newHotelDto.getHotelDescription())
                .setHotelCity(newHotelDto.getHotelCity());
        return ResponseHotelData.of(hotelRepository.save(currentHotel));
    }

    /**
     * Mark hotel as deleted
     * @param id deleted hotel id
     * @return true if hotel deleted or false if not
     */
    public boolean deleteHotel(Long id) {
        Hotel hotel = findHotelById(id).setDeleted(true);
        return hotelRepository.save(hotel).isDeleted();
    }

    /**
     * Get all rooms in current hotel
     * @param hotelId searched hotel id
     * @return list of ResponseRoomData with rooms data
     */
    public List<ResponseRoomData> getRoomsList(Long hotelId) {
        Hotel currentHotel = findHotelById(hotelId);
        return currentHotel.getRoomsList().stream()
                .filter(room -> !room.isDeleted())
                .map(ResponseRoomData::of)
                .toList();
    }

}
