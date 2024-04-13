package com.xabit.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name="lookuprelation")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LookupRelation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer lookupId;
	private Integer columnId;
	private Integer relationId;
}
