package com.auction.portal.model;

import lombok.Data;

@Data
public class LoginUserResponse extends SuccessResponse {

	private String username;
	private String token;

}
