package com.xabit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ObjectDto {

	private Integer conditionId;
	private String tableName;
	private String field;
	private String value;
	private String operator;
	private String optimizeFor;

}
