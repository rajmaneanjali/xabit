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

@Table(name = "emailactivity")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailActivity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer tableId;
	private String tableName;
	private String stage;
	private Date createDate;
	private Date updateDate;
	private Integer conditionId;
	private String field;
	private String operator;
	private String value;
	private String optimizeFor;
	public EmailActivity(Integer id, Integer tableId, String tableName, String stage, Date createDate,
			Date updateDate) {
		super();
		this.id = id;
		this.tableId = tableId;
		this.tableName = tableName;
		this.stage = stage;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}
 
}
