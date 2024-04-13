package com.xabit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xabit.model.Condition;
import com.xabit.repository.ConditionRepository;

@Service
public class ConditionService {

	@Autowired
	private ConditionRepository conditionRepository;
	
	public List<Condition> getAllCondition(){
		return conditionRepository.findAll();	}
}
