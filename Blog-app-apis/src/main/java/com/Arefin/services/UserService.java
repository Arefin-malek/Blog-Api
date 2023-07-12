package com.Arefin.services;

import com.Arefin.entities.User;
import com.Arefin.payloads.UserDto;

public interface UserService {
	
	UserDto registerNewUser(UserDto userDto);
	UserDto createUser(UserDto user);
	UserDto updateUser(UserDto user , Integer userId);
	UserDto getUserById(Integer userId);
	java.util.List<UserDto> getAllUsers();
	void deleteUser(Integer userId);
	
	

}
