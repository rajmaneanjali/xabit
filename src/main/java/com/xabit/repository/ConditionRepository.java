package com.xabit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xabit.model.Condition;

@Repository
public interface ConditionRepository extends JpaRepository<Condition, Integer> {

	List<Condition> findAll();
}
