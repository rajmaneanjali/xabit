package com.xabit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xabit.model.LookupRelation;

@Repository
public interface LookupRelationRepository extends JpaRepository<LookupRelation, Integer> {

	List<LookupRelation> findByLookupIdIn(List<Integer> ids);

	List<LookupRelation> findByColumnId(Integer id);

}
