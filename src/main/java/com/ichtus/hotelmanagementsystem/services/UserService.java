package com.ichtus.hotelmanagementsystem.services;

import com.ichtus.hotelmanagementsystem.domain.User;
import com.ichtus.hotelmanagementsystem.dto.UserDto;

public interface UserService {

    public User saveNewUser(UserDto userDTO);
}
