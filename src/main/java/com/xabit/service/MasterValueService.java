package com.xabit.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xabit.model.MasterValueEntity;
import com.xabit.repository.MasterValueRepository;

@Service
@Transactional
public class MasterValueService {
	@Autowired
	private MasterValueRepository masterValueRepository;
	
	public MasterValueEntity saveMasterValue(MasterValueEntity masterValueEntity) {
		return masterValueRepository.save(masterValueEntity);
	}

}
