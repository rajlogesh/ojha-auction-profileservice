package com.auction.portal.util;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.auction.portal.entity.UserDetailsEntity;
import com.auction.portal.model.RegisterUserRequest;

public class ProfileServiceEntityMapper {

	@Autowired
	PasswordEncoder encoder;
	
	public UserDetailsEntity userDetailsRequestMapper(RegisterUserRequest request) {
		UserDetailsEntity entity = new UserDetailsEntity();
		entity.setUserId(request.getUserId());
		entity.setUsername(request.getUsername());
		entity.setEmail(request.getEmail());
		entity.setPassword(encoder.encode(request.getPassword()));
		entity.setAddress(request.getAddress());
		entity.setCity(request.getCity());
		entity.setState(request.getState());
		entity.setCountry(request.getCountry());
		entity.setPin(request.getPin());
		entity.setPhone(request.getPhone());
		entity.setFirstName(request.getFirstName());
		entity.setLastName(request.getLastName());
		entity.setCreationDate(new Date());
		entity.setUpdateDate(new Date());
		return entity;
	}
	
}
