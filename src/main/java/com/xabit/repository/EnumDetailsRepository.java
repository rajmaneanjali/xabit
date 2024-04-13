package com.xabit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xabit.model.EnumDetails;
@Repository
public interface EnumDetailsRepository extends JpaRepository<EnumDetails, Integer>{

	List<EnumDetails> findAll();
}
