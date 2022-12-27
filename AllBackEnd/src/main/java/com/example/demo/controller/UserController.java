package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
//import org.springframework.http.RequestEntity;
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

import com.example.demo.payload.ApiResponse;
import com.example.demo.payload.UserDto;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
    private UserService service;
	@PostMapping("/")
	public ResponseEntity<UserDto> createDto(@Valid @RequestBody UserDto dto){
		UserDto createUser = this.service.createUser(dto);
		return new ResponseEntity<>(createUser,HttpStatus.CREATED);
		
	}
	@PutMapping("/update/{id}")
	public ResponseEntity<UserDto> updateDto(@Valid @RequestBody UserDto dto, @PathVariable("id")int id){
		UserDto updateUser = this.service.updateUser(dto, id);
		return ResponseEntity.ok(updateUser); 
	}
	//admin
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ApiResponse> deleteDto(@PathVariable("id") int id){
		this.service.deleteUserById(id);
		return new ResponseEntity<ApiResponse>(new ApiResponse("user deleted successfully!",true),HttpStatus.OK);
	}
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUserDtos(){
		return ResponseEntity.ok(this.service.getAllUser());
	}
	@GetMapping("/{id}")
	public ResponseEntity<UserDto> getuser(@PathVariable("id") int id){
		return ResponseEntity.ok(this.service.getUserById(id));
	}
}
