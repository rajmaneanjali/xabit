package com.xabit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xabit.model.MasterValueEntity;
import com.xabit.service.MasterValueService;

import lombok.AllArgsConstructor;

@RequestMapping("masterValue")
@RestController
@AllArgsConstructor
public class MasterValueController {
	@Autowired
	private MasterValueService masterValueService;

	@PostMapping("/saveMasterValue")
	public MasterValueEntity saveMasterValue(@RequestBody MasterValueEntity masterValueEntity) {
		return masterValueService.saveMasterValue(masterValueEntity);
	}
}
