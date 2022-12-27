package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.example.demo.security.CustomUserDetailService;
import com.example.demo.security.JwtAuthenticationEntryPoint;
import com.example.demo.security.JwtAuthenticationFilter;
//import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebMvc
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	public static final String[] PUBLIC_URLS= {
		"/api/auth/**",
		"/v3/api-docs",
		"/v2/api-docs",
		"/swagger-resources/**",
		"/swagger-ui.html/**",
		"/webjars/**"
	};
	@Autowired
	private CustomUserDetailService service;
	@Autowired
	private JwtAuthenticationEntryPoint entryPoint;
	@Autowired
	private JwtAuthenticationFilter authFilter;
    @Bean
	public PasswordEncoder encode() {
    	return new BCryptPasswordEncoder();
    }
    
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	     auth.userDetailsService(this.service).passwordEncoder(this.encode());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeHttpRequests()
		.antMatchers(PUBLIC_URLS).permitAll()
		.antMatchers(HttpMethod.GET).permitAll()
		.anyRequest().authenticated().and().exceptionHandling().
		authenticationEntryPoint(this.entryPoint).and().sessionManagement().
		sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(this.authFilter, UsernamePasswordAuthenticationFilter.class);
	}
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(PUBLIC_URLS);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		
		return super.authenticationManagerBean();
	}

}
