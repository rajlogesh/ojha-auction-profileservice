package com.auction.portal.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.auction.portal.constants.ProfileServiceConstants;
import com.auction.portal.dao.RoleDetailsRepository;
import com.auction.portal.dao.UserDetailsRepository;
import com.auction.portal.entity.RoleDetailsEntity;
import com.auction.portal.entity.UserDetailsEntity;
import com.auction.portal.model.ERole;
import com.auction.portal.model.LoginUserRequest;
import com.auction.portal.model.LoginUserResponse;
import com.auction.portal.model.RegisterUserRequest;
import com.auction.portal.model.RegisterUserResponse;
import com.auction.portal.model.UserDetailsVO;
import com.auction.portal.service.UserDetailService;
import com.auction.portal.util.ProfileServiceEntityMapper;
import com.auction.portal.util.ProfileServiceUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Service
public class UserDetailServiceImpl implements UserDetailService, UserDetailsService {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserDetailsRepository userDetailsRepository;

	@Autowired
	RoleDetailsRepository roleDetailsRepository;

	@Autowired
	ProfileServiceUtil util;

	@Autowired
	ProfileServiceEntityMapper mapper;

	@Override
	public RegisterUserResponse registerUser(RegisterUserRequest request) {

		if (userDetailsRepository.existsByUsername(request.getUsername())) {
			throw new RuntimeException();
		}

		if (userDetailsRepository.existsByEmail(request.getEmail())) {
			throw new RuntimeException();
		}

		UserDetailsEntity entity = mapper.userDetailsRequestMapper(request);
		entity.setRoles(getRoleDetailsFromRequest(request.getRoles()));

		UserDetailsEntity result = userDetailsRepository.save(entity);
		RegisterUserResponse response = new RegisterUserResponse();
		response.setResponseID(util.getResponseId());
		response.setResponseMsg("User Registered successfully");
		response.setUserId(result.getUserId().toString());
		return null;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetailsEntity user = userDetailsRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

		return UserDetailsVO.build(user);
	}

	public Set<RoleDetailsEntity> getRoleDetailsFromRequest(List<String> strRoles) {
		Set<RoleDetailsEntity> roles = new HashSet<>();

		if (strRoles == null) {
			RoleDetailsEntity userRole = roleDetailsRepository.findByRoleName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					RoleDetailsEntity adminRole = roleDetailsRepository.findByRoleName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "mod":
					RoleDetailsEntity modRole = roleDetailsRepository.findByRoleName(ERole.ROLE_MODERATOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);

					break;
				default:
					RoleDetailsEntity userRole = roleDetailsRepository.findByRoleName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}
		return roles;
	}

	@Override
	public LoginUserResponse loginUser(LoginUserRequest request) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = JWT.create().withSubject(((UserDetailsVO) authentication.getPrincipal()).getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + ProfileServiceConstants.EXPIRATION_TIME))
				.sign(Algorithm.HMAC512(ProfileServiceConstants.SECRET.getBytes()));

		LoginUserResponse response = new LoginUserResponse();
		response.setToken(jwt);
		response.setUsername(request.getUsername());
		response.setResponseID(util.getResponseId());
		response.setResponseMsg("User Logged in successfully");
		return response;
	}

}
