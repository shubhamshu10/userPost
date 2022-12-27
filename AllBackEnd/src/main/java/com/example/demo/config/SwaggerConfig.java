package com.example.demo.config;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
	public static final String AUTHORIZATION_HEADER="Authorization";
	
	private ApiKey apiKeys() {
		return new ApiKey("JWT",AUTHORIZATION_HEADER,"header");
	}
	private List<SecurityContext> securityContext(){
		return Arrays.asList(SecurityContext.builder().securityReferences(this.securityRef()).build());
		
	}
	private List<SecurityReference> securityRef(){
		AuthorizationScope scope = new AuthorizationScope("Global", "AccessEveryThing");
		return Arrays.asList(new SecurityReference("scope", new AuthorizationScope[] {scope}));
	}
	@Bean
    public Docket api() {
    	return new Docket(DocumentationType.SWAGGER_2)
    			.apiInfo(getInfo())
    			.securityContexts(securityContext())
    			.securitySchemes(Arrays.asList(apiKeys()))
    			.select()
    			.apis(RequestHandlerSelectors.any())
    			.paths(PathSelectors.any()).build();
    }
	
    private ApiInfo getInfo() {
		return new ApiInfo("Full backend", "This course is all about the development for any app where user can upload post and perform actions",
				"1.0", "Terms of service url", new Contact("Shubham","shubhamsharma@gmail.com","https://learncodewithdurgesh.com"),
				"Api licence", "Api license url",Collections.emptyList());
    }
}
