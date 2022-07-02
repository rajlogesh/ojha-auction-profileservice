package com.auction.portal.model;

import java.util.List;

import lombok.Data;

@Data
public class RegisterUserRequest {

	private Long userId;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String address;
	private String city;
	private String state;
	private String country;
	private Integer pin;
	private Integer phone;
	private String email;
	private List<String> roles;

}
