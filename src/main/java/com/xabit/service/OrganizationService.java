package com.xabit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xabit.model.Organization;
import com.xabit.repository.OrganizationRepository;

@Service
public class OrganizationService {
	@Autowired
	private OrganizationRepository organizationRepository;

	public List<Organization> getAllOrganization() {
		return organizationRepository.findAll();
	}
}
