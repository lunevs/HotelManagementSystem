package com.ichtus.hotelmanagementsystem.services;

import com.ichtus.hotelmanagementsystem.exceptions.ResourceNotFoundException;
import com.ichtus.hotelmanagementsystem.model.dao.Location;
import com.ichtus.hotelmanagementsystem.model.dao.Room;
import com.ichtus.hotelmanagementsystem.model.dto.CreateLocationRequestDto;
import com.ichtus.hotelmanagementsystem.model.dto.CreateRoomRequestDto;
import com.ichtus.hotelmanagementsystem.model.dto.GetRoomsResponseDto;
import com.ichtus.hotelmanagementsystem.model.dto.UpdateLocationRequestDto;
import com.ichtus.hotelmanagementsystem.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class LocationService {

    private final LocationRepository locationRepository;

    public Iterable<Location> getLocationsList() {
        return locationRepository.findAll();
    }

    public Location addLocation(CreateLocationRequestDto locationDto) {
        Location newLocation = new Location()
                .setLocationName(locationDto.getLocationName())
                .setLocationDescription(locationDto.getLocationDescription());
        return locationRepository.save(newLocation);
    }

    public Location getLocationInfo(Long id) throws ResourceNotFoundException {
        return locationRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public void updateLocationInfo(Long id, UpdateLocationRequestDto newLocationDto) {
        Location currentLocation = getLocationInfo(id);
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

    /**/
    public List<GetRoomsResponseDto> getRoomsList(Location location) {
        Location currentLocation = getLocationInfo(location.getId());
        return currentLocation.getRoomList().stream()
                        .map(room -> new GetRoomsResponseDto(
                                room.getId(),
                                room.getRoomName(),
                                room.getRoomPrice(),
                                room.getRoomCapacity(),
                                location.getId()
                        ))
                        .toList();
    }

    public Location addRoomToLocation(Long id, CreateRoomRequestDto roomRequestDto) {
        Location currentLocation = getLocationInfo(id);
        Room newRoom = new Room()
                        .setRoomName(roomRequestDto.getRoomName())
                        .setRoomPrice(roomRequestDto.getRoomPrice())
                        .setRoomCapacity(roomRequestDto.getRoomMaxCapacity());
        currentLocation.getRoomList().add(newRoom);
        return locationRepository.save(currentLocation);
    }
}
