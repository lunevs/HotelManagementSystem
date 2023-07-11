package com.ichtus.hotelmanagementsystem.controllers;

import com.ichtus.hotelmanagementsystem.domain.RoomOrder;
import com.ichtus.hotelmanagementsystem.dto.RoomOrderDto;
import com.ichtus.hotelmanagementsystem.services.RoomOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
@Slf4j
public class RoomOrderController {

    private final RoomOrderService orderService;

    @GetMapping
    ResponseEntity<List<RoomOrder>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllActiveOrders());
    }

    @PostMapping
    ResponseEntity<RoomOrder> saveOrder(@RequestBody RoomOrderDto roomOrderDTO) {
        log.info("get new order = " + roomOrderDTO);
        return ResponseEntity.ok(orderService.saveNewOrder(roomOrderDTO));
    }
}
