package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.ENTITY.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.UserRepository;
@Service
public class CustomUserDetailService implements UserDetailsService{
    @Autowired
	private UserRepository repo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user=this.repo.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("User ", "username", username));
		return user;
	}

}
