package com.ichtus.hotelmanagementsystem.services;

import com.ichtus.hotelmanagementsystem.exceptions.AccountNotFoundException;
import com.ichtus.hotelmanagementsystem.exceptions.LocationNotFoundException;
import com.ichtus.hotelmanagementsystem.model.entities.Account;
import com.ichtus.hotelmanagementsystem.model.entities.Location;
import com.ichtus.hotelmanagementsystem.model.entities.Room;
import com.ichtus.hotelmanagementsystem.model.dto.location.CreateLocationRequestDto;
import com.ichtus.hotelmanagementsystem.model.dto.location.GetLocationInfoResponseDto;
import com.ichtus.hotelmanagementsystem.model.dto.location.GetLocationsResponseDto;
import com.ichtus.hotelmanagementsystem.model.dto.room.CreateRoomRequestDto;
import com.ichtus.hotelmanagementsystem.model.dto.room.GetRoomsResponseDto;
import com.ichtus.hotelmanagementsystem.model.dto.location.UpdateLocationRequestDto;
import com.ichtus.hotelmanagementsystem.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class LocationService {

    private final LocationRepository locationRepository;
    private final AccountService accountService;

    public Iterable<GetLocationsResponseDto> getLocationsList() {
        return locationRepository.findAll().stream()
                .map(GetLocationsResponseDto::of)
                .collect(Collectors.toList());
    }

    public Location addLocation(CreateLocationRequestDto locationDto, String creatorName) {
        Account creator = accountService.findByName(creatorName).orElseThrow(() -> new AccountNotFoundException(creatorName));
        Location newLocation = new Location()
                .setLocationName(locationDto.getLocationName())
                .setLocationDescription(locationDto.getLocationDescription())
                .setRoomsList(Collections.emptyList())
                .setLocationAdmin(creator);
        return locationRepository.save(newLocation);
    }

    public Location findLocationById(Long id) throws LocationNotFoundException {
        return locationRepository
                .findById(id)
                .orElseThrow(() -> new LocationNotFoundException(id));
    }

    public GetLocationInfoResponseDto getLocationInfo(Long id) {
        Location currentLocation = findLocationById(id);
        return GetLocationInfoResponseDto.of(currentLocation);
    }

    public GetLocationsResponseDto updateLocationInfo(Long id, UpdateLocationRequestDto newLocationDto) {
        Location currentLocation = findLocationById(id);
        currentLocation
                .setLocationName(
                        newLocationDto.getLocationName() == null
                                ? currentLocation.getLocationName()
                                : newLocationDto.getLocationName())
                .setLocationDescription(
                        newLocationDto.getLocationDescription() == null
                                ? currentLocation.getLocationDescription()
                                : newLocationDto.getLocationDescription());
        Location updatedLocation = locationRepository.save(currentLocation);
        return GetLocationsResponseDto.of(updatedLocation);
    }

    public void deleteLocation(Long id) {
        locationRepository.deleteById(id);
    }

    public List<GetRoomsResponseDto> getRoomsList(Long locationId) {
        Location currentLocation = findLocationById(locationId);
        return currentLocation.getRoomsList().stream()
                        .map(GetRoomsResponseDto::of)
                        .toList();
    }

//    public GetLocationsResponseDto addRoomToLocation(Long id, CreateRoomRequestDto roomRequestDto) {
//        Location currentLocation = findLocationById(id);
//        Room newRoom = new Room()
//                        .setRoomName(roomRequestDto.getRoomName())
//                        .setRoomPrice(roomRequestDto.getRoomPrice())
//                        .setRoomCapacity(roomRequestDto.getRoomMaxCapacity())
//                        .setLocation(currentLocation);
//        Room savedRoom = roomService.saveNewRoom(newRoom);
//        log.info("addRoomToLocation1: " + savedRoom);
////        Location savedLocation = locationRepository.save(currentLocation);
////        log.info("addRoomToLocation3: " + savedLocation);
//        return GetLocationsResponseDto.of(currentLocation);
//    }

}
