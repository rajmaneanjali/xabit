package com.xabit.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xabit.model.Account;
import com.xabit.model.Campaign;
import com.xabit.model.Contact;
import com.xabit.model.Lead;
import com.xabit.model.Lookup;
import com.xabit.model.Opportunity;
import com.xabit.model.Task;
import com.xabit.repository.AccountRepository;
import com.xabit.repository.CampaignRepository;
import com.xabit.repository.ContactRepository;
import com.xabit.repository.LeadRepository;
import com.xabit.repository.LookupRelationRepository;
import com.xabit.repository.LookupRepository;
import com.xabit.repository.MasterRepository;
import com.xabit.repository.MasterValueRepository;
import com.xabit.repository.OpportunityRepository;
import com.xabit.repository.TaskRepository;
import com.xabit.utility.LookupName;

@Service
public class AccountService {
	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private MasterValueRepository masterValueRepository;

	@Autowired
	private MasterRepository masterRepository;

	@Autowired
	private LookupRepository lookupRepository;

	@Autowired
	private LeadRepository leadRepository;

	@Autowired
	private ContactRepository contactRepository;

	@Autowired
	private CampaignRepository campaignRepository;

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private OpportunityRepository opportunityRepository;

	@Autowired
	private LookupRelationRepository lookupRelationRepository;
	
	@PersistenceContext
    private EntityManager entityManager;   
	
	
	public List<Account> getAllAccounts() {
		
		return accountRepository.findAll();
	}
	public List<Map<String, Object>> getAllAccount() {
		List<Map<String, Object>> lstMap = new ArrayList<>();
		List<Map<String, Object>> responseMap = new ArrayList<>();

		lstMap = accountRepository.findAllAccounts();

		List<Lookup> lstLookup = lookupRepository.findByRelatedTo("ACCOUNT");

		if (!lstLookup.isEmpty()) {
			Map<String, String> map = new HashMap<>();
			for (Lookup lookup : lstLookup) {
				map.put(lookup.getRelatedWith(), lookup.getRelatedWith());
			}

			String leadLookupValue = map.get(LookupName.LEAD.toString());
			if (leadLookupValue != null && leadLookupValue.equalsIgnoreCase(LookupName.LEAD.toString())) {
				checkLookupRelations(responseMap, lstMap);
			}

			String contactLookupValue = map.get(LookupName.CONTACT.toString());
			if (contactLookupValue != null && contactLookupValue.equalsIgnoreCase(LookupName.CONTACT.toString())) {
				checkLookupRelations1(responseMap, lstMap);
			}

			String opportunityLookupValue = map.get(LookupName.OPPORTUNITY.toString());
			if (opportunityLookupValue != null
					&& opportunityLookupValue.equalsIgnoreCase(LookupName.OPPORTUNITY.toString())) {
				checkLookupRelations2(responseMap, lstMap);
			}

			String taskLookupValue = map.get(LookupName.TASK.toString());
			if (taskLookupValue != null && taskLookupValue.equalsIgnoreCase(LookupName.TASK.toString())) {
				checkLookupRelations3(responseMap, lstMap);
			}

			String campaignLookupValue = map.get(LookupName.CAMPAIGN.toString());
			if (campaignLookupValue != null && campaignLookupValue.equalsIgnoreCase(LookupName.CAMPAIGN.toString())) {
				checkLookupRelations4(responseMap, lstMap);
			}

		}

		List<Map<String, Object>> deduped = responseMap.stream().distinct().collect(Collectors.toList());

		return deduped;

	}

	private List<Map<String, Object>> checkLookupRelations(List<Map<String, Object>> responseMap,
			List<Map<String, Object>> lstMap) {

		Map<String, Object> leadMap = new HashMap<>();
		Map<String, Object> accountMap = new HashMap<>();

		List<Lead> leadDataList = leadRepository.findAll();

		accountMap.put("AccountData", lstMap);
		leadMap.put("LeadData", leadDataList);

		responseMap.add(accountMap);
		responseMap.add(leadMap);

		return responseMap;

	}

	private List<Map<String, Object>> checkLookupRelations1(List<Map<String, Object>> responseMap,
			List<Map<String, Object>> lstMap) {

		Map<String, Object> contactMap = new HashMap<>();
		Map<String, Object> accountMap = new HashMap<>();

		List<Contact> contactDataList = contactRepository.findAll();

		accountMap.put("AccountData", lstMap);
		contactMap.put("ContactData", contactDataList);

		responseMap.add(accountMap);
		responseMap.add(contactMap);

		return responseMap;

	}

	private List<Map<String, Object>> checkLookupRelations2(List<Map<String, Object>> responseMap,
			List<Map<String, Object>> lstMap) {

		Map<String, Object> opportunityMap = new HashMap<>();
		Map<String, Object> accountMap = new HashMap<>();

		List<Opportunity> opportunityDataList = opportunityRepository.findAll();

		accountMap.put("AccountData", lstMap);
		opportunityMap.put("OpportunityData", opportunityDataList);

		responseMap.add(accountMap);
		responseMap.add(opportunityMap);

		return responseMap;

	}

	private List<Map<String, Object>> checkLookupRelations3(List<Map<String, Object>> responseMap,
			List<Map<String, Object>> lstMap) {

		Map<String, Object> taskMap = new HashMap<>();
		Map<String, Object> accountMap = new HashMap<>();

		List<Task> taskDataList = taskRepository.findAll();

		accountMap.put("AccountData", lstMap);
		taskMap.put("TaskData", taskDataList);

		responseMap.add(accountMap);
		responseMap.add(taskMap);

		return responseMap;

	}

	private List<Map<String, Object>> checkLookupRelations4(List<Map<String, Object>> responseMap,
			List<Map<String, Object>> lstMap) {

		Map<String, Object> campaignMap = new HashMap<>();
		Map<String, Object> accountMap = new HashMap<>();

		List<Campaign> campaignDataList = campaignRepository.findAll();

		accountMap.put("AccountData", lstMap);
		campaignMap.put("CampaignData", campaignDataList);

		responseMap.add(accountMap);
		responseMap.add(campaignMap);

		return responseMap;

	}

	public Account getAccountById(Integer id) {
		return accountRepository.findById(id).orElse(null);
	}

	public List<Account> findByRating(String rating) {
		return accountRepository.findByRatingIgnoreCase(rating);
	}

	public List<Account> findByOwnership(String ownership) {
		return accountRepository.findByOwnershipIgnoreCase(ownership);
	}

	public List<Account> findByAccountType(String accountType) {
		return accountRepository.findByAccountTypeIgnoreCase(accountType);
	}

	public List<Account> findByIndustry(String industry) {
		return accountRepository.findByIndustryIgnoreCase(industry);
	}

	public List<Account> findBySla(String sla) {
		return accountRepository.findBySlaIgnoreCase(sla);
	}

	public List<Account> findByUpsellOpportunity(String upsellOpportunity) {
		return accountRepository.findByUpsellOpportunityIgnoreCase(upsellOpportunity);
	}

	public List<Account> findByCustomerPriority(String customerPriority) {
		return accountRepository.findByCustomerPriorityIgnoreCase(customerPriority);
	}

//	public List<Account> findByActive(String active) {
//		return accountRepository.findByActiveIgnoreCase(active);
//	}

	public Account createAccount(Account account) {
		account.setOwnerAlias(manipulateAccountNames(account));
		return accountRepository.save(account);
	}

	public String manipulateAccountNames(Account account) {
		String OwnerLastName = account.getOwnerLastName();

		if (OwnerLastName.length() > 6) {
			OwnerLastName = OwnerLastName.substring(0, 6);
		}

		String OwnerFirstName = account.getOwnerFirstName().substring(0, 1);

		String modifiedName = OwnerLastName + OwnerFirstName;

		// Check if the alias already exists in the database
		int suffix = 0;
		String aliasToCheck = modifiedName;

		while (isAliasExists(aliasToCheck)) {
			suffix++;
			aliasToCheck = modifiedName + String.format("%02d", suffix);
		}

		account.setOwnerAlias(aliasToCheck);

		return aliasToCheck;
	}

	private boolean isAliasExists(String ownerAlias) {
		Optional<Account> existingUser = accountRepository.findByOwnerAlias(ownerAlias);
		return existingUser.isPresent();
	}

	public Account updateAccount(Account account) {
		Account account1 = accountRepository.findById(account.getAccountid()).orElse(null);
		account1.setAccountOwner(account.getAccountOwner());
		account1.setAccountName(account.getAccountName());
		account1.setAccountSource(account.getAccountSource());
		account1.setAccountNumber(account.getAccountNumber());
		account1.setCreatedBy(account.getCreatedBy());
		account1.setCreatedDate(account.getCreatedDate());
		account1.setLastModifiedBy(account.getLastModifiedBy());
		account1.setLastModifiedDate(account.getLastModifiedDate());
		account1.setAccountSite(account.getAccountSite());
		account1.setAccountType(account.getAccountType());
		account1.setIndustry(account.getIndustry());
		account1.setRating(account.getRating());
		account1.setPhone(account.getPhone());
		account1.setFax(account.getFax());
		account1.setWebsite(account.getWebsite());
		account1.setTickerSymbol(account.getTickerSymbol());
		account1.setOwnership(account.getOwnership());
		account1.setEmployees(account.getEmployees());
		account1.setAnnualRevenue(account.getAnnualRevenue());
		account1.setSicCode(account.getSicCode());
		account1.setBillingAddress(account.getBillingAddress());
		account1.setBillingStreet(account.getBillingStreet());
		account1.setBillingCity(account.getBillingCity());
		account1.setBillingZipCode(account.getBillingZipCode());
		account1.setBillingState(account.getBillingState());
		account1.setBillingCountry(account.getBillingCountry());

		account1.setShippingAddress(account.getShippingAddress());
		account1.setShippingStreet(account.getShippingStreet());
		account1.setShippingCity(account.getShippingCity());
		account1.setShippingZipCode(account.getShippingZipCode());
		account1.setShippingState(account.getShippingState());
		account1.setShippingCountry(account.getShippingCountry());

		account1.setCustomerPriority(account.getCustomerPriority());
		account1.setSlaExpirationDate(account.getSlaExpirationDate());
		account1.setNumberOfLocation(account.getNumberOfLocation());
		account1.setSla(account.getSla());
		account1.setSlaSerialNumber(account.getSlaSerialNumber());
		account1.setUpsellOpportunity(account.getUpsellOpportunity());
		account1.setActive(account.isActive());
		account1.setDescription(account.getDescription());
		account1.setNumberOfEmployees(account.getNumberOfEmployees());
		account1.setYearStarted(account.getYearStarted());
		account1.setOwnerFirstName(account.getOwnerFirstName());
		account1.setOwnerLastName(account.getOwnerLastName());
		account1.setOwnerAlias(account.getOwnerAlias());
		return accountRepository.save(account1);
	}

	public void deleteAccount(Integer id) {
		accountRepository.deleteById(id);
	}

	@Transactional
	public void deleteAccountByIds(List<Integer> accountid) {
		accountRepository.deleteAllByIdInBatch(accountid);
	}

	public List<String> getColumnDataTypes() {
        return accountRepository.getColumnDataTypes();
    }
	
}
