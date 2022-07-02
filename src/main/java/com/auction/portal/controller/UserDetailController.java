package com.auction.portal.controller;

import static com.auction.portal.constants.ProfileServiceConstants.API_CONTEXT_ROOT;
import static com.auction.portal.constants.ProfileServiceConstants.LOGIN_USER_URI;
import static com.auction.portal.constants.ProfileServiceConstants.REGISTER_USER_URI;
import static com.auction.portal.constants.ProfileServiceConstants.USER_DETAILS_CONTROLLER;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auction.portal.model.LoginUserRequest;
import com.auction.portal.model.LoginUserResponse;
import com.auction.portal.model.RegisterUserRequest;
import com.auction.portal.model.RegisterUserResponse;
import com.auction.portal.service.UserDetailService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = API_CONTEXT_ROOT, produces = { MediaType.APPLICATION_JSON_VALUE, }, consumes = {
		MediaType.APPLICATION_JSON_VALUE })
@Tag(name = USER_DETAILS_CONTROLLER)
public class UserDetailController {

	@Autowired
	UserDetailService service;

	@Operation(summary = REGISTER_USER_URI, description = "This API is used to register the User")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "User Profile created") })
	@PostMapping(REGISTER_USER_URI)
	public ResponseEntity<RegisterUserResponse> registerUser(
			@Parameter(name = "RegisterUserRequest", description = "Request Body", required = true) @RequestBody RegisterUserRequest request) {
		logger.info("Register User started");
		RegisterUserResponse response = service.registerUser(request);
		return ResponseEntity.ok(response);
	}

	@Operation(summary = LOGIN_USER_URI, description = "This API is used to login the User")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Login Successful") })
	@PostMapping(LOGIN_USER_URI)
	public ResponseEntity<LoginUserResponse> saveCompanyData(
			@Parameter(name = "LoginUserRequest", description = "Request Body", required = true) @RequestBody LoginUserRequest request) {
		logger.info("Register User started");
		LoginUserResponse response = service.loginUser(request);
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-SESSION-TOKEN", response.getToken());
		response.setToken(null);
		return ResponseEntity.ok().headers(headers).body(response);
	}

}
