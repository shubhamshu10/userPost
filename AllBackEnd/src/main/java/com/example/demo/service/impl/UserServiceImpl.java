package com.example.demo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.ENTITY.Role;
import com.example.demo.ENTITY.User;
import com.example.demo.config.ApplicationConstant;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.payload.UserDto;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
@Service
public class UserServiceImpl implements UserService{
    @Autowired
	private UserRepository repo; 
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private RoleRepository roleRepo;
    @Override
	public UserDto createUser(UserDto dto) {
		User user = this.dtoToUser(dto);
		User saveuser = this.repo.save(user);
		return this.userToDto(saveuser);
	}

	@Override
	public UserDto updateUser(UserDto dto, Integer user_id) {
		User user= this.repo.findById(user_id).orElseThrow(()-> new ResourceNotFoundException("user"," id",user_id));
		user.setName(dto.getName());
		user.setEmail(dto.getEmail());
		user.setPassword(dto.getPassword());
		user.setAbout(dto.getAbout());
		User updateUser = repo.save(user);
		UserDto dto1 = this.userToDto(updateUser);
		return dto1;
	}

	@Override
	public UserDto getUserById(int id) {
		User user= this.repo.findById(id).orElseThrow(()-> new ResourceNotFoundException("user"," id",id));

		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUser() {
		List<User> users = this.repo.findAll();
		List<UserDto> dto = users.stream().map(user ->this.userToDto(user)).collect(Collectors.toList());
		return dto;
	}

	@Override
	public void deleteUserById(int id) {
		User user=this.repo.findById(id).orElseThrow(()-> new ResourceNotFoundException("user", " id", id));
		repo.delete(user);
	}
    public User dtoToUser(UserDto dto) {
    	User user = this.modelMapper.map(dto, User.class);
        return user;   
    }
    public UserDto userToDto(User user) {
    	UserDto dto = this.modelMapper.map(user, UserDto.class);
        return dto;   
    }

	@Override
	public UserDto registerNewUser(UserDto dto) {
		User user=this.modelMapper.map(dto, User.class);
		user.setPassword(this.encoder.encode(user.getPassword()));
		//roles
		Role role=this.roleRepo.findById(ApplicationConstant.NORMAL_USER).get();
		user.getRole().add(role);
		User newUser = this.repo.save(user);
		return this.modelMapper.map(newUser, UserDto.class);
	}
}
