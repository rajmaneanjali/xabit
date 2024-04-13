package com.xabit.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "user")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userid;
	@Column(unique = true, length = 10)
	private String alias;
	@Column(length = 20)
	private String firstName;
	@Column(length = 20)
	private String lastName;
	private String createdBy;
	private Date createdDate;
	private String lastModifiedBy;
	private Date lastModifiedDate;
	@Column(length = 40)
	private String companyName;
	private String email;
	
	
	@Column(unique = true)
	private String userName;
	private String language;
	private String locale;
	private long mobile;
	private String title;
	private Date birthDate;

}

