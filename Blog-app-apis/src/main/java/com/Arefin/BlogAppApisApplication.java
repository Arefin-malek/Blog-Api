package com.Arefin;

import javax.naming.spi.DirStateFactory.Result;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.Arefin.entities.Role;
import com.Arefin.repositories.RoleRepo;

import antlr.collections.List;

@SpringBootApplication
public class BlogAppApisApplication implements CommandLineRunner{

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo; 
	
	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper()
	{
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(this.passwordEncoder.encode("xyz"));
		
		try {
			 
			Role role = new Role();
			role.setId(1);
			role.setName("ROLE_ADMIN");
			
			Role role1 = new Role();
			role1.setId(2);
			role1.setName("ROLE_NORMAL");
			
			java.util.List<Role> list = java.util.List.of(role , role1);
			
			java.util.List<Role> Result = this.roleRepo.saveAll(list);
			
			Result.forEach(r->{
				System.out.println(r.getName());
			});
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

	
	
}
