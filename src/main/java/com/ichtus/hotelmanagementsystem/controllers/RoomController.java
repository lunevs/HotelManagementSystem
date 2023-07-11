package com.ichtus.hotelmanagementsystem.controllers;

import com.ichtus.hotelmanagementsystem.domain.Room;
import com.ichtus.hotelmanagementsystem.dto.RoomDto;
import com.ichtus.hotelmanagementsystem.services.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/room")
@Slf4j
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    ResponseEntity<Room> saveNewRoom(@RequestBody RoomDto roomDto) {
        log.info("Save new room " + roomDto.getRoomTitle() + " to Hotel " + roomDto.getHotelTitle());
        return ResponseEntity.ok(roomService.saveNewRoom(roomDto));
    }

    @GetMapping
    ResponseEntity<Iterable<Room>> findAll(@RequestParam(required = false) Long hotelId) {
        return ResponseEntity.ok(roomService.findAll(hotelId));
    }


}
