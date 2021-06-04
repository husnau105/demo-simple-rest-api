package com.example.demo.dto;

import com.example.demo.model.User;
import com.example.demo.model.enumaration.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long userId;
    private String name;
    private String username;
    private UserRole role;
}
