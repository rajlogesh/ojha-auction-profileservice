package com.auction.portal.service;

import com.auction.portal.model.LoginUserRequest;
import com.auction.portal.model.LoginUserResponse;
import com.auction.portal.model.RegisterUserRequest;
import com.auction.portal.model.RegisterUserResponse;

public interface UserDetailService {

	public RegisterUserResponse registerUser(RegisterUserRequest request);
	
	public LoginUserResponse loginUser(LoginUserRequest request);

}
