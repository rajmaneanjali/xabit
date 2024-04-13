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

@Table(name = "email")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Email {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String sentTo;
	private String subject;
	private String body;
	private Date createdDate;
	private Date updatedDate;
	private Integer userid;

}
