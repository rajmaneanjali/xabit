package com.xabit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xabit.model.EnumDetails;
import com.xabit.repository.EnumDetailsRepository;

@Service
public class EnumDetailsService {

	@Autowired
	private EnumDetailsRepository enumDetailsRepository;
	
	public List<EnumDetails> getAllEnumDetails() {
		
		return enumDetailsRepository.findAll();
	}

}
