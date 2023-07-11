package com.ichtus.hotelmanagementsystem.services;

import com.ichtus.hotelmanagementsystem.domain.User;
import com.ichtus.hotelmanagementsystem.dto.UserDto;
import com.ichtus.hotelmanagementsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements  UserService {

    private final UserRepository userRepository;


    @Override
    public User saveNewUser(UserDto userDTO) {
        User user = new User(
                null,
                userDTO.getName(),
                null,
                null,
                null
        );
        return userRepository.save(user);
    }
}
