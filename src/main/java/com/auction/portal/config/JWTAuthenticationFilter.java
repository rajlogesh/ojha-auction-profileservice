package com.auction.portal.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auction.portal.constants.ProfileServiceConstants;
import com.auction.portal.entity.UserDetailsEntity;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;

		setFilterProcessesUrl(ProfileServiceConstants.API_CONTEXT_ROOT + ProfileServiceConstants.LOGIN_USER_URI);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException {
		try {
			UserDetailsEntity creds = new ObjectMapper().readValue(req.getInputStream(), UserDetailsEntity.class);

			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(creds.getUsername(),
					creds.getPassword(), new ArrayList<>()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Authentication auth) throws IOException {
		String token = JWT.create().withSubject(((UserDetailsEntity) auth.getPrincipal()).getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + ProfileServiceConstants.EXPIRATION_TIME))
				.sign(Algorithm.HMAC512(ProfileServiceConstants.SECRET.getBytes()));

		String body = ((UserDetailsEntity) auth.getPrincipal()).getUsername() + " " + token;

		res.getWriter().write(body);
		res.getWriter().flush();
	}
}
