package com.ichtus.hotelmanagementsystem.services;

import com.ichtus.hotelmanagementsystem.exceptions.LocationNotFoundException;
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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class LocationService {

    private final LocationRepository locationRepository;

    public Iterable<GetLocationsResponseDto> getLocationsList() {
        return locationRepository.findAll().stream()
                .map(GetLocationsResponseDto::of)
                .collect(Collectors.toList());
    }

    public Location addLocation(CreateLocationRequestDto locationDto) {
        Location newLocation = new Location()
                .setLocationName(locationDto.getLocationName())
                .setLocationDescription(locationDto.getLocationDescription());
        return locationRepository.save(newLocation);
    }

    private Location findLocationById(Long id) throws LocationNotFoundException {
        return locationRepository
                .findById(id)
                .orElseThrow(() -> new LocationNotFoundException(id));
    }

    public GetLocationInfoResponseDto getLocationInfo(Long id) {
        Location currentLocation = findLocationById(id);
        return GetLocationInfoResponseDto.of(currentLocation);
    }

    public void updateLocationInfo(Long id, UpdateLocationRequestDto newLocationDto) {
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
        locationRepository.save(currentLocation);
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

    public GetLocationsResponseDto addRoomToLocation(Long id, CreateRoomRequestDto roomRequestDto) {
        Location currentLocation = findLocationById(id);
        Room newRoom = new Room()
                        .setRoomName(roomRequestDto.getRoomName())
                        .setRoomPrice(roomRequestDto.getRoomPrice())
                        .setRoomCapacity(roomRequestDto.getRoomMaxCapacity());
        currentLocation.getRoomsList().add(newRoom);
        return GetLocationsResponseDto.of(locationRepository.save(currentLocation));
    }
}
