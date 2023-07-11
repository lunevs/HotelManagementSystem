package com.ichtus.hotelmanagementsystem.controllers;

import com.ichtus.hotelmanagementsystem.domain.User;
import com.ichtus.hotelmanagementsystem.dto.UserDto;
import com.ichtus.hotelmanagementsystem.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping
    ResponseEntity<User> saveNewUser(@RequestBody UserDto userDTO) {
        log.info("save new user = " + userDTO);
        return ResponseEntity.ok(userService.saveNewUser(userDTO));
    }
}
