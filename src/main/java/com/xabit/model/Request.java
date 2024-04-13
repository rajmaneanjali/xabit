package com.xabit.model;

import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Request {

	private Integer pageNo;
	private Integer pageSize;
	private Boolean sortOrder;
	private String sortFieldName;
	private String value;
	private String field;
	private String operator;
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Boolean getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(Boolean sortOrder) {
		this.sortOrder = sortOrder;
	}
	public String getSortFieldName() {
		return sortFieldName;
	}
	public void setSortFieldName(String sortFieldName) {
		this.sortFieldName = sortFieldName;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	@Override
	public String toString() {
		return "Request [pageNo=" + pageNo + ", pageSize=" + pageSize + ", sortOrder=" + sortOrder + ", sortFieldName="
				+ sortFieldName + ", value=" + value + ", field=" + field + ", operator=" + operator + "]";
	}
	
	
}
