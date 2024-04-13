package com.xabit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xabit.model.MasterEntity;
import com.xabit.repository.MasterRepository;

@Service
@Transactional
public class MasterService {
	@Autowired
	private MasterRepository masterRepository;

	public MasterEntity saveMasterField(MasterEntity masterEntity) {
		return masterRepository.save(masterEntity);
	}

	public List<MasterEntity> findAllMasterData() {
		return masterRepository.findAll();
	}
}
