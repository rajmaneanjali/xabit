package com.xabit.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xabit.model.Account;
import com.xabit.model.Lead;
import com.xabit.model.Opportunity;

@Repository
public interface OpportunityRepository extends JpaRepository<Opportunity, Integer> {

	// opportunityid
	List<Opportunity> findByOpportunityid(Integer opportunityid);
//	List< Map<String, Object>> findAlls();
//	 List<Opportunity> findAllOpportunity();
	
	List<Opportunity> findAll();
	
	@Query(value = "SELECT column_name, data_type FROM information_schema.columns WHERE table_schema = 'xabit' AND table_name = 'opportunity'", nativeQuery = true)
	List<String> getColumnDataTypes();
	
	// stage
	List<Opportunity> findByStageIgnoreCase(String stage);

	// type
	List<Opportunity> findByTypeIgnoreCase(String type);

	// leadSource
	List<Opportunity> findByleadSourceIgnoreCase(String leadSource);

	// status
	List<Opportunity> findByStatusIgnoreCase(String status);

	// amount
	// GreaterThan api
	List<Opportunity> findByAmountGreaterThan(Long amount);

	// lessThan api
	List<Opportunity> findByAmountLessThan(Long amount);

	// not equal to api
	List<Opportunity> findByAmountNot(Long amount);

	// equal api
	List<Opportunity> findByAmountEquals(Long amount);

	// greater or equal
	List<Opportunity> findByAmountGreaterThanEqual(Long amount);

	//// less or equal
	List<Opportunity> findByAmountLessThanEqual(Long amount);

	// expectedRevenue
	// GreaterThan api
	List<Opportunity> findByExpectedRevenueGreaterThan(Long expectedRevenue);

	// lessThan api
	List<Opportunity> findByExpectedRevenueLessThan(Long expectedRevenue);

	// not equal to api
	List<Opportunity> findByExpectedRevenueNot(Long expectedRevenue);

	// equal api
	List<Opportunity> findByExpectedRevenueEquals(Long expectedRevenue);

	List<Opportunity> findByExpectedRevenueLessThanEqual(Long expectedRevenue);

	List<Opportunity> findByExpectedRevenueGreaterThanEqual(Long expectedRevenue);

	// closed
	List<Opportunity> findByClosed(Boolean status);

	// isPrivate
	List<Opportunity> findByisPrivate(Boolean isPrivate);

	// won;
	List<Opportunity> findByWon(Boolean won);

	// trackingNumber(long) // GreaterThan api
	List<Opportunity> findBytrackingNumberGreaterThan(Long trackingNumber);

	// lessThan api
	List<Opportunity> findBytrackingNumberLessThan(Long trackingNumber);

	// not equal to api
	List<Opportunity> findBytrackingNumberNot(Long trackingNumber);

	// equal api
	List<Opportunity> findBytrackingNumberEquals(Long trackingNumber);

	// greater or equal
	List<Opportunity> findBytrackingNumberGreaterThanEqual(Long trackingNumber);

	//// less or equal
	List<Opportunity> findBytrackingNumberLessThanEqual(Long trackingNumber);

	// pushCount(long)
	// GreaterThan api
	List<Opportunity> findBypushCountGreaterThan(Long pushCount);

	// lessThan api
	List<Opportunity> findBypushCountLessThan(Long pushCount);

	// not equal to api
	List<Opportunity> findBypushCountNot(Long pushCount);

	// equal api
	List<Opportunity> findBypushCountEquals(Long pushCount);

	// greater or equal
	List<Opportunity> findBypushCountGreaterThanEqual(Long pushCount);

	//// less or equal
	List<Opportunity> findBypushCountLessThanEqual(Long pushCount);

	// quantity(long)
	// GreaterThan api
	List<Opportunity> findByQuantityGreaterThan(Long quantity);

	// lessThan api
	List<Opportunity> findByQuantityLessThan(Long quantity);

	// not equal to api
	List<Opportunity> findByQuantityNot(Long quantity);

	// equal api
	List<Opportunity> findByQuantityEquals(Long quantity);

	// greater or equal
	List<Opportunity> findByQuantityGreaterThanEqual(Long quantity);

	//// less or equal
	List<Opportunity> findByQuantityLessThanEqual(Long quantity);

	// orderNumber(long)
	// GreaterThan api
	List<Opportunity> findByorderNumberGreaterThan(Long orderNumber);

	// lessThan api
	List<Opportunity> findByorderNumberLessThan(Long orderNumber);

	// not equal to api
	List<Opportunity> findByorderNumberNot(Long orderNumber);

	// equal api
	List<Opportunity> findByorderNumberEquals(Long orderNumber);

	// greater or equal
	List<Opportunity> findByorderNumberGreaterThanEqual(Long orderNumber);

	//// less or equal
	List<Opportunity> findByorderNumberLessThanEqual(Long orderNumber);

	// stage
	List<Opportunity> findByStageContainingIgnoreCase(String stage);

	List<Opportunity> findByStageNotContainingIgnoreCase(String stage);

	List<Opportunity> findByStageStartsWithIgnoreCase(String stage);

	// leadSource
	List<Opportunity> findByleadSourceContainingIgnoreCase(String leadSource);

	List<Opportunity> findByleadSourceNotContainingIgnoreCase(String leadSource);

	List<Opportunity> findByleadSourceStartsWithIgnoreCase(String leadSource);

	// accountName
	List<Opportunity> findByAccountNameContainingIgnoreCase(String accountName);

	List<Opportunity> findByAccountNameNotContainingIgnoreCase(String accountName);

	List<Opportunity> findByAccountNameStartsWithIgnoreCase(String accountName);

	// mainCompetitor
	List<Opportunity> findBymainCompetitorContainingIgnoreCase(String mainCompetitor);

	List<Opportunity> findBymainCompetitorNotContainingIgnoreCase(String mainCompetitor);

	List<Opportunity> findBymainCompetitorStartsWithIgnoreCase(String mainCompetitor);

	// ownerFirstName
	List<Opportunity> findByownerFirstNameContainingIgnoreCase(String ownerFirstName);

	List<Opportunity> findByownerFirstNameNotContainingIgnoreCase(String ownerFirstName);

	List<Opportunity> findByownerFirstNameStartsWithIgnoreCase(String ownerFirstName);

	// ownerLastName
	List<Opportunity> findByownerLastNameContainingIgnoreCase(String ownerLastName);

	List<Opportunity> findByownerLastNameNotContainingIgnoreCase(String ownerLastName);

	List<Opportunity> findByownerLastNameStartsWithIgnoreCase(String ownerLastName);

	// ownerFullName
	List<Opportunity> findByownerFullNameContainingIgnoreCase(String ownerFullName);

	List<Opportunity> findByownerFullNameNotContainingIgnoreCase(String ownerFullName);

	List<Opportunity> findByownerFullNameStartsWithIgnoreCase(String ownerFullName);

	// opportunityOwnerAlias
	List<Opportunity> findByopportunityOwnerAliasContainingIgnoreCase(String opportunityOwnerAlias);

	List<Opportunity> findByopportunityOwnerAliasNotContainingIgnoreCase(String opportunityOwnerAlias);

	List<Opportunity> findByopportunityOwnerAliasStartsWithIgnoreCase(String opportunityOwnerAlias);

	// createdByAlias
	List<Opportunity> findBycreatedByAliasContainingIgnoreCase(String createdByAlias);

	List<Opportunity> findBycreatedByAliasNotContainingIgnoreCase(String createdByAlias);

	List<Opportunity> findBycreatedByAliasStartsWithIgnoreCase(String createdByAlias);

	// lastModifiedByAlias
	List<Opportunity> findBylastModifiedByAliasContainingIgnoreCase(String lastModifiedByAlias);

	List<Opportunity> findBylastModifiedByAliasNotContainingIgnoreCase(String lastModifiedByAlias);

	List<Opportunity> findBylastModifiedByAliasStartsWithIgnoreCase(String lastModifiedByAlias);

	// topics
	List<Opportunity> findByTopicsContainingIgnoreCase(String topics);

	List<Opportunity> findByTopicsNotContainingIgnoreCase(String topics);

	// accountSite
	List<Opportunity> findByAccountSiteContainingIgnoreCase(String accountSite);

	List<Opportunity> findByAccountSiteNotContainingIgnoreCase(String accountSite);

	List<Opportunity> findByAccountSiteStartsWithIgnoreCase(String accountSite);

	// lastActivity
	List<Opportunity> findBylastActivityContainingIgnoreCase(String lastActivity);

	List<Opportunity> findBylastActivityNotContainingIgnoreCase(String lastActivity);

	List<Opportunity> findBylastActivityStartsWithIgnoreCase(String lastActivity);

	// lastStageChangeDate
	List<Opportunity> findBylastStageChangeDateGreaterThan(Date lastStageChangeDate);

	List<Opportunity> findBylastStageChangeDateLessThan(Date lastStageChangeDate);

	List<Opportunity> findBylastStageChangeDateNot(Date lastStageChangeDate);

	List<Opportunity> findBylastStageChangeDateEquals(Date lastStageChangeDate);

	List<Opportunity> findBylastStageChangeDateLessThanEqual(Date lastStageChangeDate);

	List<Opportunity> findBylastStageChangeDateGreaterThanEqual(Date lastStageChangeDate);

	// createdDate
	List<Opportunity> findBycreatedDateGreaterThan(Date createdDate);

	List<Opportunity> findBycreatedDateLessThan(Date createdDate);

	List<Opportunity> findBycreatedDateNot(Date createdDate);

	List<Opportunity> findBycreatedDateEquals(Date createdDate);

	List<Opportunity> findBycreatedDateLessThanEqual(Date createdDate);

	List<Opportunity> findBycreatedDateGreaterThanEqual(Date createdDate);

	// closeDate
	List<Opportunity> findBycloseDateGreaterThan(Date closeDate);

	List<Opportunity> findBycloseDateLessThan(Date closeDate);

	List<Opportunity> findBycloseDateNot(Date closeDate);

	List<Opportunity> findBycloseDateEquals(Date closeDate);

	List<Opportunity> findBycloseDateLessThanEqual(Date closeDate);

	List<Opportunity> findBycloseDateGreaterThanEqual(Date closeDate);

	// lastModifiedDate
	List<Opportunity> findBylastModifiedDateGreaterThan(Date lastModifiedDate);

	List<Opportunity> findBylastModifiedDateLessThan(Date lastModifiedDate);

	List<Opportunity> findBylastModifiedDateNot(Date lastModifiedDate);

	List<Opportunity> findBylastModifiedDateEquals(Date lastModifiedDate);

	List<Opportunity> findBylastModifiedDateLessThanEqual(Date lastModifiedDate);

	List<Opportunity> findBylastModifiedDateGreaterThanEqual(Date lastModifiedDate);

	// status
	List<Opportunity> findByStatusContainingIgnoreCase(String status);

	List<Opportunity> findByStatusNotContainingIgnoreCase(String status);

	List<Opportunity> findByStatusStartsWithIgnoreCase(String status);

	// forecastCategoryName
	List<Opportunity> findByforecastCategoryNameContainingIgnoreCase(String forecastCategoryName);

	List<Opportunity> findByforecastCategoryNameNotContainingIgnoreCase(String forecastCategoryName);

	List<Opportunity> findByforecastCategoryNameStartsWithIgnoreCase(String forecastCategoryName);

	// nextStep
	List<Opportunity> findBynextStepContainingIgnoreCase(String nextStep);

	List<Opportunity> findBynextStepNotContainingIgnoreCase(String nextStep);

	List<Opportunity> findBynextStepStartsWithIgnoreCase(String nextStep);

	// name
	List<Opportunity> findByNameContainingIgnoreCase(String name);

	List<Opportunity> findByNameNotContainingIgnoreCase(String name);

	List<Opportunity> findByNameStartsWithIgnoreCase(String name);

	// probability
	// GreaterThan api
	List<Opportunity> findByProbabilityGreaterThan(String probability);

	// lessThan api
	List<Opportunity> findByProbabilityLessThan(String probability);

	// not equal to api
	List<Opportunity> findByProbabilityNot(String probability);

	// equal api
	List<Opportunity> findByProbabilityEquals(String probability);

	// greater or equal
	List<Opportunity> findByProbabilityGreaterThanEqual(String probability);

	//// less or equal
	List<Opportunity> findByProbabilityLessThanEqual(String probability);

	// type
	List<Opportunity> findByTypeContainingIgnoreCase(String type);

	List<Opportunity> findByTypeNotContainingIgnoreCase(String type);

	List<Opportunity> findByTypeStartsWithIgnoreCase(String type);

	// currentGenerators
	List<Opportunity> findBycurrentGeneratorsContainingIgnoreCase(String currentGenerators);

	List<Opportunity> findBycurrentGeneratorsNotContainingIgnoreCase(String currentGenerators);

	List<Opportunity> findBycurrentGeneratorsStartsWithIgnoreCase(String currentGenerators);

	Opportunity findByCreatedByAliasAndAccountidNot(@Param("createdByAlias") String createdByAlias,
			@Param("opportunityid") Integer opportunityid);

	Optional<Opportunity> findByCreatedByAlias(String createdByAlias);

	Optional<Opportunity> findByLastModifiedByAlias(String lastModifiedByAlias);

	Optional<Opportunity> findByOpportunityOwnerAlias(String opportunityOwnerAlias);

	@Query(value = "select * from xabit.opportunity", nativeQuery = true)
	List<Map<String, Object>> findByDynamicAllOpportunity();

	@Query(value = "select * from xabit.opportunity as u where u.opportunityid=:id", nativeQuery = true)
	List<Map<String, Object>> findByDynamicOpportunityById(Integer id);

	@Query(value = "select * from xabit.opportunity", nativeQuery = true)
	List<Map<String, Object>> findAllOpportunities();	
	
//	Optional<Opportunity> findByOpportunityid(Integer id);
	
	@Query(value = "describe xabit.opportunity" ,nativeQuery = true)
	List<Map<String,Object>> findByField();
}
