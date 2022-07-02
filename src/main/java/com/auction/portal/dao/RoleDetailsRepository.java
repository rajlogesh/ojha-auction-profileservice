package com.auction.portal.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.auction.portal.entity.RoleDetailsEntity;
import com.auction.portal.model.ERole;

public interface RoleDetailsRepository extends JpaRepository<RoleDetailsEntity, Long>{

	Optional<RoleDetailsEntity> findByRoleName(ERole name);
	
}
