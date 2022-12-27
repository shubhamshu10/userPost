package com.example.demo.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
    @Autowired
    private UserDetailsService service;
    @Autowired
    private JwtTokenHelper helper;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String requestToken = request.getHeader("Authorization");
		// Bearer 2352523dgsg
		System.out.println(requestToken);
		String username=null;
		String token=null;
		if(requestToken!=null && requestToken.startsWith("Bearer")) {
			token = requestToken.substring(7);		
			try {
			username = this.helper.getUsernameFromToken(token);
		 }catch(IllegalArgumentException ex) {
			 System.out.println("unable to get jwt token");
		 }catch(ExpiredJwtException ex) {
			 System.out.println(" jwt token has expired");
		 }catch(MalformedJwtException ex) {
			 System.out.println("invailid jwt token");
		 }
		}else {
			System.out.println("jwt token not begin with Bearer");
		}
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			UserDetails userDetail = this.service.loadUserByUsername(username);
			if(this.helper.validateToken(token, userDetail)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(
						userDetail,null,userDetail.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}else {
				System.out.println("Invalid jwt token");
			}
		}else {
			System.out.println("Username is null or context is not null");
		}
		filterChain.doFilter(request, response);
}
}