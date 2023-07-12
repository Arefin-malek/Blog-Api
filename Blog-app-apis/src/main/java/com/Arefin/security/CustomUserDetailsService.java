package com.Arefin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Arefin.entities.User;
import com.Arefin.exceptions.ResouceNotFoundException;
import com.Arefin.repositories.UserRepo;

@Service
public class CustomUserDetailsService  implements UserDetailsService{

	@Autowired
	private UserRepo userepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = this.userepo.findByEmail(username).orElseThrow(()->new ResouceNotFoundException("User" ,"email"+username , 0));
		return user;
	}
}
