package com.auction.portal.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.auction.portal.entity.UserDetailsEntity;

public interface UserDetailsRepository extends JpaRepository<UserDetailsEntity, Long>{

	Optional<UserDetailsEntity> findByUsername(String username);
	
	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
}
