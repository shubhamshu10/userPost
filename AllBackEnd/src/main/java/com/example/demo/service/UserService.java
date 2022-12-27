package com.example.demo.service;

import java.util.List;

import com.example.demo.payload.UserDto;

public interface UserService {
	UserDto registerNewUser(UserDto dto);
     UserDto createUser(UserDto user);
     UserDto updateUser(UserDto user, Integer user_id);
     UserDto getUserById(int id);
     List<UserDto> getAllUser();
     void deleteUserById(int id);
}
