package com.xabit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xabit.model.MasterEntity;

@Repository
public interface MasterRepository extends JpaRepository<MasterEntity, Integer> {

	List<MasterEntity> findByClassNameIgnoreCase(String className);
}
