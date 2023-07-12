package com.Arefin.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.Arefin.entities.User;

public interface UserRepo extends JpaRepository<User, Integer>{
	Optional<User> findByEmail(String email);
}