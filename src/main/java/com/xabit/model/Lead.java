package com.xabit.model;


import java.time.LocalDateTime;
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

@Table(name = "lead")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Lead {
	@Id()
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer leadid;
	private String salutation;
	private String owner;
	@Column(name="firstName", length=40)
	private String firstName;
	@Column(name="lastName", length=40)
	private String lastName;
	@Column(name="company", length=40)
	private String company;
	@Column(length=40)
	private String title;
	private String createdBy;
	private Date createdDate;
	private String lastModifiedBy;
	private Date lastModifiedDate;
	private long mobile;
	private long phone;
	private String email;
	private String fax;
	private String leadStatus;
	private String leadSource;
	@Column(length=256)
	private String description;
	private String industry;
	private String website;
	private String annualRevenue;
	private String noOfEmployees;
	private String rating;
	@Column(length=20)
	private String address;
	@Column(length=20)
	private String street;
	@Column(length=20)
	private String city;
	@Column(length=20)
	private String state;
	@Column(length=6)
	private int zipCode;
	@Column(length=20)
	private String country;
	private String productInterest;
	private String currentGenerator;
	private String sicCode;
	private String primary;
	private String numberOfLocations;

	private Integer campaignid;//parent
	private Integer orgid;
	private Integer userid;
	

}




