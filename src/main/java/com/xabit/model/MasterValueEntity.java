package com.xabit.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MasterValueEntity {

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private Integer id;
	private String value;
	private Integer relationId;
	private Integer masterId;
}
