package com.xabit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xabit.model.EnumDetails;
import com.xabit.service.AccountService;
import com.xabit.service.EnumDetailsService;

import lombok.AllArgsConstructor;

@RequestMapping("enumdetails")
@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class EnumDetailsController {

	@Autowired
	private EnumDetailsService enumDetailsService;
	
	@GetMapping("/enumDetails")
	public List<EnumDetails> getEnumDetails() {
		return enumDetailsService.getAllEnumDetails();
	}
}
