package com.oauth2.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class OAuthAuthorizationServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(OAuthAuthorizationServerApplication.class, args);
	}

	@Bean
	BCryptPasswordEncoder pencoder(){
		return new BCryptPasswordEncoder();
	}

}
