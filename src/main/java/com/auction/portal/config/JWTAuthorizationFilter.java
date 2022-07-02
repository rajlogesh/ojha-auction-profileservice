package com.auction.portal.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auction.portal.constants.ProfileServiceConstants;
import com.auction.portal.service.impl.UserDetailServiceImpl;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	@Autowired
	private UserDetailServiceImpl userDetailsService;
	
    public JWTAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(ProfileServiceConstants.HEADER_STRING);

        if (header == null || !header.startsWith(ProfileServiceConstants.TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    // Reads the JWT from the Authorization header, and then uses JWT to validate the token
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(ProfileServiceConstants.HEADER_STRING);

        token = token.replace(ProfileServiceConstants.TOKEN_PREFIX, "");
        if (token != null && !token.equalsIgnoreCase("null")) {
            // parse the token.
            String user = JWT.require(Algorithm.HMAC512(ProfileServiceConstants.SECRET.getBytes()))
                    .build()
                    .verify(token.replace(ProfileServiceConstants.TOKEN_PREFIX, ""))
                    .getSubject();

            UserDetails userDetails = userDetailsService.loadUserByUsername(user);
            
            if (user != null) {
                // new arraylist means authorities
                return new UsernamePasswordAuthenticationToken(user, null, userDetails.getAuthorities());
            }

            return null;
        }

        return null;
    }
    
}