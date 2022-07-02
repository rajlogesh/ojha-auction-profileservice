package com.auction.portal.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "USER_PROFILE", uniqueConstraints = { @UniqueConstraint(columnNames = "USR_NME"),
		@UniqueConstraint(columnNames = "USR_MAIL_ID") })
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USR_ID")
	private Long userId;

	@Column(name = "USR_NME")
	private String username;

	@Column(name = "USR_PWD")
	private String password;

	@Column(name = "FRST_NME")
	private String firstName;
	
	@Column(name = "LST_NME")
	private String lastName;
	
	@Column(name = "USR_ADDR")
	private String address;
	
	@Column(name = "USR_CTY")
	private String city;
	
	@Column(name = "USR_STE")
	private String state;
	
	@Column(name = "USR_CNTRY")
	private String country;
	
	@Column(name = "USR_PIN")
	private Integer pin;
	
	@Column(name = "USR_PHN")
	private Integer phone;
	
	@Column(name = "USR_MAIL_ID")
	private String email;
	
	@Column(name = "CRT_TMS")
	private Date creationDate;

	@Column(name = "UPDT_TMS")
	private Date updateDate;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "USER_ROLES", 
				joinColumns = @JoinColumn(name = "USR_ID"), 
				inverseJoinColumns = @JoinColumn(name = "RLE_ID"))
	private Set<RoleDetailsEntity> roles = new HashSet<>();

}