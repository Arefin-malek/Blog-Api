package com.Arefin.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Arefin.exceptions.ApiException;
import com.Arefin.payloads.JwtAuthRequest;
import com.Arefin.payloads.JwtAuthResponse;
import com.Arefin.payloads.UserDto;
import com.Arefin.security.JWTokenHelper;
import com.Arefin.services.UserService;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {
	
	@Autowired
	private JWTokenHelper jwtTokenHelper;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(
			@RequestBody JwtAuthRequest request
			)
	{
		this.authenticate(request.getUsername(),request.getPassword());
		
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
		
		String token = this.jwtTokenHelper.generateToken(userDetails);
		
		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(token);
		return new ResponseEntity<JwtAuthResponse>(response,HttpStatus.OK);
	}

	private void authenticate(String username, String password) {
		
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		try {	
			this.authenticationManager.authenticate(authenticationToken);
		}
		catch(BadCredentialsException e)
		{
			System.out.println("Invalid Details");
			throw new ApiException("Invalid Username Or Password !!");
		}
	}
	
	//Register New Register API
	@PostMapping("/register")
	//to  post thing in the database;
	public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto){
		UserDto RegisteredUser = this.userService.registerNewUser(userDto);

		return new ResponseEntity<UserDto>(RegisteredUser ,HttpStatus.CREATED);
	}
	

}
