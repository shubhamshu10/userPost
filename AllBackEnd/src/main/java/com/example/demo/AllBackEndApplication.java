package com.example.demo;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.ENTITY.Role;
import com.example.demo.config.ApplicationConstant;
import com.example.demo.repository.RoleRepository;

@SpringBootApplication
public class AllBackEndApplication implements CommandLineRunner{
    @Autowired
    private PasswordEncoder encode;
    
    @Autowired
    private RoleRepository repo;
	public static void main(String[] args) {
		SpringApplication.run(AllBackEndApplication.class, args);
	}
	@Bean
    public ModelMapper model() {
    	return new ModelMapper();
    }
	@Override
	public void run(String... args) throws Exception {
	//	System.out.println(this.encode.encode("shubham"));
		try {
		Role role =new Role();
		role.setId(ApplicationConstant.ADMIN_USER);
		role.setName("ROLE_ADMIN");
		
		Role role1 =new Role();
		role1.setId(ApplicationConstant.NORMAL_USER);
		role1.setName("ROLE_NORMAL");
		
		List<Role> roles=List.of(role,role1);
		this.repo.saveAll(roles);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
