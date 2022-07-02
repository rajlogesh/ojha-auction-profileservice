package com.auction.portal.model;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.auction.portal.entity.UserDetailsEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class UserDetailsVO implements UserDetails {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String userId;
	@JsonIgnore
	private String password;
	private String pan;
	private String email;
	private Collection<? extends GrantedAuthority> authorities;

	public static UserDetailsVO build(UserDetailsEntity user) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getRoleName().name())).collect(Collectors.toList());

		UserDetailsVO userdata = new UserDetailsVO();
		userdata.setId(user.getUserId());
		userdata.setUserId(user.getUsername());
		userdata.setEmail(user.getEmail());
		userdata.setPassword(user.getPassword());
		userdata.setAuthorities(authorities);

		return userdata;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return userId;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsVO user = (UserDetailsVO) o;
		return Objects.equals(id, user.id);
	}

}
