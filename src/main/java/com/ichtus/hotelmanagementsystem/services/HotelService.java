package com.ichtus.hotelmanagementsystem.services;

import com.ichtus.hotelmanagementsystem.exceptions.AccountNotFoundException;
import com.ichtus.hotelmanagementsystem.exceptions.HotelNotFoundException;
import com.ichtus.hotelmanagementsystem.model.entities.Account;
import com.ichtus.hotelmanagementsystem.model.entities.Hotel;
import com.ichtus.hotelmanagementsystem.model.dto.hotels.ResponseHotelChange;
import com.ichtus.hotelmanagementsystem.model.dto.room.ResponseRoomData;
import com.ichtus.hotelmanagementsystem.model.dto.hotels.RequestHotelChange;
import com.ichtus.hotelmanagementsystem.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class HotelService {

    private final HotelRepository hotelRepository;
    private final AccountService accountService;

    public Iterable<ResponseHotelChange> getLocationsList() {
        return hotelRepository.findAll().stream()
                .map(ResponseHotelChange::of)
                .collect(Collectors.toList());
    }

    public Hotel addLocation(RequestHotelChange hotelChange, String creatorName) {
        Account creator = accountService.findAccountByName(creatorName);
        Hotel newHotel = new Hotel()
                .setHotelName(hotelChange.getHotelName())
                .setHotelDescription(hotelChange.getHotelDescription())
                .setRoomsList(Collections.emptyList())
                .setHotelAdmin(creator);
        return hotelRepository.save(newHotel);
    }

    public Hotel findHotelById(Long id) throws HotelNotFoundException {
        return hotelRepository
                .findById(id)
                .orElseThrow(() -> new HotelNotFoundException(id));
    }

    public ResponseHotelChange getHotelInfo(Long id) {
        Hotel currentHotel = findHotelById(id);
        return ResponseHotelChange.of(currentHotel);
    }

    public ResponseHotelChange updateLocationInfo(Long id, RequestHotelChange newLocationDto) {
        Hotel currentHotel = findHotelById(id);
        currentHotel
                .setHotelName(
                        newLocationDto.getHotelName() == null
                                ? currentHotel.getHotelName()
                                : newLocationDto.getHotelName())
                .setHotelDescription(
                        newLocationDto.getHotelDescription() == null
                                ? currentHotel.getHotelDescription()
                                : newLocationDto.getHotelDescription());
        Hotel updatedHotel = hotelRepository.save(currentHotel);
        return ResponseHotelChange.of(updatedHotel);
    }

    public void deleteLocation(Long id) {
        hotelRepository.deleteById(id);
    }

    public List<ResponseRoomData> getRoomsList(Long locationId) {
        Hotel currentHotel = findHotelById(locationId);
        return currentHotel.getRoomsList().stream()
                        .map(ResponseRoomData::of)
                        .toList();
    }
}
