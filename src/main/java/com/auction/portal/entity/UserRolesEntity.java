package com.auction.portal.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "USER_ROLES")
@AllArgsConstructor
@NoArgsConstructor
public class UserRolesEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USR_RLE_ID")
	private Long userRoleid;
	
	@Column(name = "USR_ID")
	private Long userId;

	@Column(name = "RLE_ID")
	private Long roleId;
}
