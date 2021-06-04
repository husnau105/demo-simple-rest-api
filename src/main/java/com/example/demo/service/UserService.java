package com.example.demo.service;

import com.example.demo.dto.UserDto;
import com.example.demo.model.User;

import java.util.List;

public interface UserService {
    UserDto create(User user);

    void deleteUser(Long id);

    List<UserDto> getAllUser();

    UserDto getUserByName(String name);

    UserDto getUserById(Long id);

    User updateUser(User user, Long id);

    User updateUserPartially(UserDto user, Long id);
}