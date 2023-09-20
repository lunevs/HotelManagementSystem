package com.ichtus.hotelmanagementsystem.controllers;

import com.ichtus.hotelmanagementsystem.model.anotations.IsModerator;
import com.ichtus.hotelmanagementsystem.model.anotations.IsUser;
import com.ichtus.hotelmanagementsystem.model.dto.room.RequestRoomCreate;
import com.ichtus.hotelmanagementsystem.model.dto.room.ResponseRoomData;
import com.ichtus.hotelmanagementsystem.services.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller allows to manage Rooms in a hotel
 * @author smlunev
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;

    /**
     * Endpoint to get one room details
     * @param id searched room id
     * @return dto ResponseRoomData with room information
     */
    @GetMapping("{id}")
    @IsUser
    ResponseEntity<ResponseRoomData> getRoomData(@PathVariable Long id) {
        return new ResponseEntity<>(
                ResponseRoomData.of(roomService.findById(id)),
                HttpStatus.OK
        );
    }

    /**
     * Endpoint to create new room in a hotel
     * @param roomRequestDto dto with new room information
     * @return dto ResponseRoomData with created room data
     */
    @PostMapping
    @IsModerator
    ResponseEntity<ResponseRoomData> addRoom(@Valid @RequestBody RequestRoomCreate roomRequestDto) {
        return new ResponseEntity<>(roomService.addRoom(roomRequestDto), HttpStatus.OK);
    }

    /**
     * Endpoint to update room specification
     * @param roomRequestDto dto with room information to update
     * @param roomId updated room id
     * @return dto ResponseRoomData with updated room data
     */
    @PutMapping("/{roomId}")
    @IsModerator
    ResponseEntity<?> updateRoom(@Valid @RequestBody RequestRoomCreate roomRequestDto, @PathVariable Long roomId) {
        return new ResponseEntity<>(roomService.updateRoom(roomId, roomRequestDto), HttpStatus.OK);
    }

    /**
     * Endpoint to delete room
     * @param id deleted room id
     * @return empty response with status 200
     */
    @DeleteMapping("/{id}")
    @IsModerator
    public ResponseEntity<?> deleteRoom(@PathVariable Long id) {
        if (roomService.deleteRoom(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
