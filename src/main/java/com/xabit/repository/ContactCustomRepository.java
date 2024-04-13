package com.xabit.repository;

import java.util.Map;

import com.xabit.model.Request;

public interface ContactCustomRepository {
	
	Map<String, Object> findAllAccountContact(Request request);

}
	
