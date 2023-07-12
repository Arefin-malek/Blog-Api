package com.Arefin.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.Arefin.entities.*;
import com.Arefin.payloads.UserDto;
import com.Arefin.repositories.*;
import com.Arefin.services.UserService;
import com.Arefin.exceptions.*;
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired 
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		
		User user = this.dtoToUser(userDto);
		
	    User savedUser = this.userRepo.save(user);
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {

		User user = this.userRepo.findById(userId).
				orElseThrow(()-> new ResouceNotFoundException("User", "Id", userId));
				
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		
		User updateduser = this.userRepo.save(user);
		UserDto userDto1 = this.userToDto(updateduser);
		
		
		return userDto1;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user = this.userRepo.findById(userId).
				orElseThrow(()-> new ResouceNotFoundException("User", "Id", userId));
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		
		List<User> users = this.userRepo.findAll();
		//converting list of users into list of userDto;
		List<UserDto> usersDto = users.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
		
		return usersDto;
	}

	@Override
	public void deleteUser(Integer userId) {
		
		User user = this.userRepo.findById(userId).
				orElseThrow(()-> new ResouceNotFoundException("User", "Id", userId));
		
		this.userRepo.delete(user);

	}
	public User dtoToUser(UserDto userDto)
	{
		User user = this.modelMapper.map(userDto, User.class);
		
		
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setAbout(userDto.getAbout());
//		user.setPassword(userDto.getPassword());
		
		return user;
		
	}
	
	public UserDto userToDto(User user)
	{
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setAbout(user.getAbout());
//		userDto.setPassword(user.getPassword());
		return userDto;
		
	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		
		User user = this.modelMapper.map(userDto, User.class);
		//Password Encoded
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		//Roles
		//Normal User = 2;
		//Admin = 1
		Role role = this.roleRepo.findById(2).get();
		
		user.getRoles().add(role);
		
		User user2 = this.userRepo.save(user);
		
		return this.modelMapper.map(user , UserDto.class);
	}

}
