package com.xabit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import com.xabit.model.Organization;
import com.xabit.service.OrganizationService;

public class OrganizationController {
	@Autowired
	private OrganizationService organizationService;

	@GetMapping
	public List<Organization> getAllOpportunity() {
		return organizationService.getAllOrganization();
	}
}
