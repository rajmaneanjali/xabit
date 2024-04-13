package com.xabit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xabit.model.Condition;
import com.xabit.service.ConditionService;

import lombok.AllArgsConstructor;

@RequestMapping("accounts")
@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class ConditionController {

	@Autowired
	private ConditionService conditionService;
	
	@GetMapping("/conditions")
	public List<Condition> getAllCondition(){
		return conditionService.getAllCondition();	}
	
}
