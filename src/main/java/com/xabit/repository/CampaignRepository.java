package com.xabit.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xabit.model.Campaign;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Integer>{

	List<Campaign> findAll();
	List<Campaign> findByTypeIgnoreCase(String type);
	
	@Query(value = "SELECT column_name, data_type FROM information_schema.columns WHERE table_schema = 'xabit' AND table_name = 'campaign'", nativeQuery = true)
	List<String> getColumnDataTypes();

	List<Campaign> findByStatusIgnoreCase(String status);

	@Query(value = "select * from xabit.campaign", nativeQuery = true)
	List<Map<String, Object>> findByDynamicAllCampaign();
	
	@Query(value = "select * from xabit.campaign as u where u.campaignid=:id", nativeQuery = true)
	List<Map<String, Object>> findByDynamicCampaignById(Integer id);

	
	@Query(value = "select * from xabit.campaign", nativeQuery = true)
	List<Map<String, Object>> findAllCampaigns();
	
	List<Campaign> findByCampaignid(Integer id);
	
	@Query(value = "describe xabit.campaign" ,nativeQuery = true)
	List<Map<String,Object>> findByField();

}
