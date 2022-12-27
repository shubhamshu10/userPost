package com.example.demo.payload;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.example.demo.ENTITY.Role;


public class UserDto {
	    private int id;
	    @NotEmpty
	    @Size(min=4 , max=15,message="please put min 4 and max 15 characters")
	    private String name;
	    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
	           message="email should be valid")
	    private String email;
	    @NotEmpty(message="password should be valid")
	    @Size(min=5,max=14)
	    private String password;
	    @NotEmpty
	   
	    private String about;
	    private Set<RoleDto> role=new HashSet<>();
		public Set<RoleDto> getRole() {
			return role;
		}
		public void setRole(Set<RoleDto> role) {
			this.role = role;
		}
		public UserDto() {
			super();
			// TODO Auto-generated constructor stub
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getAbout() {
			return about;
		}
		public void setAbout(String about) {
			this.about = about;
		}
}
