package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.ApiException;
import com.example.demo.payload.JwtAuthRequest;
import com.example.demo.payload.JwtAuthResponse;
import com.example.demo.payload.UserDto;
import com.example.demo.security.JwtTokenHelper;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private JwtTokenHelper helper;
    @Autowired
    private UserDetailsService service;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authManager;
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception{
         this.authenticate(request.getUsername(), request.getPassword());	
         UserDetails user=this.service.loadUserByUsername(request.getUsername());
         String token=this.helper.generateToken(user);
         JwtAuthResponse res=new JwtAuthResponse();
         res.setToken(token);
         return new ResponseEntity<JwtAuthResponse>(res,HttpStatus.OK);
    }
    private void authenticate(String username, String password) throws Exception {
    	UsernamePasswordAuthenticationToken authToken= new UsernamePasswordAuthenticationToken(username, password);
    	try {
    	this.authManager.authenticate(authToken);
    }catch(BadCredentialsException e) {
    	System.out.println("Invalid details!!");
    	throw new ApiException("Invalid username or password");
    }
    } 
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto dto){
    	UserDto registeredUser=this.userService.registerNewUser(dto);
    	return new ResponseEntity<UserDto>(registeredUser,HttpStatus.CREATED);
    }
}
