package com.xabit.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xabit.model.Email;
import com.xabit.model.Lead;

@Repository
public interface LeadRepository extends JpaRepository<Lead, Integer> {

	List<Lead> findAll();
	
	List<Lead> findByUserid(Integer id);

	@Query(value = "SELECT column_name, data_type FROM information_schema.columns WHERE table_schema = 'xabit' AND table_name = 'lead'", nativeQuery = true)
	List<String> getColumnDataTypes();
	
	List<Lead> findByLeadSourceIgnoreCase(String leadSource);

	List<Lead> findByLeadStatusIgnoreCase(String leadStatus);

	List<Lead> findByRatingIgnoreCase(String rating);

	List<Lead> findByIndustryIgnoreCase(String industry);

	List<Lead> findByProductInterestIgnoreCase(String productInterest);

	@Query(value = "select * from xabit.lead", nativeQuery = true)
	List<Map<String, Object>> findByDynamicAllLead();

	@Query(value = "select * from xabit.lead as u where u.leadid=:id", nativeQuery = true)
	List<Map<String, Object>> findByDynamicLeadById(Integer id);

	@Query(value = "select * from xabit.lead", nativeQuery = true)
	List<Map<String, Object>> findAllLeads();
	
	@Query(value = "describe xabit.lead" ,nativeQuery = true)
	List<Map<String,Object>> findByField();
	
	
	@Query(value = "select :value from xabit.lead;" ,nativeQuery = true)
	List<String> findByValue(String value);

	
	
//	Email save(Email email);

//	Optional<Lead> findByLeadid(Integer id);

}
