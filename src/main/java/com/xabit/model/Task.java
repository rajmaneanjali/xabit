package com.xabit.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name="task")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer taskid;
	private String assignedTo;
	private String status;
	private String subject;
	private Date dueDate;
	private String priority;
	private String name;     //LEAD, CONTACT
	private String relatedTo;
	private String comments;
	private Date reminder;
	private String taskType;

	private Date lastModifiedDate;
	private String email;
	private Date createdDate;
	private Integer incomingEmailCount;
	private Integer outgoingEmailCount;
	private Integer incomingcallCount;
	private Integer outgoingcallCount;

	private Integer opportunityid;
	private Integer leadid;
	private Integer accountid;
	private Integer contactid;
	private Integer campaignid;
	private Integer orgid;
	private Integer userid;

}

