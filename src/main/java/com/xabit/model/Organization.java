package com.xabit.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name="organization")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Organization {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer orgid;
	private String orgName;
	private String createDate;
	private String updateDate;
	private String createdBy;
	private String updatedBy;


}
