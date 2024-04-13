package com.xabit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xabit.model.Lookup;
import com.xabit.model.LookupDto;
import com.xabit.service.LookupService;

import lombok.AllArgsConstructor;

@RequestMapping("lookup")
@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class LookupController {

	@Autowired
	private LookupService lookupService;

	@PostMapping
	public String createLookup(@RequestBody LookupDto lookup) {
		lookupService.createLookup(lookup);
		return "Lookup Created Successfully";

	}
}
