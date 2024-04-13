package com.xabit.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "contact")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contact {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer contactid;
	private String owner;
	private String salutation;
	@Column(name = "firstName", length = 40)
	private String firstName;
	@Column(name = "lastName", length = 40)
	private String lastName;
	
	private String createdBy;
	private Date createdDate;
	private String lastModifiedBy;
	private Date lastModifiedDate;
	@Column(length = 40)
	private String title;
	private long phone;
	private long homePhone;
	private long mobile;
	private long otherPhone;
	private Date birthDate;
	@Column(name = "department", length = 40)
	private String department;
//	private String contactOwner;
	private String email;
	private boolean emailOptOut;
	private String fax;
	private String reportsTo;
	private String leadSource;
	private String assistant;
	private long asstPhone;
	private String mailingAddress;
	private String mailingStreet;
	private String mailingCity;
	private long mailingZipCode;
	private String mailingState;
	private String mailingCountry;

	private String otherAddress;
	private String otherStreet;
	private String otherCity;
	private long otherZipCode;
	private String otherState;
	private String otherCountry;

	private String languages;
	private String level;
	@Column(name = "description", length = 256)
	private String description;

	private Integer accountid;// parent
	private Integer campaignid;
	private Integer orgid;
	private Integer userid;

	@Column(unique = true, length = 10)
	private String ownerAlias;
	@Column(unique = true, length = 10)
	private String createdByAlias;
	@Column(unique = true, length = 10)
	
	private String lastModifiedByAlias;

	private String emailBouncedDate;
	private String emailBouncedReason;
	private String isEmailBounced;
	private String topics;
	private String imagePath;
	private String imageId;

}