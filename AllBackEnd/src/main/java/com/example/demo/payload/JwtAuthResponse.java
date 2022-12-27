package com.example.demo.payload;

import lombok.NoArgsConstructor;

@NoArgsConstructor

public class JwtAuthResponse {
     private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	} 
}
