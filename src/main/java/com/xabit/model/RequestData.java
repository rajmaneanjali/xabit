package com.xabit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestData {

	private String tableName;
	private String columnName;
	private String columnType;
	private String uniqueConstraints;
	private String tableValues;
	private String length;

}
