package com.auction.portal.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.auction.portal.model.ERole;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "ROLE_DETAILS")
@AllArgsConstructor
@NoArgsConstructor
public class RoleDetailsEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "RLE_ID")
	private Long roleId;

	@Enumerated(EnumType.STRING)
	@Column(name = "RLE_NME")
	private ERole roleName;
}
