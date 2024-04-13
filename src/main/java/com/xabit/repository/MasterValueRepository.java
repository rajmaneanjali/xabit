package com.xabit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xabit.model.MasterValueEntity;

@Repository
public interface MasterValueRepository extends JpaRepository<MasterValueEntity, Integer> {

	List<MasterValueEntity> findByRelationId(Integer relationId);

	List<MasterValueEntity> findByMasterId(Integer relationId);

}
