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
@Table(name="opportunity")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Opportunity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer opportunityid;
	@Column(length=120)
	private String accountName;
	private String name;
	private long amount;
	private Date closeDate;  //date
	private String contact;  //lookup
	private String createdBy;
	private Date createdDate;
	@Column(length=255)
	private String description;
	private long expectedRevenue;
	private String forecastCategoryName;   //picklist
	private String lastModifiedBy;
	private Date lastModifiedDate;
	private String leadSource;
	@Column(length=255)
	private String nextStep;
	private String type;
	private String owner;
	private Integer iqScore;
	private String pricebook2;
	private boolean isPrivate;    //checkbox-- boolean
	private boolean closed;
	private String probability;
	private String primarycampaignsource;
	private String ordernumber;
	private String currentGenerators;
	private long trackingNumber;
	private String mainCompetitor;
	private Integer totalOpportunityQuantity;
	private String stage;
	private String status;

	private String ownerFirstName;
	private String ownerLastName;
	private String ownerFullName;
	
	@Column(unique = true, length = 10)
	private String opportunityOwnerAlias;
	@Column(unique = true, length = 10)
	private String createdByAlias;
	@Column(unique = true, length = 10)
	private String lastModifiedByAlias;
	
	private String topics;
	private String accountSite;
	private String lastActivity;
	private String lastStageChangeDate;
	private long pushCount;
	private long quantity;
	private long orderNumber;
	private boolean won;




	private Integer accountid;  //parent
	private Integer orgid;
	private Integer userid;


}