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
public class MasterEntity {
	@GeneratedValue(strategy = GenerationType.AUTO)
	
	@Id
	private Integer id;
	private String fieldType;      //datatype
	private String fieldName;		//coloum_name
	private String className;		//tableName
	private String fieldLabel;
}
