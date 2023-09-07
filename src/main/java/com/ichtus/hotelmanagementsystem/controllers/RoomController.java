package com.ichtus.hotelmanagementsystem.controllers;

import com.ichtus.hotelmanagementsystem.model.anotations.IsModerator;
import com.ichtus.hotelmanagementsystem.model.dto.room.RequestRoomCreate;
import com.ichtus.hotelmanagementsystem.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    @IsModerator
    ResponseEntity<?> addRoom(@RequestBody RequestRoomCreate roomRequestDto) {
        return new ResponseEntity<>(roomService.addRoom(roomRequestDto), HttpStatus.OK);
    }

    @PutMapping("/{roomId}")
    ResponseEntity<?> updateRoom(@RequestBody RequestRoomCreate roomRequestDto, @PathVariable Long roomId) {
        return new ResponseEntity<>(roomService.updateRoom(roomId, roomRequestDto), HttpStatus.OK);
    }

}