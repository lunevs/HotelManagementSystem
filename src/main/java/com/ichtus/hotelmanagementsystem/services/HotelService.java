package com.ichtus.hotelmanagementsystem.services;

import com.ichtus.hotelmanagementsystem.exceptions.HotelNotFoundException;
import com.ichtus.hotelmanagementsystem.model.entities.Account;
import com.ichtus.hotelmanagementsystem.model.entities.Hotel;
import com.ichtus.hotelmanagementsystem.model.dto.hotels.ResponseHotelData;
import com.ichtus.hotelmanagementsystem.model.dto.room.ResponseRoomData;
import com.ichtus.hotelmanagementsystem.model.dto.hotels.RequestHotelChange;
import com.ichtus.hotelmanagementsystem.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class HotelService {

    private final HotelRepository hotelRepository;
    private final AccountService accountService;

    public List<ResponseHotelData> getHotelsList() {
        return hotelRepository.findAllByDeleted(false).stream()
                .map(ResponseHotelData::of)
                .toList();
    }

    public Set<String> getHotelsCities() {
        return hotelRepository.findAllByDeleted(false).stream()
                .map(Hotel::getHotelCity)
                .collect(Collectors.toSet());
    }

    public Hotel addHotel(RequestHotelChange hotelChange, String creatorName) {
        Account creator = accountService.findAccountByName(creatorName);
        Hotel newHotel = new Hotel()
                .setHotelName(hotelChange.getHotelName())
                .setHotelDescription(hotelChange.getHotelDescription())
                .setHotelCity(hotelChange.getHotelCity())
                .setRoomsList(Collections.emptyList())
                .setHotelAdmin(creator);
        return hotelRepository.save(newHotel);
    }

    public Hotel findHotelById(Long id) throws HotelNotFoundException {
        return hotelRepository.findById(id)
                .orElseThrow(() -> new HotelNotFoundException(id));
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

    public void deleteHotel(Long id) {
        Hotel hotel = findHotelById(id).setDeleted(true);
        hotelRepository.save(hotel);
    }

    public List<ResponseRoomData> getRoomsList(Long hotelId) {
        Hotel currentHotel = findHotelById(hotelId);
        return currentHotel.getRoomsList().stream()
                        .map(ResponseRoomData::of)
                        .toList();
    }

}
