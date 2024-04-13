package com.xabit.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LookupDto {

	private String relatedTo;
	private String relatedWith;
//	List<LookupRelation> lookupRelationList = new ArrayList<>();

}
