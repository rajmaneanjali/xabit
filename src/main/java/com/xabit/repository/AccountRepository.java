package com.xabit.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xabit.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

	List<Account> findAll();

	@Query(value = "SELECT column_name, data_type FROM information_schema.columns WHERE table_schema = 'xabit' AND table_name = 'account'", nativeQuery = true)
	List<String> getColumnDataTypes();

	List<Account> findByRatingIgnoreCase(String rating);

	List<Account> findByOwnershipIgnoreCase(String ownership);

	List<Account> findByAccountTypeIgnoreCase(String accountType);

	List<Account> findByIndustryIgnoreCase(String industry);

	List<Account> findBySlaIgnoreCase(String sla);

	List<Account> findByUpsellOpportunityIgnoreCase(String upsellOpportunity);

	List<Account> findByCustomerPriorityIgnoreCase(String customerPriority);

//	List<Account> findByActiveIgnoreCase(String active);

	Account findByOwnerAliasAndAccountidNot(@Param("Owneralias") String ownerAlias,
			@Param("accountid") Integer accountid);

	Optional<Account> findByOwnerAlias(String Owneralias);

	@Query(value = "select * from xabit.account", nativeQuery = true)
	List<Map<String, Object>> findByDynamicAllAccount();

	@Query(value = "select * from xabit.account as u where u.accountid=:id", nativeQuery = true)
	List<Map<String, Object>> findByDynamicAccountById(Integer id);

	@Query(value = "select * from xabit.account", nativeQuery = true)
	List<Map<String, Object>> findAllAccounts();

	List<Account> findByAccountid(Integer id);

	@Query(value = "describe xabit.account", nativeQuery = true)
	List<Map<String, Object>> findByField();

}
