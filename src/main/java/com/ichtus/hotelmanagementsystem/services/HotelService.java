package com.ichtus.hotelmanagementsystem.services;

import com.ichtus.hotelmanagementsystem.model.entities.Account;
import com.ichtus.hotelmanagementsystem.model.entities.Hotel;
import com.ichtus.hotelmanagementsystem.model.dto.hotels.ResponseHotelData;
import com.ichtus.hotelmanagementsystem.model.dto.room.ResponseRoomData;
import com.ichtus.hotelmanagementsystem.model.dto.hotels.RequestHotelChange;
import com.ichtus.hotelmanagementsystem.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
@RequiredArgsConstructor
@Slf4j
public class HotelService {

    private final HotelRepository hotelRepository;
    private final AccountService accountService;

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

    public Set<String> getHotelsCities() {
        return hotelRepository.findAllByDeleted(false).stream()
                .map(Hotel::getHotelCity)
                .collect(Collectors.toSet());
    }

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

    public Hotel findHotelById(Long id) throws ObjectNotFoundException {
        return hotelRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id, Hotel.class.getName()));
    }

    public ResponseHotelData getHotelInfo(Long id) {
        return ResponseHotelData.of(findHotelById(id));
    }

    public ResponseHotelData updateHotelInfo(Long id, RequestHotelChange newHotelDto) {
        Hotel currentHotel = findHotelById(id)
                .setHotelName(newHotelDto.getHotelName())
                .setHotelDescription(newHotelDto.getHotelDescription())
                .setHotelCity(newHotelDto.getHotelCity());
        return ResponseHotelData.of(hotelRepository.save(currentHotel));
    }

    public boolean deleteHotel(Long id) {
        Hotel hotel = findHotelById(id).setDeleted(true);
        return hotelRepository.save(hotel).isDeleted();
    }

    public List<ResponseRoomData> getRoomsList(Long hotelId) {
        Hotel currentHotel = findHotelById(hotelId);
        return currentHotel.getRoomsList().stream()
                .filter(room -> !room.isDeleted())
                .map(ResponseRoomData::of)
                .toList();
    }

}
