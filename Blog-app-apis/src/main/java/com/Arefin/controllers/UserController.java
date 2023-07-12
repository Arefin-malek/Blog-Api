package com.Arefin.controllers;

import javax.validation.Valid;


import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Arefin.payloads.ApiResponse;
import com.Arefin.payloads.UserDto;
import com.Arefin.services.UserService;

import antlr.collections.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private UserService userservice;
	
	@PostMapping("/")
	//to  post thing in the database;
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		UserDto createUserDto = this.userservice.createUser(userDto);

		return new ResponseEntity<>(createUserDto,HttpStatus.CREATED);
	}
	//PUT update user
	@PutMapping("/{userId}")
	///{userId}" with help of - path variable passing value directing to {userId} to uid
	//...@requestbody gives obj of user 
	
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto , @PathVariable("userId") Integer uid)
	{
		UserDto updatedUser = this.userservice.updateUser(userDto, uid);
		return ResponseEntity.ok(updatedUser);
	}
	
	//DELETE 
	//ADMIN Powers
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer uid)
	{
		
		this.userservice.deleteUser(uid);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted Successfully",true),HttpStatus.OK);
		
	}
	
	//GET - USER GET
	
	@GetMapping("/")
	public ResponseEntity<java.util.List<UserDto>> getAllUsers()
	{
		return ResponseEntity.ok(this.userservice.getAllUsers());
	}
	
	//GET - SINGLE USER GET
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getSinglelUsers(@PathVariable("userId") Integer uid)
	{
		return ResponseEntity.ok(this.userservice.getUserById(uid));
	}
	
	
	
}
