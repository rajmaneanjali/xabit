package com.xabit.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Table(name = "campaign_data")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Campaign {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer campaignid;
	private String owner;
	private String createdBy;
	private Date createdDate;
	private String lastModifiedBy;
	private Date lastModifiedDate;
	@Column (length=30)
	private String campaignName;
	private boolean active;
	private String type;
	private String status;
	private Date startDate;
	private Date endDate;
	private String parentCampaign;
	private String expectedRevenueInCampaign;
	private String budgetedCostInCampaign;
	private String actualCostInCampaign;
	private long expectedResponse;
	private int numSentInCampaign;
	@Column(length=256)
	private String description;
	private Integer orgid;
	private Integer userid;

}