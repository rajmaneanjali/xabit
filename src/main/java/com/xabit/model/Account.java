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

@Table(name="account")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer accountid;
	private String accountOwner;
	@Column(length=40)
	private String accountName;
	private String accountSource;
	@Column(length=40)
	
	private String accountNumber;
	private String createdBy;
	private Date createdDate;
	private String lastModifiedBy;
	private Date lastModifiedDate;
	private String accountSite;
	private String accountType;
	private String industry;
	private String rating;
	private long phone;
	private String fax;
	private String website;
	private String tickerSymbol;
	private String ownership;
	private Integer employees; //
	private long annualRevenue;  //
	private long sicCode;
	private String billingAddress;
	private String billingStreet;
	private String billingCity;
	private Integer billingZipCode; //
	private String billingState;
	private String billingCountry;

	private String shippingAddress;
	private String shippingStreet;
	private String shippingCity;
	private Integer shippingZipCode;//
	private String shippingState;
	private String shippingCountry;

	private String customerPriority;
	private Date slaExpirationDate;
	private Integer numberOfLocation;//
	private String sla;
	private Integer slaSerialNumber;
	private String upsellOpportunity;
	private boolean active;
	private String description;
	private Integer numberOfEmployees;
	private String yearStarted;
	private Integer orgid;
	private Integer userid;
	
	
	private String ownerFirstName;
	private String ownerLastName;
	@Column(unique = true, length = 10)
	private String ownerAlias;
	
	
	



}
