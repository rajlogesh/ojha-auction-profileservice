package com.auction.portal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.auction.portal.util.ProfileServiceEntityMapper;
import com.auction.portal.util.ProfileServiceUtil;

@Configuration
public class ProfileServiceConfig {
	
	@Bean
	public ProfileServiceUtil profileServiceUtil() {
		return new ProfileServiceUtil();
	}
	
	@Bean
	public ProfileServiceEntityMapper profileServiceEntityMapper() {
		return new ProfileServiceEntityMapper();
	}
	
	@Bean 
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
	    return new BCryptPasswordEncoder(); 
	}
}
