package com.xabit.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xabit.model.EmailActivity;

@Repository
public interface EmailActivityRepository extends JpaRepository<EmailActivity, Integer> {

	Optional<EmailActivity> findByTableIdAndTableNameAndStage(Integer tableId, String tableName, String stage);

	List<EmailActivity> findByTableNameAndStage(String tableName, String stage);

	List<EmailActivity> findByTableNameAndStageAndValue(String tableName, String stage, String value);

}
