package com.xabit.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xabit.model.Account;
import com.xabit.model.Campaign;
import com.xabit.model.Contact;
import com.xabit.model.Lead;
import com.xabit.model.Lookup;
import com.xabit.model.Opportunity;
import com.xabit.model.Request;
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
import com.xabit.utility.Field;
import com.xabit.utility.LookupName;
import com.xabit.utility.Operator;

@Service
public class OpportunityService {
	@Autowired
	private OpportunityRepository opportunityRepository;
	@Autowired
	private MasterValueRepository masterValueRepository;

	@Autowired
	private MasterRepository masterRepository;

	@Autowired
	private LookupRepository lookupRepository;

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private ContactRepository contactRepository;

	@Autowired
	private CampaignRepository campaignRepository;
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private LeadRepository leadRepository;

	@Autowired
	private LookupRelationRepository lookupRelationRepository;

	public List<Opportunity> getAllOpportunities() {
		return opportunityRepository.findAll();
	}

	public List<String> getColumnDataTypes() {
        return opportunityRepository.getColumnDataTypes();
    }
	
	

	public List<Map<String, Object>> getAllOpportunity() {
		List<Map<String, Object>> lstMap = new ArrayList<>();
//		List<Map<String, Object>> responseList = new ArrayList<>();
		List<Map<String, Object>> responseMap = new ArrayList<>();

		lstMap = opportunityRepository.findAllOpportunities();

		List<Lookup> lstLookup = lookupRepository.findByRelatedTo("OPPORTUNITY");

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

			String accountLookupValue = map.get(LookupName.ACCOUNT.toString());
			if (accountLookupValue != null && accountLookupValue.equalsIgnoreCase(LookupName.ACCOUNT.toString())) {
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
		Map<String, Object> opportunityMap = new HashMap<>();

		List<Lead> leadDataList = leadRepository.findAll();

		opportunityMap.put("OpportunityData", lstMap);
		leadMap.put("LeadData", leadDataList);

		responseMap.add(opportunityMap);
		responseMap.add(leadMap);

		return responseMap;

	}

	private List<Map<String, Object>> checkLookupRelations1(List<Map<String, Object>> responseMap,
			List<Map<String, Object>> lstMap) {

		Map<String, Object> contactMap = new HashMap<>();
		Map<String, Object> opportunityMap = new HashMap<>();

		List<Contact> contactDataList = contactRepository.findAll();

		opportunityMap.put("OpportunityData", lstMap);
		contactMap.put("ContactData", contactDataList);

		responseMap.add(opportunityMap);
		responseMap.add(contactMap);

		return responseMap;

	}

	private List<Map<String, Object>> checkLookupRelations2(List<Map<String, Object>> responseMap,
			List<Map<String, Object>> lstMap) {

		Map<String, Object> accountMap = new HashMap<>();
		Map<String, Object> opportunityMap = new HashMap<>();

		List<Account> accountDataList = accountRepository.findAll();

		opportunityMap.put("OpportunityData", lstMap);
		accountMap.put("AccountData", accountDataList);

		responseMap.add(opportunityMap);
		responseMap.add(accountMap);

		return responseMap;

	}

	private List<Map<String, Object>> checkLookupRelations3(List<Map<String, Object>> responseMap,
			List<Map<String, Object>> lstMap) {

		Map<String, Object> taskMap = new HashMap<>();
		Map<String, Object> opportunityMap = new HashMap<>();

		List<Task> taskDataList = taskRepository.findAll();

		opportunityMap.put("OpportunityData", lstMap);
		taskMap.put("TaskData", taskDataList);

		responseMap.add(opportunityMap);
		responseMap.add(taskMap);

		return responseMap;

	}

	private List<Map<String, Object>> checkLookupRelations4(List<Map<String, Object>> responseMap,
			List<Map<String, Object>> lstMap) {

		Map<String, Object> campaignMap = new HashMap<>();
		Map<String, Object> opportunityMap = new HashMap<>();

		List<Campaign> campaignDataList = campaignRepository.findAll();

		opportunityMap.put("OpportunityData", lstMap);
		campaignMap.put("CampaignData", campaignDataList);

		responseMap.add(opportunityMap);
		responseMap.add(campaignMap);

		return responseMap;

	}

	public Opportunity getOpportunityById(Integer id) {
		return opportunityRepository.findById(id).orElse(null);
	}

	public List<Opportunity> findByType(String type) {
		List<Opportunity> listOpportunity = opportunityRepository.findByTypeIgnoreCase(type);
		return listOpportunity; // Return the combined list
	}

	public List<Opportunity> findByleadSource(String leadSource) {
		List<Opportunity> listOpportunity = opportunityRepository.findByleadSourceIgnoreCase(leadSource);
		return listOpportunity;
	}

	public List<Opportunity> findByStage(String stage) {
		List<Opportunity> listOpportunity = opportunityRepository.findByStageIgnoreCase(stage);
		return listOpportunity;
	}

	public List<Opportunity> findByStatus(String status) {
		List<Opportunity> listOpportunity = opportunityRepository.findByStatusIgnoreCase(status);
		return listOpportunity;
	}

	public Opportunity createOpportunity(Opportunity opportunity) {
		String createdByAlias = manipulateOpportunityNames(opportunity);
		opportunity.setCreatedByAlias(createdByAlias);
//        opportunity.setLastModifiedByAlias(createdByAlias); // Set initially to created by alias
		opportunity.setOpportunityOwnerAlias(manipulateOpportunityNames(opportunity));
		return opportunityRepository.save(opportunity);
	}

	private String manipulateOpportunityNames(Opportunity opportunity) {
		String ownerLastName = opportunity.getOwnerLastName();

		if (ownerLastName.length() > 6) {
			ownerLastName = ownerLastName.substring(0, 6);
		}

		String ownerFirstName = opportunity.getOwnerFirstName().substring(0, 1);

		String modifiedName = ownerLastName + ownerFirstName;

		// Check if the alias already exists in the database
		int suffix = 0;
		String aliasToCheck = modifiedName;

		while (isAliasExists(aliasToCheck)) {
			suffix++;
			aliasToCheck = modifiedName + String.format("%02d", suffix);
		}

		return aliasToCheck;
	}

	private boolean isAliasExists(String aliasToCheck) {
		Optional<Opportunity> existingOpportunity = opportunityRepository.findByCreatedByAlias(aliasToCheck);
		return existingOpportunity.isPresent();
	}

	public Opportunity updateOpportunity(Opportunity opportunity) {

		String lastModifiedByAlias = manipulateOpportunityNames(opportunity);
		opportunity.setLastModifiedByAlias(lastModifiedByAlias);

		Opportunity opportunity1 = opportunityRepository.findById(opportunity.getOpportunityid()).orElse(null);
		opportunity1.setAccountName(opportunity.getAccountName());
		opportunity1.setName(opportunity.getName());
		opportunity1.setAmount(opportunity.getAmount());
		opportunity1.setCloseDate(opportunity.getCloseDate());
		opportunity1.setContact(opportunity.getContact());
		opportunity1.setCreatedBy(opportunity.getCreatedBy());
		opportunity1.setCreatedDate(opportunity.getCreatedDate());
		opportunity1.setDescription(opportunity.getDescription());
		opportunity1.setExpectedRevenue(opportunity.getExpectedRevenue());
		opportunity1.setForecastCategoryName(opportunity.getForecastCategoryName());
		opportunity1.setLastModifiedBy(opportunity.getLastModifiedBy());
		opportunity1.setLastModifiedDate(opportunity.getLastModifiedDate());
		opportunity1.setLeadSource(opportunity.getLeadSource());
		opportunity1.setNextStep(opportunity.getNextStep());
		opportunity1.setType(opportunity.getType());
		opportunity1.setOwner(opportunity.getOwner());
		opportunity1.setIqScore(opportunity.getIqScore());
		opportunity1.setPricebook2(opportunity.getPricebook2());
		opportunity1.setPrivate(opportunity.isPrivate());
		opportunity1.setClosed(opportunity.isClosed());
		opportunity1.setProbability(opportunity.getProbability());
		opportunity1.setPrimarycampaignsource(opportunity.getPrimarycampaignsource());
		opportunity1.setOrdernumber(opportunity.getOrdernumber());
		opportunity1.setCurrentGenerators(opportunity.getCurrentGenerators());
		opportunity1.setTrackingNumber(opportunity.getTrackingNumber());
		opportunity1.setMainCompetitor(opportunity.getMainCompetitor());
		opportunity1.setTotalOpportunityQuantity(opportunity.getTotalOpportunityQuantity());
		opportunity1.setStage(opportunity.getStage());
		opportunity1.setStatus(opportunity.getStatus());
		opportunity1.setOwnerFirstName(opportunity.getOwnerFirstName());
		opportunity1.setOwnerLastName(opportunity.getOwnerLastName());
		opportunity1.setOwnerFullName(opportunity.getOwnerFullName());
		opportunity1.setOpportunityOwnerAlias(opportunity.getOpportunityOwnerAlias());
		opportunity1.setCreatedByAlias(opportunity.getCreatedByAlias());
		opportunity1.setLastModifiedByAlias(opportunity.getLastModifiedByAlias());
		opportunity1.setTopics(opportunity.getTopics());
		opportunity1.setAccountSite(opportunity.getAccountSite());
		opportunity1.setLastActivity(opportunity.getLastActivity());
		opportunity1.setLastStageChangeDate(opportunity.getLastStageChangeDate());
		opportunity1.setPushCount(opportunity.getPushCount());
		opportunity1.setQuantity(opportunity.getQuantity());
		opportunity1.setOrdernumber(opportunity.getOrdernumber());
		opportunity1.setWon(opportunity.isWon());
		opportunity1.setOrgid(opportunity.getOrgid());
		opportunity1.setUserid(opportunity.getUserid());
		opportunity1.setAccountid(opportunity.getAccountid());
		return opportunityRepository.save(opportunity);

	}

//	public List<Opportunity> findByFilter(Field field, Operator operator, String value) {
//
//		List<Opportunity> listOpp = new ArrayList<>();
//
//		if (field.toString().equalsIgnoreCase(Field.AMOUNT.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.GREATER_THAN.toString())) {
//			Long longValue = Long.parseLong(value);
//			return opportunityRepository.findByAmountGreaterThan(longValue);
//		} else if (field.toString().equalsIgnoreCase(Field.AMOUNT.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.LESS_THAN.toString())) {
//			Long longValue = Long.parseLong(value);
//			return opportunityRepository.findByAmountLessThan(longValue);
//		} else if (field.toString().equalsIgnoreCase(Field.AMOUNT.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.EQUALS.toString())) {
//			Long longValue = Long.parseLong(value);
//			return opportunityRepository.findByAmountEquals(longValue);
//		} else if (field.toString().equalsIgnoreCase(Field.AMOUNT.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.NOT_EQUALS_TO.toString())) {
//			Long longValue = Long.parseLong(value);
//			return opportunityRepository.findByAmountNot(longValue);
//		} else if (field.toString().equalsIgnoreCase(Field.AMOUNT.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.GREATER_OR_EQUAL.toString())) {
//			Long longValue = Long.parseLong(value);
//			return opportunityRepository.findByAmountGreaterThanEqual(longValue);
//		} else if (field.toString().equalsIgnoreCase(Field.AMOUNT.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.LESS_OR_EQUAL.toString())) {
//			Long longValue = Long.parseLong(value);
//			return opportunityRepository.findByAmountLessThanEqual(longValue);
//
//		}
//
//		else if (field.toString().equalsIgnoreCase(Field.EXPECTED_REVENUE.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.GREATER_THAN.toString())) {
//			Long longValue = Long.parseLong(value);
//			return opportunityRepository.findByExpectedRevenueGreaterThan(longValue);
//		} else if (field.toString().equalsIgnoreCase(Field.EXPECTED_REVENUE.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.LESS_THAN.toString())) {
//			Long longValue = Long.parseLong(value);
//			return opportunityRepository.findByExpectedRevenueLessThan(longValue);
//		} else if (field.toString().equalsIgnoreCase(Field.EXPECTED_REVENUE.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.NOT_EQUALS_TO.toString())) {
//			Long longValue = Long.parseLong(value);
//			return opportunityRepository.findByExpectedRevenueNot(longValue);
//		} else if (field.toString().equalsIgnoreCase(Field.EXPECTED_REVENUE.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.EQUALS.toString())) {
//			Long longValue = Long.parseLong(value);
//			return opportunityRepository.findByExpectedRevenueEquals(longValue);
//		} else if (field.toString().equalsIgnoreCase(Field.EXPECTED_REVENUE.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.LESS_OR_EQUAL.toString())) {
//			Long longValue = Long.parseLong(value);
//			return opportunityRepository.findByExpectedRevenueLessThanEqual(longValue);
//		} else if (field.toString().equalsIgnoreCase(Field.EXPECTED_REVENUE.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.GREATER_OR_EQUAL.toString())) {
//			Long longValue = Long.parseLong(value);
//			return opportunityRepository.findByExpectedRevenueGreaterThanEqual(longValue);
//		}
//		// trackingNumber(long)
//		if (field.toString().equalsIgnoreCase(Field.TRACKING_NUMBER.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.GREATER_THAN.toString())) {
//			Long longValue = Long.parseLong(value);
//			return opportunityRepository.findBytrackingNumberGreaterThan(longValue);
//		} else if (field.toString().equalsIgnoreCase(Field.TRACKING_NUMBER.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.LESS_THAN.toString())) {
//			Long longValue = Long.parseLong(value);
//			return opportunityRepository.findBytrackingNumberLessThan(longValue);
//		} else if (field.toString().equalsIgnoreCase(Field.TRACKING_NUMBER.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.EQUALS.toString())) {
//			Long longValue = Long.parseLong(value);
//			return opportunityRepository.findBytrackingNumberEquals(longValue);
//		} else if (field.toString().equalsIgnoreCase(Field.TRACKING_NUMBER.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.NOT_EQUALS_TO.toString())) {
//			Long longValue = Long.parseLong(value);
//			return opportunityRepository.findBytrackingNumberNot(longValue);
//		} else if (field.toString().equalsIgnoreCase(Field.TRACKING_NUMBER.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.GREATER_OR_EQUAL.toString())) {
//			Long longValue = Long.parseLong(value);
//			return opportunityRepository.findBytrackingNumberGreaterThanEqual(longValue);
//		} else if (field.toString().equalsIgnoreCase(Field.TRACKING_NUMBER.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.LESS_OR_EQUAL.toString())) {
//			Long longValue = Long.parseLong(value);
//			return opportunityRepository.findBytrackingNumberLessThanEqual(longValue);
//
//		}
//
//		// pushCount(long)
//		if (field.toString().equalsIgnoreCase(Field.PUSH_COUNT.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.GREATER_THAN.toString())) {
//			Long longValue = Long.parseLong(value);
//			return opportunityRepository.findBypushCountGreaterThan(longValue);
//		} else if (field.toString().equalsIgnoreCase(Field.PUSH_COUNT.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.LESS_THAN.toString())) {
//			Long longValue = Long.parseLong(value);
//			return opportunityRepository.findBypushCountLessThan(longValue);
//		} else if (field.toString().equalsIgnoreCase(Field.PUSH_COUNT.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.EQUALS.toString())) {
//			Long longValue = Long.parseLong(value);
//			return opportunityRepository.findBypushCountEquals(longValue);
//		} else if (field.toString().equalsIgnoreCase(Field.PUSH_COUNT.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.NOT_EQUALS_TO.toString())) {
//			Long longValue = Long.parseLong(value);
//			return opportunityRepository.findBypushCountNot(longValue);
//		} else if (field.toString().equalsIgnoreCase(Field.PUSH_COUNT.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.GREATER_OR_EQUAL.toString())) {
//			Long longValue = Long.parseLong(value);
//			return opportunityRepository.findBypushCountGreaterThanEqual(longValue);
//		} else if (field.toString().equalsIgnoreCase(Field.PUSH_COUNT.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.LESS_OR_EQUAL.toString())) {
//			Long longValue = Long.parseLong(value);
//			return opportunityRepository.findBypushCountLessThanEqual(longValue);
//
//		}
//
//		// quantity(long)
//		if (field.toString().equalsIgnoreCase(Field.QUANTITY.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.GREATER_THAN.toString())) {
//			Long longValue = Long.parseLong(value);
//			return opportunityRepository.findByQuantityGreaterThan(longValue);
//		} else if (field.toString().equalsIgnoreCase(Field.QUANTITY.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.LESS_THAN.toString())) {
//			Long longValue = Long.parseLong(value);
//			return opportunityRepository.findByQuantityLessThan(longValue);
//		} else if (field.toString().equalsIgnoreCase(Field.QUANTITY.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.EQUALS.toString())) {
//			Long longValue = Long.parseLong(value);
//			return opportunityRepository.findByQuantityEquals(longValue);
//		} else if (field.toString().equalsIgnoreCase(Field.QUANTITY.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.NOT_EQUALS_TO.toString())) {
//			Long longValue = Long.parseLong(value);
//			return opportunityRepository.findByQuantityNot(longValue);
//		} else if (field.toString().equalsIgnoreCase(Field.QUANTITY.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.GREATER_OR_EQUAL.toString())) {
//			Long longValue = Long.parseLong(value);
//			return opportunityRepository.findByQuantityGreaterThanEqual(longValue);
//		} else if (field.toString().equalsIgnoreCase(Field.QUANTITY.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.LESS_OR_EQUAL.toString())) {
//			Long longValue = Long.parseLong(value);
//			return opportunityRepository.findByQuantityLessThanEqual(longValue);
//
//		}
//
//		// orderNumber(long)
//		if (field.toString().equalsIgnoreCase(Field.ORDER_NUMBER.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.GREATER_THAN.toString())) {
//			Long longValue = Long.parseLong(value);
//			return opportunityRepository.findByorderNumberGreaterThan(longValue);
//		} else if (field.toString().equalsIgnoreCase(Field.ORDER_NUMBER.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.LESS_THAN.toString())) {
//			Long longValue = Long.parseLong(value);
//			return opportunityRepository.findByorderNumberLessThan(longValue);
//		} else if (field.toString().equalsIgnoreCase(Field.ORDER_NUMBER.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.EQUALS.toString())) {
//			Long longValue = Long.parseLong(value);
//			return opportunityRepository.findByorderNumberEquals(longValue);
//		} else if (field.toString().equalsIgnoreCase(Field.ORDER_NUMBER.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.NOT_EQUALS_TO.toString())) {
//			Long longValue = Long.parseLong(value);
//			return opportunityRepository.findByorderNumberNot(longValue);
//		} else if (field.toString().equalsIgnoreCase(Field.ORDER_NUMBER.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.GREATER_OR_EQUAL.toString())) {
//			Long longValue = Long.parseLong(value);
//			return opportunityRepository.findByorderNumberGreaterThanEqual(longValue);
//		} else if (field.toString().equalsIgnoreCase(Field.ORDER_NUMBER.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.LESS_OR_EQUAL.toString())) {
//			Long longValue = Long.parseLong(value);
//			return opportunityRepository.findByorderNumberLessThanEqual(longValue);
//
//		}
//		// closed
//		if (field.toString().equalsIgnoreCase(Field.CLOSED.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.EQUALS.toString())) {
//			if (value.equalsIgnoreCase("true")) {
//				return opportunityRepository.findByClosed(true);
//			}
//		} else {
//			if (value.equalsIgnoreCase("false")) {
//				return opportunityRepository.findByClosed(false);
//			}
//		}
//
//		if (field.toString().equalsIgnoreCase(Field.CLOSED.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.NOT_EQUALS_TO.toString())) {
//			if (value.equalsIgnoreCase("true")) {
//				return opportunityRepository.findByClosed(true);
//			}
//		} else {
//			if (value.equalsIgnoreCase("false")) {
//				return opportunityRepository.findByClosed(false);
//			}
//		}
//		// private
//		if (field.toString().equalsIgnoreCase(Field.PRIVATE.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.EQUALS.toString())) {
//			if (value.equalsIgnoreCase("true")) {
//				return opportunityRepository.findByisPrivate(true);
//			}
//		} else {
//			if (value.equalsIgnoreCase("false")) {
//				return opportunityRepository.findByisPrivate(false);
//			}
//		}
//		if (field.toString().equalsIgnoreCase(Field.PRIVATE.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.NOT_EQUALS_TO.toString())) {
//			if (value.equalsIgnoreCase("true")) {
//				return opportunityRepository.findByisPrivate(true);
//			}
//		} else {
//			if (value.equalsIgnoreCase("false")) {
//				return opportunityRepository.findByisPrivate(false);
//			}
//		}
//
//		// won
//		if (field.toString().equalsIgnoreCase(Field.WON.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.EQUALS.toString())) {
//			if (value.equalsIgnoreCase("true")) {
//				return opportunityRepository.findByWon(true);
//			}
//		} else {
//			if (value.equalsIgnoreCase("false")) {
//				return opportunityRepository.findByWon(false);
//			}
//		}
//		if (field.toString().equalsIgnoreCase(Field.WON.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.NOT_EQUALS_TO.toString())) {
//			if (value.equalsIgnoreCase("true")) {
//				return opportunityRepository.findByWon(true);
//			}
//		} else {
//			if (value.equalsIgnoreCase("false")) {
//				return opportunityRepository.findByWon(false);
//			}
//		}
//
//		// stage
//		if (field.toString().equalsIgnoreCase(Field.STAGE.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.CONTAINS.toString())) {
//			return opportunityRepository.findByStageContainingIgnoreCase(value);
//
//		} else if (field.toString().equalsIgnoreCase(Field.STAGE.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.DOES_NOT_CONTAIN.toString())) {
//			return opportunityRepository.findByStageNotContainingIgnoreCase(value);
//		}
//		if (field.toString().equalsIgnoreCase(Field.STAGE.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.STARTS_WITH.toString())) {
//			return opportunityRepository.findByStageStartsWithIgnoreCase(value);
//		}
//
//		// leadSource
//		if (field.toString().equalsIgnoreCase(Field.LEAD_SOURCE.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.CONTAINS.toString())) {
//			return opportunityRepository.findByleadSourceContainingIgnoreCase(value);
//
//		} else if (field.toString().equalsIgnoreCase(Field.LEAD_SOURCE.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.DOES_NOT_CONTAIN.toString())) {
//			return opportunityRepository.findByleadSourceNotContainingIgnoreCase(value);
//		}
//		if (field.toString().equalsIgnoreCase(Field.LEAD_SOURCE.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.STARTS_WITH.toString())) {
//			return opportunityRepository.findByleadSourceStartsWithIgnoreCase(value);
//		}
//
//		// mainCompetitor
//		if (field.toString().equalsIgnoreCase(Field.MAIN_COMPETITOR.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.CONTAINS.toString())) {
//			return opportunityRepository.findBymainCompetitorContainingIgnoreCase(value);
//
//		} else if (field.toString().equalsIgnoreCase(Field.MAIN_COMPETITOR.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.DOES_NOT_CONTAIN.toString())) {
//			return opportunityRepository.findBymainCompetitorNotContainingIgnoreCase(value);
//		}
//		if (field.toString().equalsIgnoreCase(Field.MAIN_COMPETITOR.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.STARTS_WITH.toString())) {
//			return opportunityRepository.findBymainCompetitorStartsWithIgnoreCase(value);
//		}
//
//		// type
//		if (field.toString().equalsIgnoreCase(Field.TYPE.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.CONTAINS.toString())) {
//			return opportunityRepository.findByTypeContainingIgnoreCase(value);
//
//		} else if (field.toString().equalsIgnoreCase(Field.TYPE.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.DOES_NOT_CONTAIN.toString())) {
//			return opportunityRepository.findByTypeNotContainingIgnoreCase(value);
//		}
//		if (field.toString().equalsIgnoreCase(Field.TYPE.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.STARTS_WITH.toString())) {
//			return opportunityRepository.findByTypeStartsWithIgnoreCase(value);
//		}
//
//		// accountName
//		if (field.toString().equalsIgnoreCase(Field.ACCOUNT_NAME.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.CONTAINS.toString())) {
//			return opportunityRepository.findByAccountNameContainingIgnoreCase(value);
//		} else if (field.toString().equalsIgnoreCase(Field.ACCOUNT_NAME.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.DOES_NOT_CONTAIN.toString())) {
//			return opportunityRepository.findByAccountNameNotContainingIgnoreCase(value);
//		}
//		if (field.toString().equalsIgnoreCase(Field.ACCOUNT_NAME.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.STARTS_WITH.toString())) {
//			return opportunityRepository.findByAccountNameStartsWithIgnoreCase(value);
//		}
//
//		// createdDate
//		if (field.toString().equalsIgnoreCase(Field.CREATED_DATE.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.GREATER_THAN.toString())) {
//			try {
//				if (value.equalsIgnoreCase("yesterday")) {
//					Calendar cal = Calendar.getInstance();
//					cal.add(Calendar.DATE, -1);
//					Date createdDate = cal.getTime();
//					return opportunityRepository.findBycreatedDateGreaterThan(createdDate);
//
//				} else if (value.equalsIgnoreCase("today")) {
//					Date createdDate = new Date();
//					return opportunityRepository.findBycreatedDateGreaterThan(createdDate);
//				} else if (value.equalsIgnoreCase("tomorrow")) {
//					Calendar cal = Calendar.getInstance();
//					cal.add(Calendar.DATE, 1);
//					Date createdDate = cal.getTime();
//					return opportunityRepository.findBycreatedDateGreaterThan(createdDate);
//				} else {
//					Date createdDate = new SimpleDateFormat("yyyy-MM-dd").parse(value);
//					return opportunityRepository.findBycreatedDateGreaterThan(createdDate);
//				}
//
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			return null;
//		}
//
//		if (field.toString().equalsIgnoreCase(Field.CREATED_DATE.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.LESS_THAN.toString())) {
//			try {
//				if (value.equalsIgnoreCase("yesterday")) {
//					Calendar cal = Calendar.getInstance();
//					cal.add(Calendar.DATE, -1);
//					Date createdDate = cal.getTime();
//					return opportunityRepository.findBycreatedDateLessThan(createdDate);
//
//				} else if (value.equalsIgnoreCase("today")) {
//					Date createdDate = new Date();
//					return opportunityRepository.findBycreatedDateLessThan(createdDate);
//				} else if (value.equalsIgnoreCase("tomorrow")) {
//					Calendar cal = Calendar.getInstance();
//					cal.add(Calendar.DATE, 1);
//					Date createdDate = cal.getTime();
//					return opportunityRepository.findBycreatedDateLessThan(createdDate);
//				} else {
//					Date createdDate = new SimpleDateFormat("yyyy-MM-dd").parse(value);
//					return opportunityRepository.findBycreatedDateLessThan(createdDate);
//				}
//
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			return null;
//		}
//
//		if (field.toString().equalsIgnoreCase(Field.CREATED_DATE.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.NOT_EQUALS_TO.toString())) {
//			try {
//				if (value.equalsIgnoreCase("yesterday")) {
//					Calendar cal = Calendar.getInstance();
//					cal.add(Calendar.DATE, -1);
//					Date createdDate = cal.getTime();
//					return opportunityRepository.findBycreatedDateNot(createdDate);
//
//				} else if (value.equalsIgnoreCase("today")) {
//					Date createdDate = new Date();
//					return opportunityRepository.findBycreatedDateNot(createdDate);
//				} else if (value.equalsIgnoreCase("tomorrow")) {
//					Calendar cal = Calendar.getInstance();
//					cal.add(Calendar.DATE, 1);
//					Date createdDate = cal.getTime();
//					return opportunityRepository.findBycreatedDateNot(createdDate);
//				} else {
//					Date createdDate = new SimpleDateFormat("yyyy-MM-dd").parse(value);
//					return opportunityRepository.findBycreatedDateNot(createdDate);
//				}
//
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			return null;
//		}
//
//		if (field.toString().equalsIgnoreCase(Field.CREATED_DATE.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.EQUALS.toString())) {
//			try {
//				if (value.equalsIgnoreCase("yesterday")) {
//					Calendar cal = Calendar.getInstance();
//					cal.add(Calendar.DATE, -1);
//					Date createdDate = cal.getTime();
//					return opportunityRepository.findBycreatedDateEquals(createdDate);
//
//				} else if (value.equalsIgnoreCase("today")) {
//					Date createdDate = new Date();
//					return opportunityRepository.findBycreatedDateEquals(createdDate);
//				} else if (value.equalsIgnoreCase("tomorrow")) {
//					Calendar cal = Calendar.getInstance();
//					cal.add(Calendar.DATE, 1);
//					Date createdDate = cal.getTime();
//					return opportunityRepository.findBycreatedDateEquals(createdDate);
//				} else {
//					Date createdDate = new SimpleDateFormat("yyyy-MM-dd").parse(value);
//					return opportunityRepository.findBycreatedDateEquals(createdDate);
//				}
//
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			return null;
//		}
//
//		if (field.toString().equalsIgnoreCase(Field.CREATED_DATE.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.GREATER_OR_EQUAL.toString())) {
//			try {
//				if (value.equalsIgnoreCase("yesterday")) {
//					Calendar cal = Calendar.getInstance();
//					cal.add(Calendar.DATE, -1);
//					Date createdDate = cal.getTime();
//					return opportunityRepository.findBycreatedDateGreaterThanEqual(createdDate);
//
//				} else if (value.equalsIgnoreCase("today")) {
//					Date createdDate = new Date();
//					return opportunityRepository.findBycreatedDateGreaterThanEqual(createdDate);
//				} else if (value.equalsIgnoreCase("tomorrow")) {
//					Calendar cal = Calendar.getInstance();
//					cal.add(Calendar.DATE, 1);
//					Date createdDate = cal.getTime();
//					return opportunityRepository.findBycreatedDateGreaterThanEqual(createdDate);
//				} else {
//					Date createdDate = new SimpleDateFormat("yyyy-MM-dd").parse(value);
//					return opportunityRepository.findBycreatedDateGreaterThanEqual(createdDate);
//				}
//
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			return null;
//		}
//
//		if (field.toString().equalsIgnoreCase(Field.CREATED_DATE.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.LESS_OR_EQUAL.toString())) {
//			try {
//				if (value.equalsIgnoreCase("yesterday")) {
//					Calendar cal = Calendar.getInstance();
//					cal.add(Calendar.DATE, -1);
//					Date createdDate = cal.getTime();
//					return opportunityRepository.findBycreatedDateLessThanEqual(createdDate);
//
//				} else if (value.equalsIgnoreCase("today")) {
//					Date createdDate = new Date();
//					return opportunityRepository.findBycreatedDateLessThanEqual(createdDate);
//				} else if (value.equalsIgnoreCase("tomorrow")) {
//					Calendar cal = Calendar.getInstance();
//					cal.add(Calendar.DATE, 1);
//					Date createdDate = cal.getTime();
//					return opportunityRepository.findBycreatedDateLessThanEqual(createdDate);
//				} else {
//					Date createdDate = new SimpleDateFormat("yyyy-MM-dd").parse(value);
//					return opportunityRepository.findBycreatedDateLessThanEqual(createdDate);
//				}
//
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			return null;
//		}
//		// closeDate
//		if (field.toString().equalsIgnoreCase(Field.CLOSE_DATE.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.GREATER_THAN.toString())) {
//			try {
//				if (value.equalsIgnoreCase("yesterday")) {
//					Calendar cal = Calendar.getInstance();
//					cal.add(Calendar.DATE, -1);
//					Date closeDate = cal.getTime();
//					return opportunityRepository.findBycloseDateGreaterThan(closeDate);
//
//				} else if (value.equalsIgnoreCase("today")) {
//					Date closeDate = new Date();
//					return opportunityRepository.findBycloseDateGreaterThan(closeDate);
//				} else if (value.equalsIgnoreCase("tomorrow")) {
//					Calendar cal = Calendar.getInstance();
//					cal.add(Calendar.DATE, 1);
//					Date closeDate = cal.getTime();
//					return opportunityRepository.findBycloseDateGreaterThan(closeDate);
//				} else {
//					Date closeDate = new SimpleDateFormat("yyyy-MM-dd").parse(value);
//					return opportunityRepository.findBycloseDateGreaterThan(closeDate);
//				}
//
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			return null;
//		}
//
//		if (field.toString().equalsIgnoreCase(Field.CLOSE_DATE.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.LESS_THAN.toString())) {
//			try {
//				if (value.equalsIgnoreCase("yesterday")) {
//					Calendar cal = Calendar.getInstance();
//					cal.add(Calendar.DATE, -1);
//					Date closeDate = cal.getTime();
//					return opportunityRepository.findBycloseDateLessThan(closeDate);
//
//				} else if (value.equalsIgnoreCase("today")) {
//					Date closeDate = new Date();
//					return opportunityRepository.findBycloseDateLessThan(closeDate);
//				} else if (value.equalsIgnoreCase("tomorrow")) {
//					Calendar cal = Calendar.getInstance();
//					cal.add(Calendar.DATE, 1);
//					Date closeDate = cal.getTime();
//					return opportunityRepository.findBycloseDateLessThan(closeDate);
//				} else {
//					Date closeDate = new SimpleDateFormat("yyyy-MM-dd").parse(value);
//					return opportunityRepository.findBycloseDateLessThan(closeDate);
//				}
//
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			return null;
//		}
//
//		if (field.toString().equalsIgnoreCase(Field.CLOSE_DATE.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.NOT_EQUALS_TO.toString())) {
//			try {
//				if (value.equalsIgnoreCase("yesterday")) {
//					Calendar cal = Calendar.getInstance();
//					cal.add(Calendar.DATE, -1);
//					Date closeDate = cal.getTime();
//					return opportunityRepository.findBycloseDateNot(closeDate);
//
//				} else if (value.equalsIgnoreCase("today")) {
//					Date closeDate = new Date();
//					return opportunityRepository.findBycloseDateNot(closeDate);
//				} else if (value.equalsIgnoreCase("tomorrow")) {
//					Calendar cal = Calendar.getInstance();
//					cal.add(Calendar.DATE, 1);
//					Date closeDate = cal.getTime();
//					return opportunityRepository.findBycloseDateNot(closeDate);
//				} else {
//					Date closeDate = new SimpleDateFormat("yyyy-MM-dd").parse(value);
//					return opportunityRepository.findBycloseDateNot(closeDate);
//				}
//
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			return null;
//		}
//
//		if (field.toString().equalsIgnoreCase(Field.CLOSE_DATE.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.EQUALS.toString())) {
//			try {
//				if (value.equalsIgnoreCase("yesterday")) {
//					Calendar cal = Calendar.getInstance();
//					cal.add(Calendar.DATE, -1);
//					Date closeDate = cal.getTime();
//					return opportunityRepository.findBycloseDateEquals(closeDate);
//
//				} else if (value.equalsIgnoreCase("today")) {
//					Date closeDate = new Date();
//					return opportunityRepository.findBycloseDateEquals(closeDate);
//				} else if (value.equalsIgnoreCase("tomorrow")) {
//					Calendar cal = Calendar.getInstance();
//					cal.add(Calendar.DATE, 1);
//					Date closeDate = cal.getTime();
//					return opportunityRepository.findBycloseDateEquals(closeDate);
//				} else {
//					Date closeDate = new SimpleDateFormat("yyyy-MM-dd").parse(value);
//					return opportunityRepository.findBycloseDateEquals(closeDate);
//				}
//
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			return null;
//		}
//
//		if (field.toString().equalsIgnoreCase(Field.CLOSE_DATE.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.GREATER_OR_EQUAL.toString())) {
//			try {
//				if (value.equalsIgnoreCase("yesterday")) {
//					Calendar cal = Calendar.getInstance();
//					cal.add(Calendar.DATE, -1);
//					Date closeDate = cal.getTime();
//					return opportunityRepository.findBycloseDateGreaterThanEqual(closeDate);
//
//				} else if (value.equalsIgnoreCase("today")) {
//					Date closeDate = new Date();
//					return opportunityRepository.findBycloseDateGreaterThanEqual(closeDate);
//				} else if (value.equalsIgnoreCase("tomorrow")) {
//					Calendar cal = Calendar.getInstance();
//					cal.add(Calendar.DATE, 1);
//					Date closeDate = cal.getTime();
//					return opportunityRepository.findBycloseDateGreaterThanEqual(closeDate);
//				} else {
//					Date closeDate = new SimpleDateFormat("yyyy-MM-dd").parse(value);
//					return opportunityRepository.findBycloseDateGreaterThanEqual(closeDate);
//				}
//
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			return null;
//		}
//
//		if (field.toString().equalsIgnoreCase(Field.CLOSE_DATE.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.LESS_OR_EQUAL.toString())) {
//			try {
//				if (value.equalsIgnoreCase("yesterday")) {
//					Calendar cal = Calendar.getInstance();
//					cal.add(Calendar.DATE, -1);
//					Date closeDate = cal.getTime();
//					return opportunityRepository.findBycloseDateLessThanEqual(closeDate);
//
//				} else if (value.equalsIgnoreCase("today")) {
//					Date closeDate = new Date();
//					return opportunityRepository.findBycloseDateLessThanEqual(closeDate);
//				} else if (value.equalsIgnoreCase("tomorrow")) {
//					Calendar cal = Calendar.getInstance();
//					cal.add(Calendar.DATE, 1);
//					Date closeDate = cal.getTime();
//					return opportunityRepository.findBycloseDateLessThanEqual(closeDate);
//				} else {
//					Date closeDate = new SimpleDateFormat("yyyy-MM-dd").parse(value);
//					return opportunityRepository.findBycloseDateLessThanEqual(closeDate);
//				}
//
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			return null;
//		}
//
//		// lastModifiedDate
//		if (field.toString().equalsIgnoreCase(Field.LAST_MODIFIED_DATE.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.GREATER_THAN.toString())) {
//			try {
//				if (value.equalsIgnoreCase("yesterday")) {
//					Calendar cal = Calendar.getInstance();
//					cal.add(Calendar.DATE, -1);
//					Date lastModifiedDate = cal.getTime();
//					return opportunityRepository.findBylastModifiedDateGreaterThan(lastModifiedDate);
//
//				} else if (value.equalsIgnoreCase("today")) {
//					Date lastModifiedDate = new Date();
//					return opportunityRepository.findBylastModifiedDateGreaterThan(lastModifiedDate);
//				} else if (value.equalsIgnoreCase("tomorrow")) {
//					Calendar cal = Calendar.getInstance();
//					cal.add(Calendar.DATE, 1);
//					Date lastModifiedDate = cal.getTime();
//					return opportunityRepository.findBylastModifiedDateGreaterThan(lastModifiedDate);
//				} else {
//					Date lastModifiedDate = new SimpleDateFormat("yyyy-MM-dd").parse(value);
//					return opportunityRepository.findBylastModifiedDateGreaterThan(lastModifiedDate);
//				}
//
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			return null;
//		}
//
//		if (field.toString().equalsIgnoreCase(Field.LAST_MODIFIED_DATE.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.LESS_THAN.toString())) {
//			try {
//				if (value.equalsIgnoreCase("yesterday")) {
//					Calendar cal = Calendar.getInstance();
//					cal.add(Calendar.DATE, -1);
//					Date lastModifiedDate = cal.getTime();
//					return opportunityRepository.findBylastModifiedDateLessThan(lastModifiedDate);
//
//				} else if (value.equalsIgnoreCase("today")) {
//					Date lastModifiedDate = new Date();
//					return opportunityRepository.findBylastModifiedDateLessThan(lastModifiedDate);
//				} else if (value.equalsIgnoreCase("tomorrow")) {
//					Calendar cal = Calendar.getInstance();
//					cal.add(Calendar.DATE, 1);
//					Date lastModifiedDate = cal.getTime();
//					return opportunityRepository.findBylastModifiedDateLessThan(lastModifiedDate);
//				} else {
//					Date lastModifiedDate = new SimpleDateFormat("yyyy-MM-dd").parse(value);
//					return opportunityRepository.findBylastModifiedDateLessThan(lastModifiedDate);
//				}
//
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			return null;
//		}
//
//		if (field.toString().equalsIgnoreCase(Field.LAST_MODIFIED_DATE.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.NOT_EQUALS_TO.toString())) {
//			try {
//				if (value.equalsIgnoreCase("yesterday")) {
//					Calendar cal = Calendar.getInstance();
//					cal.add(Calendar.DATE, -1);
//					Date lastModifiedDate = cal.getTime();
//					return opportunityRepository.findBylastModifiedDateNot(lastModifiedDate);
//
//				} else if (value.equalsIgnoreCase("today")) {
//					Date lastModifiedDate = new Date();
//					return opportunityRepository.findBylastModifiedDateNot(lastModifiedDate);
//				} else if (value.equalsIgnoreCase("tomorrow")) {
//					Calendar cal = Calendar.getInstance();
//					cal.add(Calendar.DATE, 1);
//					Date lastModifiedDate = cal.getTime();
//					return opportunityRepository.findBylastModifiedDateNot(lastModifiedDate);
//				} else {
//					Date lastModifiedDate = new SimpleDateFormat("yyyy-MM-dd").parse(value);
//					return opportunityRepository.findBylastModifiedDateNot(lastModifiedDate);
//				}
//
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			return null;
//		}
//
//		if (field.toString().equalsIgnoreCase(Field.LAST_MODIFIED_DATE.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.EQUALS.toString())) {
//			try {
//				if (value.equalsIgnoreCase("yesterday")) {
//					Calendar cal = Calendar.getInstance();
//					cal.add(Calendar.DATE, -1);
//					Date lastModifiedDate = cal.getTime();
//					return opportunityRepository.findBylastModifiedDateEquals(lastModifiedDate);
//
//				} else if (value.equalsIgnoreCase("today")) {
//					Date lastModifiedDate = new Date();
//					return opportunityRepository.findBylastModifiedDateEquals(lastModifiedDate);
//				} else if (value.equalsIgnoreCase("tomorrow")) {
//					Calendar cal = Calendar.getInstance();
//					cal.add(Calendar.DATE, 1);
//					Date lastModifiedDate = cal.getTime();
//					return opportunityRepository.findBylastModifiedDateEquals(lastModifiedDate);
//				} else {
//					Date lastModifiedDate = new SimpleDateFormat("yyyy-MM-dd").parse(value);
//					return opportunityRepository.findBylastModifiedDateEquals(lastModifiedDate);
//				}
//
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			return null;
//		}
//
//		if (field.toString().equalsIgnoreCase(Field.LAST_MODIFIED_DATE.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.GREATER_OR_EQUAL.toString())) {
//			try {
//				if (value.equalsIgnoreCase("yesterday")) {
//					Calendar cal = Calendar.getInstance();
//					cal.add(Calendar.DATE, -1);
//					Date lastModifiedDate = cal.getTime();
//					return opportunityRepository.findBylastModifiedDateGreaterThanEqual(lastModifiedDate);
//
//				} else if (value.equalsIgnoreCase("today")) {
//					Date lastModifiedDate = new Date();
//					return opportunityRepository.findBylastModifiedDateGreaterThanEqual(lastModifiedDate);
//				} else if (value.equalsIgnoreCase("tomorrow")) {
//					Calendar cal = Calendar.getInstance();
//					cal.add(Calendar.DATE, 1);
//					Date lastModifiedDate = cal.getTime();
//					return opportunityRepository.findBylastModifiedDateGreaterThanEqual(lastModifiedDate);
//				} else {
//					Date lastModifiedDate = new SimpleDateFormat("yyyy-MM-dd").parse(value);
//					return opportunityRepository.findBylastModifiedDateGreaterThanEqual(lastModifiedDate);
//				}
//
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			return null;
//		}
//
//		if (field.toString().equalsIgnoreCase(Field.LAST_MODIFIED_DATE.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.LESS_OR_EQUAL.toString())) {
//			try {
//				if (value.equalsIgnoreCase("yesterday")) {
//					Calendar cal = Calendar.getInstance();
//					cal.add(Calendar.DATE, -1);
//					Date lastModifiedDate = cal.getTime();
//					return opportunityRepository.findBylastModifiedDateLessThanEqual(lastModifiedDate);
//
//				} else if (value.equalsIgnoreCase("today")) {
//					Date lastModifiedDate = new Date();
//					return opportunityRepository.findBylastModifiedDateLessThanEqual(lastModifiedDate);
//				} else if (value.equalsIgnoreCase("tomorrow")) {
//					Calendar cal = Calendar.getInstance();
//					cal.add(Calendar.DATE, 1);
//					Date lastModifiedDate = cal.getTime();
//					return opportunityRepository.findBylastModifiedDateLessThanEqual(lastModifiedDate);
//				} else {
//					Date lastModifiedDate = new SimpleDateFormat("yyyy-MM-dd").parse(value);
//					return opportunityRepository.findBylastModifiedDateLessThanEqual(lastModifiedDate);
//				}
//
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			return null;
//		}
//
//		// lastStageChangeDate
//		if (field.toString().equalsIgnoreCase(Field.LAST_STAGE_CHANGE_DATE.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.GREATER_THAN.toString())) {
//			try {
//				if (value.equalsIgnoreCase("yesterday")) {
//					Calendar cal = Calendar.getInstance();
//					cal.add(Calendar.DATE, -1);
//					Date lastStageChangeDate = cal.getTime();
//					return opportunityRepository.findBylastStageChangeDateGreaterThan(lastStageChangeDate);
//
//				} else if (value.equalsIgnoreCase("today")) {
//					Date lastStageChangeDate = new Date();
//					return opportunityRepository.findBylastStageChangeDateGreaterThan(lastStageChangeDate);
//				} else if (value.equalsIgnoreCase("tomorrow")) {
//					Calendar cal = Calendar.getInstance();
//					cal.add(Calendar.DATE, 1);
//					Date lastStageChangeDate = cal.getTime();
//					return opportunityRepository.findBylastStageChangeDateGreaterThan(lastStageChangeDate);
//				} else {
//					Date lastStageChangeDate = new SimpleDateFormat("yyyy-MM-dd").parse(value);
//					return opportunityRepository.findBylastStageChangeDateGreaterThan(lastStageChangeDate);
//				}
//
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			return null;
//		}
//
//		if (field.toString().equalsIgnoreCase(Field.LAST_STAGE_CHANGE_DATE.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.LESS_THAN.toString())) {
//			try {
//				if (value.equalsIgnoreCase("yesterday")) {
//					Calendar cal = Calendar.getInstance();
//					cal.add(Calendar.DATE, -1);
//					Date lastStageChangeDate = cal.getTime();
//					return opportunityRepository.findBylastStageChangeDateLessThan(lastStageChangeDate);
//
//				} else if (value.equalsIgnoreCase("today")) {
//					Date lastStageChangeDate = new Date();
//					return opportunityRepository.findBylastStageChangeDateLessThan(lastStageChangeDate);
//				} else if (value.equalsIgnoreCase("tomorrow")) {
//					Calendar cal = Calendar.getInstance();
//					cal.add(Calendar.DATE, 1);
//					Date lastStageChangeDate = cal.getTime();
//					return opportunityRepository.findBylastStageChangeDateLessThan(lastStageChangeDate);
//				} else {
//					Date lastStageChangeDate = new SimpleDateFormat("yyyy-MM-dd").parse(value);
//					return opportunityRepository.findBylastStageChangeDateLessThan(lastStageChangeDate);
//				}
//
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			return null;
//		}
//
//		if (field.toString().equalsIgnoreCase(Field.LAST_STAGE_CHANGE_DATE.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.NOT_EQUALS_TO.toString())) {
//			try {
//				if (value.equalsIgnoreCase("yesterday")) {
//					Calendar cal = Calendar.getInstance();
//					cal.add(Calendar.DATE, -1);
//					Date lastStageChangeDate = cal.getTime();
//					return opportunityRepository.findBylastStageChangeDateNot(lastStageChangeDate);
//
//				} else if (value.equalsIgnoreCase("today")) {
//					Date lastStageChangeDate = new Date();
//					return opportunityRepository.findBylastStageChangeDateNot(lastStageChangeDate);
//				} else if (value.equalsIgnoreCase("tomorrow")) {
//					Calendar cal = Calendar.getInstance();
//					cal.add(Calendar.DATE, 1);
//					Date lastStageChangeDate = cal.getTime();
//					return opportunityRepository.findBylastStageChangeDateNot(lastStageChangeDate);
//				} else {
//					Date lastStageChangeDate = new SimpleDateFormat("yyyy-MM-dd").parse(value);
//					return opportunityRepository.findBylastStageChangeDateNot(lastStageChangeDate);
//				}
//
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			return null;
//		}
//
//		if (field.toString().equalsIgnoreCase(Field.LAST_STAGE_CHANGE_DATE.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.EQUALS.toString())) {
//			try {
//				if (value.equalsIgnoreCase("yesterday")) {
//					Calendar cal = Calendar.getInstance();
//					cal.add(Calendar.DATE, -1);
//					Date lastStageChangeDate = cal.getTime();
//					return opportunityRepository.findBylastStageChangeDateEquals(lastStageChangeDate);
//
//				} else if (value.equalsIgnoreCase("today")) {
//					Date lastStageChangeDate = new Date();
//					return opportunityRepository.findBylastStageChangeDateEquals(lastStageChangeDate);
//				} else if (value.equalsIgnoreCase("tomorrow")) {
//					Calendar cal = Calendar.getInstance();
//					cal.add(Calendar.DATE, 1);
//					Date lastStageChangeDate = cal.getTime();
//					return opportunityRepository.findBylastStageChangeDateEquals(lastStageChangeDate);
//				} else {
//					Date lastStageChangeDate = new SimpleDateFormat("yyyy-MM-dd").parse(value);
//					return opportunityRepository.findBylastStageChangeDateEquals(lastStageChangeDate);
//				}
//
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			return null;
//		}
//
//		if (field.toString().equalsIgnoreCase(Field.LAST_STAGE_CHANGE_DATE.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.GREATER_OR_EQUAL.toString())) {
//			try {
//				if (value.equalsIgnoreCase("yesterday")) {
//					Calendar cal = Calendar.getInstance();
//					cal.add(Calendar.DATE, -1);
//					Date lastStageChangeDate = cal.getTime();
//					return opportunityRepository.findBylastStageChangeDateGreaterThanEqual(lastStageChangeDate);
//
//				} else if (value.equalsIgnoreCase("today")) {
//					Date lastStageChangeDate = new Date();
//					return opportunityRepository.findBylastStageChangeDateGreaterThanEqual(lastStageChangeDate);
//				} else if (value.equalsIgnoreCase("tomorrow")) {
//					Calendar cal = Calendar.getInstance();
//					cal.add(Calendar.DATE, 1);
//					Date lastStageChangeDate = cal.getTime();
//					return opportunityRepository.findBylastStageChangeDateGreaterThanEqual(lastStageChangeDate);
//				} else {
//					Date lastStageChangeDate = new SimpleDateFormat("yyyy-MM-dd").parse(value);
//					return opportunityRepository.findBylastStageChangeDateGreaterThanEqual(lastStageChangeDate);
//				}
//
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			return null;
//		}
//
//		if (field.toString().equalsIgnoreCase(Field.LAST_STAGE_CHANGE_DATE.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.LESS_OR_EQUAL.toString())) {
//			try {
//				if (value.equalsIgnoreCase("yesterday")) {
//					Calendar cal = Calendar.getInstance();
//					cal.add(Calendar.DATE, -1);
//					Date lastStageChangeDate = cal.getTime();
//					return opportunityRepository.findBylastStageChangeDateLessThanEqual(lastStageChangeDate);
//
//				} else if (value.equalsIgnoreCase("today")) {
//					Date lastStageChangeDate = new Date();
//					return opportunityRepository.findBylastStageChangeDateLessThanEqual(lastStageChangeDate);
//				} else if (value.equalsIgnoreCase("tomorrow")) {
//					Calendar cal = Calendar.getInstance();
//					cal.add(Calendar.DATE, 1);
//					Date lastStageChangeDate = cal.getTime();
//					return opportunityRepository.findBylastStageChangeDateLessThanEqual(lastStageChangeDate);
//				} else {
//					Date lastStageChangeDate = new SimpleDateFormat("yyyy-MM-dd").parse(value);
//					return opportunityRepository.findBylastStageChangeDateLessThanEqual(lastStageChangeDate);
//				}
//
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			return null;
//		}
//
//		// status //delivery installation status
//		if (field.toString().equalsIgnoreCase(Field.DELIVERY_INSTALLATION_STATUS.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.CONTAINS.toString())) {
//			return opportunityRepository.findByStatusContainingIgnoreCase(value);
//		}
//		if (field.toString().equalsIgnoreCase(Field.DELIVERY_INSTALLATION_STATUS.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.DOES_NOT_CONTAIN.toString())) {
//			return opportunityRepository.findByStatusNotContainingIgnoreCase(value);
//		}
//		if (field.toString().equalsIgnoreCase(Field.DELIVERY_INSTALLATION_STATUS.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.STARTS_WITH.toString())) {
//			return opportunityRepository.findByStatusStartsWithIgnoreCase(value);
//		}
//
//		// forecastCategoryName
//		if (field.toString().equalsIgnoreCase(Field.FORECAST_CATEGORY.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.CONTAINS.toString())) {
//			return opportunityRepository.findByforecastCategoryNameContainingIgnoreCase(value);
//		}
//		if (field.toString().equalsIgnoreCase(Field.FORECAST_CATEGORY.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.DOES_NOT_CONTAIN.toString())) {
//			return opportunityRepository.findByforecastCategoryNameNotContainingIgnoreCase(value);
//		}
//		if (field.toString().equalsIgnoreCase(Field.FORECAST_CATEGORY.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.STARTS_WITH.toString())) {
//			return opportunityRepository.findByforecastCategoryNameStartsWithIgnoreCase(value);
//		}
//
//		// nextStep
//		if (field.toString().equalsIgnoreCase(Field.NEXT_STEP.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.CONTAINS.toString())) {
//			return opportunityRepository.findBynextStepContainingIgnoreCase(value);
//		}
//		if (field.toString().equalsIgnoreCase(Field.NEXT_STEP.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.DOES_NOT_CONTAIN.toString())) {
//			return opportunityRepository.findBynextStepNotContainingIgnoreCase(value);
//		}
//		if (field.toString().equalsIgnoreCase(Field.NEXT_STEP.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.STARTS_WITH.toString())) {
//			return opportunityRepository.findBynextStepStartsWithIgnoreCase(value);
//		}
//
//		// name opportunity name
//		if (field.toString().equalsIgnoreCase(Field.OPPORTUNITY_NAME.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.CONTAINS.toString())) {
//			return opportunityRepository.findByNameContainingIgnoreCase(value);
//		}
//		if (field.toString().equalsIgnoreCase(Field.OPPORTUNITY_NAME.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.DOES_NOT_CONTAIN.toString())) {
//			return opportunityRepository.findByNameNotContainingIgnoreCase(value);
//		}
//		if (field.toString().equalsIgnoreCase(Field.OPPORTUNITY_NAME.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.STARTS_WITH.toString())) {
//			return opportunityRepository.findByNameStartsWithIgnoreCase(value);
//		}
//
//		// ownerFirstName
//		if (field.toString().equalsIgnoreCase(Field.OWNER_FIRST_NAME.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.CONTAINS.toString())) {
//			return opportunityRepository.findByownerFirstNameContainingIgnoreCase(value);
//		}
//		if (field.toString().equalsIgnoreCase(Field.OWNER_FIRST_NAME.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.DOES_NOT_CONTAIN.toString())) {
//			return opportunityRepository.findByownerFirstNameNotContainingIgnoreCase(value);
//		}
//		if (field.toString().equalsIgnoreCase(Field.OWNER_FIRST_NAME.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.STARTS_WITH.toString())) {
//			return opportunityRepository.findByownerFirstNameStartsWithIgnoreCase(value);
//		}
//
//		// ownerLastName
//		if (field.toString().equalsIgnoreCase(Field.OWNER_LAST_NAME.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.CONTAINS.toString())) {
//			return opportunityRepository.findByownerLastNameContainingIgnoreCase(value);
//		}
//		if (field.toString().equalsIgnoreCase(Field.OWNER_LAST_NAME.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.DOES_NOT_CONTAIN.toString())) {
//			return opportunityRepository.findByownerLastNameNotContainingIgnoreCase(value);
//		}
//		if (field.toString().equalsIgnoreCase(Field.OWNER_LAST_NAME.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.STARTS_WITH.toString())) {
//			return opportunityRepository.findByownerLastNameStartsWithIgnoreCase(value);
//		}
//
//		// ownerFullName
//		if (field.toString().equalsIgnoreCase(Field.OWNER_FULL_NAME.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.CONTAINS.toString())) {
//			return opportunityRepository.findByownerFullNameContainingIgnoreCase(value);
//		}
//		if (field.toString().equalsIgnoreCase(Field.OWNER_FULL_NAME.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.DOES_NOT_CONTAIN.toString())) {
//			return opportunityRepository.findByownerFullNameNotContainingIgnoreCase(value);
//		}
//		if (field.toString().equalsIgnoreCase(Field.OWNER_FULL_NAME.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.STARTS_WITH.toString())) {
//			return opportunityRepository.findByownerFullNameStartsWithIgnoreCase(value);
//		}
//
//		// opportunityOwnerAlias
//		if (field.toString().equalsIgnoreCase(Field.OPPORTUNITY_OWNER_ALIAS.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.CONTAINS.toString())) {
//			return opportunityRepository.findByopportunityOwnerAliasContainingIgnoreCase(value);
//		}
//		if (field.toString().equalsIgnoreCase(Field.OPPORTUNITY_OWNER_ALIAS.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.DOES_NOT_CONTAIN.toString())) {
//			return opportunityRepository.findByopportunityOwnerAliasNotContainingIgnoreCase(value);
//		}
//		if (field.toString().equalsIgnoreCase(Field.OPPORTUNITY_OWNER_ALIAS.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.STARTS_WITH.toString())) {
//			return opportunityRepository.findByopportunityOwnerAliasStartsWithIgnoreCase(value);
//		}
//
//		// createdByAlias
//		if (field.toString().equalsIgnoreCase(Field.CREATED_BY_ALIAS.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.CONTAINS.toString())) {
//			return opportunityRepository.findBycreatedByAliasContainingIgnoreCase(value);
//		}
//		if (field.toString().equalsIgnoreCase(Field.CREATED_BY_ALIAS.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.DOES_NOT_CONTAIN.toString())) {
//			return opportunityRepository.findBycreatedByAliasNotContainingIgnoreCase(value);
//		}
//		if (field.toString().equalsIgnoreCase(Field.CREATED_BY_ALIAS.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.STARTS_WITH.toString())) {
//			return opportunityRepository.findBycreatedByAliasStartsWithIgnoreCase(value);
//		}
//
//		// lastModifiedByAlias
//		if (field.toString().equalsIgnoreCase(Field.LAST_MODIFIED_BY_ALIAS.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.CONTAINS.toString())) {
//			return opportunityRepository.findBylastModifiedByAliasContainingIgnoreCase(value);
//		}
//		if (field.toString().equalsIgnoreCase(Field.LAST_MODIFIED_BY_ALIAS.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.DOES_NOT_CONTAIN.toString())) {
//			return opportunityRepository.findBylastModifiedByAliasNotContainingIgnoreCase(value);
//		}
//		if (field.toString().equalsIgnoreCase(Field.LAST_MODIFIED_BY_ALIAS.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.STARTS_WITH.toString())) {
//			return opportunityRepository.findBylastModifiedByAliasStartsWithIgnoreCase(value);
//		}
//
//		// currentGenerators
//		if (field.toString().equalsIgnoreCase(Field.CURRENT_GENERATOR.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.CONTAINS.toString())) {
//			return opportunityRepository.findBycurrentGeneratorsContainingIgnoreCase(value);
//		}
//		if (field.toString().equalsIgnoreCase(Field.CURRENT_GENERATOR.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.DOES_NOT_CONTAIN.toString())) {
//			return opportunityRepository.findBycurrentGeneratorsNotContainingIgnoreCase(value);
//		}
//		if (field.toString().equalsIgnoreCase(Field.CURRENT_GENERATOR.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.STARTS_WITH.toString())) {
//			return opportunityRepository.findBycurrentGeneratorsStartsWithIgnoreCase(value);
//		}
//
//		// topics
//		if (field.toString().equalsIgnoreCase(Field.TOPICS.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.INCLUDES.toString())) {
//			return opportunityRepository.findByTopicsContainingIgnoreCase(value);
//		}
//		if (field.toString().equalsIgnoreCase(Field.TOPICS.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.EXCLUDES.toString())) {
//			return opportunityRepository.findByTopicsNotContainingIgnoreCase(value);
//		}
//
//		// accountSite
//		if (field.toString().equalsIgnoreCase(Field.ACCOUNT_SITE.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.CONTAINS.toString())) {
//			return opportunityRepository.findByAccountSiteContainingIgnoreCase(value);
//		}
//		if (field.toString().equalsIgnoreCase(Field.ACCOUNT_SITE.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.DOES_NOT_CONTAIN.toString())) {
//			return opportunityRepository.findByAccountSiteNotContainingIgnoreCase(value);
//		}
//		if (field.toString().equalsIgnoreCase(Field.ACCOUNT_SITE.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.STARTS_WITH.toString())) {
//			return opportunityRepository.findByAccountSiteStartsWithIgnoreCase(value);
//		}
//
//		// lastActivity
//		if (field.toString().equalsIgnoreCase(Field.LAST_ACTIVITY.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.CONTAINS.toString())) {
//			return opportunityRepository.findBylastActivityContainingIgnoreCase(value);
//		}
//		if (field.toString().equalsIgnoreCase(Field.LAST_ACTIVITY.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.DOES_NOT_CONTAIN.toString())) {
//			return opportunityRepository.findBylastActivityNotContainingIgnoreCase(value);
//		}
//		if (field.toString().equalsIgnoreCase(Field.LAST_ACTIVITY.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.STARTS_WITH.toString())) {
//			return opportunityRepository.findBylastActivityStartsWithIgnoreCase(value);
//		}
//
//		// probability
//		if (field.toString().equalsIgnoreCase(Field.PROBABILITY.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.GREATER_THAN.toString())) {
//			return opportunityRepository.findByProbabilityGreaterThan(value);
//		} else if (field.toString().equalsIgnoreCase(Field.PROBABILITY.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.LESS_THAN.toString())) {
//			return opportunityRepository.findByProbabilityLessThan(value);
//		} else if (field.toString().equalsIgnoreCase(Field.PROBABILITY.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.EQUALS.toString())) {
//			return opportunityRepository.findByProbabilityEquals(value);
//		} else if (field.toString().equalsIgnoreCase(Field.PROBABILITY.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.NOT_EQUALS_TO.toString())) {
//			return opportunityRepository.findByProbabilityNot(value);
//		} else if (field.toString().equalsIgnoreCase(Field.PROBABILITY.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.GREATER_OR_EQUAL.toString())) {
//			return opportunityRepository.findByProbabilityGreaterThanEqual(value);
//		} else if (field.toString().equalsIgnoreCase(Field.PROBABILITY.toString())
//				&& operator.toString().equalsIgnoreCase(Operator.LESS_OR_EQUAL.toString())) {
//			return opportunityRepository.findByProbabilityLessThanEqual(value);
//
//		}
//
//		return null;
//	}

	public Map<String, Object> findByFilterPagination(String field, String operator, String value, Request request) {

		List<Opportunity> listOpp = new ArrayList<>();

		if (field.toString().equalsIgnoreCase(Field.AMOUNT.toString())
				&& operator.toString().equalsIgnoreCase(Operator.GREATER_THAN.toString())) {
			Long longValue = Long.parseLong(value);
			listOpp = opportunityRepository.findByAmountGreaterThan(longValue);
		} else if (field.toString().equalsIgnoreCase(Field.AMOUNT.toString())
				&& operator.toString().equalsIgnoreCase(Operator.LESS_THAN.toString())) {
			Long longValue = Long.parseLong(value);
			listOpp = opportunityRepository.findByAmountLessThan(longValue);
		} else if (field.toString().equalsIgnoreCase(Field.AMOUNT.toString())
				&& operator.toString().equalsIgnoreCase(Operator.EQUALS.toString())) {
			Long longValue = Long.parseLong(value);
			listOpp = opportunityRepository.findByAmountEquals(longValue);
		} else if (field.toString().equalsIgnoreCase(Field.AMOUNT.toString())
				&& operator.toString().equalsIgnoreCase(Operator.NOT_EQUALS_TO.toString())) {
			Long longValue = Long.parseLong(value);
			listOpp = opportunityRepository.findByAmountNot(longValue);
		} else if (field.toString().equalsIgnoreCase(Field.AMOUNT.toString())
				&& operator.toString().equalsIgnoreCase(Operator.GREATER_OR_EQUAL.toString())) {
			Long longValue = Long.parseLong(value);
			listOpp = opportunityRepository.findByAmountGreaterThanEqual(longValue);
		} else if (field.toString().equalsIgnoreCase(Field.AMOUNT.toString())
				&& operator.toString().equalsIgnoreCase(Operator.LESS_OR_EQUAL.toString())) {
			Long longValue = Long.parseLong(value);
			listOpp = opportunityRepository.findByAmountLessThanEqual(longValue);

		}

		else if (field.toString().equalsIgnoreCase(Field.EXPECTED_REVENUE.toString())
				&& operator.toString().equalsIgnoreCase(Operator.GREATER_THAN.toString())) {
			Long longValue = Long.parseLong(value);
			listOpp = opportunityRepository.findByExpectedRevenueGreaterThan(longValue);
		} else if (field.toString().equalsIgnoreCase(Field.EXPECTED_REVENUE.toString())
				&& operator.toString().equalsIgnoreCase(Operator.LESS_THAN.toString())) {
			Long longValue = Long.parseLong(value);
			listOpp = opportunityRepository.findByExpectedRevenueLessThan(longValue);
		} else if (field.toString().equalsIgnoreCase(Field.EXPECTED_REVENUE.toString())
				&& operator.toString().equalsIgnoreCase(Operator.NOT_EQUALS_TO.toString())) {
			Long longValue = Long.parseLong(value);
			listOpp = opportunityRepository.findByExpectedRevenueNot(longValue);
		} else if (field.toString().equalsIgnoreCase(Field.EXPECTED_REVENUE.toString())
				&& operator.toString().equalsIgnoreCase(Operator.EQUALS.toString())) {
			Long longValue = Long.parseLong(value);
			listOpp = opportunityRepository.findByExpectedRevenueEquals(longValue);
		} else if (field.toString().equalsIgnoreCase(Field.EXPECTED_REVENUE.toString())
				&& operator.toString().equalsIgnoreCase(Operator.LESS_OR_EQUAL.toString())) {
			Long longValue = Long.parseLong(value);
			listOpp = opportunityRepository.findByExpectedRevenueLessThanEqual(longValue);
		} else if (field.toString().equalsIgnoreCase(Field.EXPECTED_REVENUE.toString())
				&& operator.toString().equalsIgnoreCase(Operator.GREATER_OR_EQUAL.toString())) {
			Long longValue = Long.parseLong(value);
			listOpp = opportunityRepository.findByExpectedRevenueGreaterThanEqual(longValue);
		}
		// trackingNumber(long)
		if (field.toString().equalsIgnoreCase(Field.TRACKING_NUMBER.toString())
				&& operator.toString().equalsIgnoreCase(Operator.GREATER_THAN.toString())) {
			Long longValue = Long.parseLong(value);
			listOpp = opportunityRepository.findBytrackingNumberGreaterThan(longValue);
		} else if (field.toString().equalsIgnoreCase(Field.TRACKING_NUMBER.toString())
				&& operator.toString().equalsIgnoreCase(Operator.LESS_THAN.toString())) {
			Long longValue = Long.parseLong(value);
			listOpp = opportunityRepository.findBytrackingNumberLessThan(longValue);
		} else if (field.toString().equalsIgnoreCase(Field.TRACKING_NUMBER.toString())
				&& operator.toString().equalsIgnoreCase(Operator.EQUALS.toString())) {
			Long longValue = Long.parseLong(value);
			listOpp = opportunityRepository.findBytrackingNumberEquals(longValue);
		} else if (field.toString().equalsIgnoreCase(Field.TRACKING_NUMBER.toString())
				&& operator.toString().equalsIgnoreCase(Operator.NOT_EQUALS_TO.toString())) {
			Long longValue = Long.parseLong(value);
			listOpp = opportunityRepository.findBytrackingNumberNot(longValue);
		} else if (field.toString().equalsIgnoreCase(Field.TRACKING_NUMBER.toString())
				&& operator.toString().equalsIgnoreCase(Operator.GREATER_OR_EQUAL.toString())) {
			Long longValue = Long.parseLong(value);
			listOpp = opportunityRepository.findBytrackingNumberGreaterThanEqual(longValue);
		} else if (field.toString().equalsIgnoreCase(Field.TRACKING_NUMBER.toString())
				&& operator.toString().equalsIgnoreCase(Operator.LESS_OR_EQUAL.toString())) {
			Long longValue = Long.parseLong(value);
			listOpp = opportunityRepository.findBytrackingNumberLessThanEqual(longValue);

		}

		// pushCount(long)
		if (field.toString().equalsIgnoreCase(Field.PUSH_COUNT.toString())
				&& operator.toString().equalsIgnoreCase(Operator.GREATER_THAN.toString())) {
			Long longValue = Long.parseLong(value);
			listOpp = opportunityRepository.findBypushCountGreaterThan(longValue);
		} else if (field.toString().equalsIgnoreCase(Field.PUSH_COUNT.toString())
				&& operator.toString().equalsIgnoreCase(Operator.LESS_THAN.toString())) {
			Long longValue = Long.parseLong(value);
			listOpp = opportunityRepository.findBypushCountLessThan(longValue);
		} else if (field.toString().equalsIgnoreCase(Field.PUSH_COUNT.toString())
				&& operator.toString().equalsIgnoreCase(Operator.EQUALS.toString())) {
			Long longValue = Long.parseLong(value);
			listOpp = opportunityRepository.findBypushCountEquals(longValue);
		} else if (field.toString().equalsIgnoreCase(Field.PUSH_COUNT.toString())
				&& operator.toString().equalsIgnoreCase(Operator.NOT_EQUALS_TO.toString())) {
			Long longValue = Long.parseLong(value);
			listOpp = opportunityRepository.findBypushCountNot(longValue);
		} else if (field.toString().equalsIgnoreCase(Field.PUSH_COUNT.toString())
				&& operator.toString().equalsIgnoreCase(Operator.GREATER_OR_EQUAL.toString())) {
			Long longValue = Long.parseLong(value);
			listOpp = opportunityRepository.findBypushCountGreaterThanEqual(longValue);
		} else if (field.toString().equalsIgnoreCase(Field.PUSH_COUNT.toString())
				&& operator.toString().equalsIgnoreCase(Operator.LESS_OR_EQUAL.toString())) {
			Long longValue = Long.parseLong(value);
			listOpp = opportunityRepository.findBypushCountLessThanEqual(longValue);

		}

		// quantity(long)
		if (field.toString().equalsIgnoreCase(Field.QUANTITY.toString())
				&& operator.toString().equalsIgnoreCase(Operator.GREATER_THAN.toString())) {
			Long longValue = Long.parseLong(value);
			listOpp = opportunityRepository.findByQuantityGreaterThan(longValue);
		} else if (field.toString().equalsIgnoreCase(Field.QUANTITY.toString())
				&& operator.toString().equalsIgnoreCase(Operator.LESS_THAN.toString())) {
			Long longValue = Long.parseLong(value);
			listOpp = opportunityRepository.findByQuantityLessThan(longValue);
		} else if (field.toString().equalsIgnoreCase(Field.QUANTITY.toString())
				&& operator.toString().equalsIgnoreCase(Operator.EQUALS.toString())) {
			Long longValue = Long.parseLong(value);
			listOpp = opportunityRepository.findByQuantityEquals(longValue);
		} else if (field.toString().equalsIgnoreCase(Field.QUANTITY.toString())
				&& operator.toString().equalsIgnoreCase(Operator.NOT_EQUALS_TO.toString())) {
			Long longValue = Long.parseLong(value);
			listOpp = opportunityRepository.findByQuantityNot(longValue);
		} else if (field.toString().equalsIgnoreCase(Field.QUANTITY.toString())
				&& operator.toString().equalsIgnoreCase(Operator.GREATER_OR_EQUAL.toString())) {
			Long longValue = Long.parseLong(value);
			listOpp = opportunityRepository.findByQuantityGreaterThanEqual(longValue);
		} else if (field.toString().equalsIgnoreCase(Field.QUANTITY.toString())
				&& operator.toString().equalsIgnoreCase(Operator.LESS_OR_EQUAL.toString())) {
			Long longValue = Long.parseLong(value);
			listOpp = opportunityRepository.findByQuantityLessThanEqual(longValue);

		}

		// orderNumber(long)
		if (field.toString().equalsIgnoreCase(Field.ORDER_NUMBER.toString())
				&& operator.toString().equalsIgnoreCase(Operator.GREATER_THAN.toString())) {
			Long longValue = Long.parseLong(value);
			listOpp = opportunityRepository.findByorderNumberGreaterThan(longValue);
		} else if (field.toString().equalsIgnoreCase(Field.ORDER_NUMBER.toString())
				&& operator.toString().equalsIgnoreCase(Operator.LESS_THAN.toString())) {
			Long longValue = Long.parseLong(value);
			listOpp = opportunityRepository.findByorderNumberLessThan(longValue);
		} else if (field.toString().equalsIgnoreCase(Field.ORDER_NUMBER.toString())
				&& operator.toString().equalsIgnoreCase(Operator.EQUALS.toString())) {
			Long longValue = Long.parseLong(value);
			listOpp = opportunityRepository.findByorderNumberEquals(longValue);
		} else if (field.toString().equalsIgnoreCase(Field.ORDER_NUMBER.toString())
				&& operator.toString().equalsIgnoreCase(Operator.NOT_EQUALS_TO.toString())) {
			Long longValue = Long.parseLong(value);
			listOpp = opportunityRepository.findByorderNumberNot(longValue);
		} else if (field.toString().equalsIgnoreCase(Field.ORDER_NUMBER.toString())
				&& operator.toString().equalsIgnoreCase(Operator.GREATER_OR_EQUAL.toString())) {
			Long longValue = Long.parseLong(value);
			listOpp = opportunityRepository.findByorderNumberGreaterThanEqual(longValue);
		} else if (field.toString().equalsIgnoreCase(Field.ORDER_NUMBER.toString())
				&& operator.toString().equalsIgnoreCase(Operator.LESS_OR_EQUAL.toString())) {
			Long longValue = Long.parseLong(value);
			listOpp = opportunityRepository.findByorderNumberLessThanEqual(longValue);

		}
		// closed
		if (field.toString().equalsIgnoreCase(Field.CLOSED.toString())
				&& operator.toString().equalsIgnoreCase(Operator.EQUALS.toString())) {
			if (value.equalsIgnoreCase("true")) {
				listOpp = opportunityRepository.findByClosed(true);
			}
		} else {
			if (value.equalsIgnoreCase("false")) {
				listOpp = opportunityRepository.findByClosed(false);
			}
		}

		if (field.toString().equalsIgnoreCase(Field.CLOSED.toString())
				&& operator.toString().equalsIgnoreCase(Operator.NOT_EQUALS_TO.toString())) {
			if (value.equalsIgnoreCase("true")) {
				listOpp = opportunityRepository.findByClosed(true);
			}
		} else {
			if (value.equalsIgnoreCase("false")) {
				listOpp = opportunityRepository.findByClosed(false);
			}
		}
		// private
		if (field.toString().equalsIgnoreCase(Field.PRIVATE.toString())
				&& operator.toString().equalsIgnoreCase(Operator.EQUALS.toString())) {
			if (value.equalsIgnoreCase("true")) {
				listOpp = opportunityRepository.findByisPrivate(true);
			}
		} else {
			if (value.equalsIgnoreCase("false")) {
				listOpp = opportunityRepository.findByisPrivate(false);
			}
		}
		if (field.toString().equalsIgnoreCase(Field.PRIVATE.toString())
				&& operator.toString().equalsIgnoreCase(Operator.NOT_EQUALS_TO.toString())) {
			if (value.equalsIgnoreCase("true")) {
				listOpp = opportunityRepository.findByisPrivate(true);
			}
		} else {
			if (value.equalsIgnoreCase("false")) {
				listOpp = opportunityRepository.findByisPrivate(false);
			}
		}

		// won
		if (field.toString().equalsIgnoreCase(Field.WON.toString())
				&& operator.toString().equalsIgnoreCase(Operator.EQUALS.toString())) {
			if (value.equalsIgnoreCase("true")) {
				listOpp = opportunityRepository.findByWon(true);
			}
		} else {
			if (value.equalsIgnoreCase("false")) {
				listOpp = opportunityRepository.findByWon(false);
			}
		}
		if (field.toString().equalsIgnoreCase(Field.WON.toString())
				&& operator.toString().equalsIgnoreCase(Operator.NOT_EQUALS_TO.toString())) {
			if (value.equalsIgnoreCase("true")) {
				listOpp = opportunityRepository.findByWon(true);
			}
		} else {
			if (value.equalsIgnoreCase("false")) {
				listOpp = opportunityRepository.findByWon(false);
			}
		}

		// stage
		if (field.toString().equalsIgnoreCase(Field.STAGE.toString())
				&& operator.toString().equalsIgnoreCase(Operator.CONTAINS.toString())) {
			listOpp = opportunityRepository.findByStageContainingIgnoreCase(value);

		} else if (field.toString().equalsIgnoreCase(Field.STAGE.toString())
				&& operator.toString().equalsIgnoreCase(Operator.DOES_NOT_CONTAIN.toString())) {
			listOpp = opportunityRepository.findByStageNotContainingIgnoreCase(value);
		}
		if (field.toString().equalsIgnoreCase(Field.STAGE.toString())
				&& operator.toString().equalsIgnoreCase(Operator.STARTS_WITH.toString())) {
			listOpp = opportunityRepository.findByStageStartsWithIgnoreCase(value);
		}

		// leadSource
		if (field.toString().equalsIgnoreCase(Field.LEAD_SOURCE.toString())
				&& operator.toString().equalsIgnoreCase(Operator.CONTAINS.toString())) {
			listOpp = opportunityRepository.findByleadSourceContainingIgnoreCase(value);

		} else if (field.toString().equalsIgnoreCase(Field.LEAD_SOURCE.toString())
				&& operator.toString().equalsIgnoreCase(Operator.DOES_NOT_CONTAIN.toString())) {
			listOpp = opportunityRepository.findByleadSourceNotContainingIgnoreCase(value);
		}
		if (field.toString().equalsIgnoreCase(Field.LEAD_SOURCE.toString())
				&& operator.toString().equalsIgnoreCase(Operator.STARTS_WITH.toString())) {
			listOpp = opportunityRepository.findByleadSourceStartsWithIgnoreCase(value);
		}

		// mainCompetitor
		if (field.toString().equalsIgnoreCase(Field.MAIN_COMPETITOR.toString())
				&& operator.toString().equalsIgnoreCase(Operator.CONTAINS.toString())) {
			listOpp = opportunityRepository.findBymainCompetitorContainingIgnoreCase(value);

		} else if (field.toString().equalsIgnoreCase(Field.MAIN_COMPETITOR.toString())
				&& operator.toString().equalsIgnoreCase(Operator.DOES_NOT_CONTAIN.toString())) {
			listOpp = opportunityRepository.findBymainCompetitorNotContainingIgnoreCase(value);
		}
		if (field.toString().equalsIgnoreCase(Field.MAIN_COMPETITOR.toString())
				&& operator.toString().equalsIgnoreCase(Operator.STARTS_WITH.toString())) {
			listOpp = opportunityRepository.findBymainCompetitorStartsWithIgnoreCase(value);
		}

		// type
		if (field.toString().equalsIgnoreCase(Field.TYPE.toString())
				&& operator.toString().equalsIgnoreCase(Operator.CONTAINS.toString())) {
			listOpp = opportunityRepository.findByTypeContainingIgnoreCase(value);

		} else if (field.toString().equalsIgnoreCase(Field.TYPE.toString())
				&& operator.toString().equalsIgnoreCase(Operator.DOES_NOT_CONTAIN.toString())) {
			listOpp = opportunityRepository.findByTypeNotContainingIgnoreCase(value);
		}
		if (field.toString().equalsIgnoreCase(Field.TYPE.toString())
				&& operator.toString().equalsIgnoreCase(Operator.STARTS_WITH.toString())) {
			listOpp = opportunityRepository.findByTypeStartsWithIgnoreCase(value);
		}

		// accountName
		if (field.toString().equalsIgnoreCase(Field.ACCOUNT_NAME.toString())
				&& operator.toString().equalsIgnoreCase(Operator.CONTAINS.toString())) {
			listOpp = opportunityRepository.findByAccountNameContainingIgnoreCase(value);
		} else if (field.toString().equalsIgnoreCase(Field.ACCOUNT_NAME.toString())
				&& operator.toString().equalsIgnoreCase(Operator.DOES_NOT_CONTAIN.toString())) {
			listOpp = opportunityRepository.findByAccountNameNotContainingIgnoreCase(value);
		}
		if (field.toString().equalsIgnoreCase(Field.ACCOUNT_NAME.toString())
				&& operator.toString().equalsIgnoreCase(Operator.STARTS_WITH.toString())) {
			listOpp = opportunityRepository.findByAccountNameStartsWithIgnoreCase(value);
		}

		// createdDate
		if (field.toString().equalsIgnoreCase(Field.CREATED_DATE.toString())
				&& operator.toString().equalsIgnoreCase(Operator.GREATER_THAN.toString())) {
			try {
				if (value.equalsIgnoreCase("yesterday")) {
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DATE, -1);
					Date createdDate = cal.getTime();
					listOpp = opportunityRepository.findBycreatedDateGreaterThan(createdDate);

				} else if (value.equalsIgnoreCase("today")) {
					Date createdDate = new Date();
					listOpp = opportunityRepository.findBycreatedDateGreaterThan(createdDate);
				} else if (value.equalsIgnoreCase("tomorrow")) {
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DATE, 1);
					Date createdDate = cal.getTime();
					listOpp = opportunityRepository.findBycreatedDateGreaterThan(createdDate);
				} else {
					Date createdDate = new SimpleDateFormat("yyyy-MM-dd").parse(value);
					listOpp = opportunityRepository.findBycreatedDateGreaterThan(createdDate);
				}

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		if (field.toString().equalsIgnoreCase(Field.CREATED_DATE.toString())
				&& operator.toString().equalsIgnoreCase(Operator.LESS_THAN.toString())) {
			try {
				if (value.equalsIgnoreCase("yesterday")) {
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DATE, -1);
					Date createdDate = cal.getTime();
					listOpp = opportunityRepository.findBycreatedDateLessThan(createdDate);

				} else if (value.equalsIgnoreCase("today")) {
					Date createdDate = new Date();
					listOpp = opportunityRepository.findBycreatedDateLessThan(createdDate);
				} else if (value.equalsIgnoreCase("tomorrow")) {
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DATE, 1);
					Date createdDate = cal.getTime();
					listOpp = opportunityRepository.findBycreatedDateLessThan(createdDate);
				} else {
					Date createdDate = new SimpleDateFormat("yyyy-MM-dd").parse(value);
					listOpp = opportunityRepository.findBycreatedDateLessThan(createdDate);
				}

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		if (field.toString().equalsIgnoreCase(Field.CREATED_DATE.toString())
				&& operator.toString().equalsIgnoreCase(Operator.NOT_EQUALS_TO.toString())) {
			try {
				if (value.equalsIgnoreCase("yesterday")) {
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DATE, -1);
					Date createdDate = cal.getTime();
					listOpp = opportunityRepository.findBycreatedDateNot(createdDate);

				} else if (value.equalsIgnoreCase("today")) {
					Date createdDate = new Date();
					listOpp = opportunityRepository.findBycreatedDateNot(createdDate);
				} else if (value.equalsIgnoreCase("tomorrow")) {
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DATE, 1);
					Date createdDate = cal.getTime();
					listOpp = opportunityRepository.findBycreatedDateNot(createdDate);
				} else {
					Date createdDate = new SimpleDateFormat("yyyy-MM-dd").parse(value);
					listOpp = opportunityRepository.findBycreatedDateNot(createdDate);
				}

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		if (field.toString().equalsIgnoreCase(Field.CREATED_DATE.toString())
				&& operator.toString().equalsIgnoreCase(Operator.EQUALS.toString())) {
			try {
				if (value.equalsIgnoreCase("yesterday")) {
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DATE, -1);
					Date createdDate = cal.getTime();
					listOpp = opportunityRepository.findBycreatedDateEquals(createdDate);

				} else if (value.equalsIgnoreCase("today")) {
					Date createdDate = new Date();
					listOpp = opportunityRepository.findBycreatedDateEquals(createdDate);
				} else if (value.equalsIgnoreCase("tomorrow")) {
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DATE, 1);
					Date createdDate = cal.getTime();
					listOpp = opportunityRepository.findBycreatedDateEquals(createdDate);
				} else {
					Date createdDate = new SimpleDateFormat("yyyy-MM-dd").parse(value);
					listOpp = opportunityRepository.findBycreatedDateEquals(createdDate);
				}

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		if (field.toString().equalsIgnoreCase(Field.CREATED_DATE.toString())
				&& operator.toString().equalsIgnoreCase(Operator.GREATER_OR_EQUAL.toString())) {
			try {
				if (value.equalsIgnoreCase("yesterday")) {
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DATE, -1);
					Date createdDate = cal.getTime();
					listOpp = opportunityRepository.findBycreatedDateGreaterThanEqual(createdDate);

				} else if (value.equalsIgnoreCase("today")) {
					Date createdDate = new Date();
					listOpp = opportunityRepository.findBycreatedDateGreaterThanEqual(createdDate);
				} else if (value.equalsIgnoreCase("tomorrow")) {
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DATE, 1);
					Date createdDate = cal.getTime();
					listOpp = opportunityRepository.findBycreatedDateGreaterThanEqual(createdDate);
				} else {
					Date createdDate = new SimpleDateFormat("yyyy-MM-dd").parse(value);
					listOpp = opportunityRepository.findBycreatedDateGreaterThanEqual(createdDate);
				}

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		if (field.toString().equalsIgnoreCase(Field.CREATED_DATE.toString())
				&& operator.toString().equalsIgnoreCase(Operator.LESS_OR_EQUAL.toString())) {
			try {
				if (value.equalsIgnoreCase("yesterday")) {
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DATE, -1);
					Date createdDate = cal.getTime();
					listOpp = opportunityRepository.findBycreatedDateLessThanEqual(createdDate);

				} else if (value.equalsIgnoreCase("today")) {
					Date createdDate = new Date();
					listOpp = opportunityRepository.findBycreatedDateLessThanEqual(createdDate);
				} else if (value.equalsIgnoreCase("tomorrow")) {
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DATE, 1);
					Date createdDate = cal.getTime();
					listOpp = opportunityRepository.findBycreatedDateLessThanEqual(createdDate);
				} else {
					Date createdDate = new SimpleDateFormat("yyyy-MM-dd").parse(value);
					listOpp = opportunityRepository.findBycreatedDateLessThanEqual(createdDate);
				}

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		// closeDate
		if (field.toString().equalsIgnoreCase(Field.CLOSE_DATE.toString())
				&& operator.toString().equalsIgnoreCase(Operator.GREATER_THAN.toString())) {
			try {
				if (value.equalsIgnoreCase("yesterday")) {
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DATE, -1);
					Date closeDate = cal.getTime();
					listOpp = opportunityRepository.findBycloseDateGreaterThan(closeDate);

				} else if (value.equalsIgnoreCase("today")) {
					Date closeDate = new Date();
					listOpp = opportunityRepository.findBycloseDateGreaterThan(closeDate);
				} else if (value.equalsIgnoreCase("tomorrow")) {
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DATE, 1);
					Date closeDate = cal.getTime();
					listOpp = opportunityRepository.findBycloseDateGreaterThan(closeDate);
				} else {
					Date closeDate = new SimpleDateFormat("yyyy-MM-dd").parse(value);
					listOpp = opportunityRepository.findBycloseDateGreaterThan(closeDate);
				}

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		if (field.toString().equalsIgnoreCase(Field.CLOSE_DATE.toString())
				&& operator.toString().equalsIgnoreCase(Operator.LESS_THAN.toString())) {
			try {
				if (value.equalsIgnoreCase("yesterday")) {
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DATE, -1);
					Date closeDate = cal.getTime();
					listOpp = opportunityRepository.findBycloseDateLessThan(closeDate);

				} else if (value.equalsIgnoreCase("today")) {
					Date closeDate = new Date();
					listOpp = opportunityRepository.findBycloseDateLessThan(closeDate);
				} else if (value.equalsIgnoreCase("tomorrow")) {
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DATE, 1);
					Date closeDate = cal.getTime();
					listOpp = opportunityRepository.findBycloseDateLessThan(closeDate);
				} else {
					Date closeDate = new SimpleDateFormat("yyyy-MM-dd").parse(value);
					listOpp = opportunityRepository.findBycloseDateLessThan(closeDate);
				}

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		if (field.toString().equalsIgnoreCase(Field.CLOSE_DATE.toString())
				&& operator.toString().equalsIgnoreCase(Operator.NOT_EQUALS_TO.toString())) {
			try {
				if (value.equalsIgnoreCase("yesterday")) {
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DATE, -1);
					Date closeDate = cal.getTime();
					listOpp = opportunityRepository.findBycloseDateNot(closeDate);

				} else if (value.equalsIgnoreCase("today")) {
					Date closeDate = new Date();
					listOpp = opportunityRepository.findBycloseDateNot(closeDate);
				} else if (value.equalsIgnoreCase("tomorrow")) {
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DATE, 1);
					Date closeDate = cal.getTime();
					listOpp = opportunityRepository.findBycloseDateNot(closeDate);
				} else {
					Date closeDate = new SimpleDateFormat("yyyy-MM-dd").parse(value);
					listOpp = opportunityRepository.findBycloseDateNot(closeDate);
				}

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		if (field.toString().equalsIgnoreCase(Field.CLOSE_DATE.toString())
				&& operator.toString().equalsIgnoreCase(Operator.EQUALS.toString())) {
			try {
				if (value.equalsIgnoreCase("yesterday")) {
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DATE, -1);
					Date closeDate = cal.getTime();
					listOpp = opportunityRepository.findBycloseDateEquals(closeDate);

				} else if (value.equalsIgnoreCase("today")) {
					Date closeDate = new Date();
					listOpp = opportunityRepository.findBycloseDateEquals(closeDate);
				} else if (value.equalsIgnoreCase("tomorrow")) {
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DATE, 1);
					Date closeDate = cal.getTime();
					listOpp = opportunityRepository.findBycloseDateEquals(closeDate);
				} else {
					Date closeDate = new SimpleDateFormat("yyyy-MM-dd").parse(value);
					listOpp = opportunityRepository.findBycloseDateEquals(closeDate);
				}

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		if (field.toString().equalsIgnoreCase(Field.CLOSE_DATE.toString())
				&& operator.toString().equalsIgnoreCase(Operator.GREATER_OR_EQUAL.toString())) {
			try {
				if (value.equalsIgnoreCase("yesterday")) {
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DATE, -1);
					Date closeDate = cal.getTime();
					listOpp = opportunityRepository.findBycloseDateGreaterThanEqual(closeDate);

				} else if (value.equalsIgnoreCase("today")) {
					Date closeDate = new Date();
					listOpp = opportunityRepository.findBycloseDateGreaterThanEqual(closeDate);
				} else if (value.equalsIgnoreCase("tomorrow")) {
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DATE, 1);
					Date closeDate = cal.getTime();
					listOpp = opportunityRepository.findBycloseDateGreaterThanEqual(closeDate);
				} else {
					Date closeDate = new SimpleDateFormat("yyyy-MM-dd").parse(value);
					listOpp = opportunityRepository.findBycloseDateGreaterThanEqual(closeDate);
				}

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		if (field.toString().equalsIgnoreCase(Field.CLOSE_DATE.toString())
				&& operator.toString().equalsIgnoreCase(Operator.LESS_OR_EQUAL.toString())) {
			try {
				if (value.equalsIgnoreCase("yesterday")) {
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DATE, -1);
					Date closeDate = cal.getTime();
					listOpp = opportunityRepository.findBycloseDateLessThanEqual(closeDate);

				} else if (value.equalsIgnoreCase("today")) {
					Date closeDate = new Date();
					listOpp = opportunityRepository.findBycloseDateLessThanEqual(closeDate);
				} else if (value.equalsIgnoreCase("tomorrow")) {
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DATE, 1);
					Date closeDate = cal.getTime();
					listOpp = opportunityRepository.findBycloseDateLessThanEqual(closeDate);
				} else {
					Date closeDate = new SimpleDateFormat("yyyy-MM-dd").parse(value);
					listOpp = opportunityRepository.findBycloseDateLessThanEqual(closeDate);
				}

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		// lastModifiedDate
		if (field.toString().equalsIgnoreCase(Field.LAST_MODIFIED_DATE.toString())
				&& operator.toString().equalsIgnoreCase(Operator.GREATER_THAN.toString())) {
			try {
				if (value.equalsIgnoreCase("yesterday")) {
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DATE, -1);
					Date lastModifiedDate = cal.getTime();
					listOpp = opportunityRepository.findBylastModifiedDateGreaterThan(lastModifiedDate);

				} else if (value.equalsIgnoreCase("today")) {
					Date lastModifiedDate = new Date();
					listOpp = opportunityRepository.findBylastModifiedDateGreaterThan(lastModifiedDate);
				} else if (value.equalsIgnoreCase("tomorrow")) {
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DATE, 1);
					Date lastModifiedDate = cal.getTime();
					listOpp = opportunityRepository.findBylastModifiedDateGreaterThan(lastModifiedDate);
				} else {
					Date lastModifiedDate = new SimpleDateFormat("yyyy-MM-dd").parse(value);
					listOpp = opportunityRepository.findBylastModifiedDateGreaterThan(lastModifiedDate);
				}

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		if (field.toString().equalsIgnoreCase(Field.LAST_MODIFIED_DATE.toString())
				&& operator.toString().equalsIgnoreCase(Operator.LESS_THAN.toString())) {
			try {
				if (value.equalsIgnoreCase("yesterday")) {
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DATE, -1);
					Date lastModifiedDate = cal.getTime();
					listOpp = opportunityRepository.findBylastModifiedDateLessThan(lastModifiedDate);

				} else if (value.equalsIgnoreCase("today")) {
					Date lastModifiedDate = new Date();
					listOpp = opportunityRepository.findBylastModifiedDateLessThan(lastModifiedDate);
				} else if (value.equalsIgnoreCase("tomorrow")) {
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DATE, 1);
					Date lastModifiedDate = cal.getTime();
					listOpp = opportunityRepository.findBylastModifiedDateLessThan(lastModifiedDate);
				} else {
					Date lastModifiedDate = new SimpleDateFormat("yyyy-MM-dd").parse(value);
					listOpp = opportunityRepository.findBylastModifiedDateLessThan(lastModifiedDate);
				}

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		if (field.toString().equalsIgnoreCase(Field.LAST_MODIFIED_DATE.toString())
				&& operator.toString().equalsIgnoreCase(Operator.NOT_EQUALS_TO.toString())) {
			try {
				if (value.equalsIgnoreCase("yesterday")) {
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DATE, -1);
					Date lastModifiedDate = cal.getTime();
					listOpp = opportunityRepository.findBylastModifiedDateNot(lastModifiedDate);

				} else if (value.equalsIgnoreCase("today")) {
					Date lastModifiedDate = new Date();
					listOpp = opportunityRepository.findBylastModifiedDateNot(lastModifiedDate);
				} else if (value.equalsIgnoreCase("tomorrow")) {
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DATE, 1);
					Date lastModifiedDate = cal.getTime();
					listOpp = opportunityRepository.findBylastModifiedDateNot(lastModifiedDate);
				} else {
					Date lastModifiedDate = new SimpleDateFormat("yyyy-MM-dd").parse(value);
					listOpp = opportunityRepository.findBylastModifiedDateNot(lastModifiedDate);
				}

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		if (field.toString().equalsIgnoreCase(Field.LAST_MODIFIED_DATE.toString())
				&& operator.toString().equalsIgnoreCase(Operator.EQUALS.toString())) {
			try {
				if (value.equalsIgnoreCase("yesterday")) {
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DATE, -1);
					Date lastModifiedDate = cal.getTime();
					listOpp = opportunityRepository.findBylastModifiedDateEquals(lastModifiedDate);

				} else if (value.equalsIgnoreCase("today")) {
					Date lastModifiedDate = new Date();
					listOpp = opportunityRepository.findBylastModifiedDateEquals(lastModifiedDate);
				} else if (value.equalsIgnoreCase("tomorrow")) {
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DATE, 1);
					Date lastModifiedDate = cal.getTime();
					listOpp = opportunityRepository.findBylastModifiedDateEquals(lastModifiedDate);
				} else {
					Date lastModifiedDate = new SimpleDateFormat("yyyy-MM-dd").parse(value);
					listOpp = opportunityRepository.findBylastModifiedDateEquals(lastModifiedDate);
				}

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		if (field.toString().equalsIgnoreCase(Field.LAST_MODIFIED_DATE.toString())
				&& operator.toString().equalsIgnoreCase(Operator.GREATER_OR_EQUAL.toString())) {
			try {
				if (value.equalsIgnoreCase("yesterday")) {
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DATE, -1);
					Date lastModifiedDate = cal.getTime();
					listOpp = opportunityRepository.findBylastModifiedDateGreaterThanEqual(lastModifiedDate);

				} else if (value.equalsIgnoreCase("today")) {
					Date lastModifiedDate = new Date();
					listOpp = opportunityRepository.findBylastModifiedDateGreaterThanEqual(lastModifiedDate);
				} else if (value.equalsIgnoreCase("tomorrow")) {
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DATE, 1);
					Date lastModifiedDate = cal.getTime();
					listOpp = opportunityRepository.findBylastModifiedDateGreaterThanEqual(lastModifiedDate);
				} else {
					Date lastModifiedDate = new SimpleDateFormat("yyyy-MM-dd").parse(value);
					listOpp = opportunityRepository.findBylastModifiedDateGreaterThanEqual(lastModifiedDate);
				}

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		if (field.toString().equalsIgnoreCase(Field.LAST_MODIFIED_DATE.toString())
				&& operator.toString().equalsIgnoreCase(Operator.LESS_OR_EQUAL.toString())) {
			try {
				if (value.equalsIgnoreCase("yesterday")) {
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DATE, -1);
					Date lastModifiedDate = cal.getTime();
					listOpp = opportunityRepository.findBylastModifiedDateLessThanEqual(lastModifiedDate);

				} else if (value.equalsIgnoreCase("today")) {
					Date lastModifiedDate = new Date();
					listOpp = opportunityRepository.findBylastModifiedDateLessThanEqual(lastModifiedDate);
				} else if (value.equalsIgnoreCase("tomorrow")) {
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DATE, 1);
					Date lastModifiedDate = cal.getTime();
					listOpp = opportunityRepository.findBylastModifiedDateLessThanEqual(lastModifiedDate);
				} else {
					Date lastModifiedDate = new SimpleDateFormat("yyyy-MM-dd").parse(value);
					listOpp = opportunityRepository.findBylastModifiedDateLessThanEqual(lastModifiedDate);
				}

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		// lastStageChangeDate
		if (field.toString().equalsIgnoreCase(Field.LAST_STAGE_CHANGE_DATE.toString())
				&& operator.toString().equalsIgnoreCase(Operator.GREATER_THAN.toString())) {
			try {
				if (value.equalsIgnoreCase("yesterday")) {
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DATE, -1);
					Date lastStageChangeDate = cal.getTime();
					listOpp = opportunityRepository.findBylastStageChangeDateGreaterThan(lastStageChangeDate);

				} else if (value.equalsIgnoreCase("today")) {
					Date lastStageChangeDate = new Date();
					listOpp = opportunityRepository.findBylastStageChangeDateGreaterThan(lastStageChangeDate);
				} else if (value.equalsIgnoreCase("tomorrow")) {
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DATE, 1);
					Date lastStageChangeDate = cal.getTime();
					listOpp = opportunityRepository.findBylastStageChangeDateGreaterThan(lastStageChangeDate);
				} else {
					Date lastStageChangeDate = new SimpleDateFormat("yyyy-MM-dd").parse(value);
					listOpp = opportunityRepository.findBylastStageChangeDateGreaterThan(lastStageChangeDate);
				}

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		if (field.toString().equalsIgnoreCase(Field.LAST_STAGE_CHANGE_DATE.toString())
				&& operator.toString().equalsIgnoreCase(Operator.LESS_THAN.toString())) {
			try {
				if (value.equalsIgnoreCase("yesterday")) {
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DATE, -1);
					Date lastStageChangeDate = cal.getTime();
					listOpp = opportunityRepository.findBylastStageChangeDateLessThan(lastStageChangeDate);

				} else if (value.equalsIgnoreCase("today")) {
					Date lastStageChangeDate = new Date();
					listOpp = opportunityRepository.findBylastStageChangeDateLessThan(lastStageChangeDate);
				} else if (value.equalsIgnoreCase("tomorrow")) {
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DATE, 1);
					Date lastStageChangeDate = cal.getTime();
					listOpp = opportunityRepository.findBylastStageChangeDateLessThan(lastStageChangeDate);
				} else {
					Date lastStageChangeDate = new SimpleDateFormat("yyyy-MM-dd").parse(value);
					listOpp = opportunityRepository.findBylastStageChangeDateLessThan(lastStageChangeDate);
				}

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		if (field.toString().equalsIgnoreCase(Field.LAST_STAGE_CHANGE_DATE.toString())
				&& operator.toString().equalsIgnoreCase(Operator.NOT_EQUALS_TO.toString())) {
			try {
				if (value.equalsIgnoreCase("yesterday")) {
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DATE, -1);
					Date lastStageChangeDate = cal.getTime();
					listOpp = opportunityRepository.findBylastStageChangeDateNot(lastStageChangeDate);

				} else if (value.equalsIgnoreCase("today")) {
					Date lastStageChangeDate = new Date();
					listOpp = opportunityRepository.findBylastStageChangeDateNot(lastStageChangeDate);
				} else if (value.equalsIgnoreCase("tomorrow")) {
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DATE, 1);
					Date lastStageChangeDate = cal.getTime();
					listOpp = opportunityRepository.findBylastStageChangeDateNot(lastStageChangeDate);
				} else {
					Date lastStageChangeDate = new SimpleDateFormat("yyyy-MM-dd").parse(value);
					listOpp = opportunityRepository.findBylastStageChangeDateNot(lastStageChangeDate);
				}

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		if (field.toString().equalsIgnoreCase(Field.LAST_STAGE_CHANGE_DATE.toString())
				&& operator.toString().equalsIgnoreCase(Operator.EQUALS.toString())) {
			try {
				if (value.equalsIgnoreCase("yesterday")) {
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DATE, -1);
					Date lastStageChangeDate = cal.getTime();
					listOpp = opportunityRepository.findBylastStageChangeDateEquals(lastStageChangeDate);

				} else if (value.equalsIgnoreCase("today")) {
					Date lastStageChangeDate = new Date();
					listOpp = opportunityRepository.findBylastStageChangeDateEquals(lastStageChangeDate);
				} else if (value.equalsIgnoreCase("tomorrow")) {
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DATE, 1);
					Date lastStageChangeDate = cal.getTime();
					listOpp = opportunityRepository.findBylastStageChangeDateEquals(lastStageChangeDate);
				} else {
					Date lastStageChangeDate = new SimpleDateFormat("yyyy-MM-dd").parse(value);
					listOpp = opportunityRepository.findBylastStageChangeDateEquals(lastStageChangeDate);
				}

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		if (field.toString().equalsIgnoreCase(Field.LAST_STAGE_CHANGE_DATE.toString())
				&& operator.toString().equalsIgnoreCase(Operator.GREATER_OR_EQUAL.toString())) {
			try {
				if (value.equalsIgnoreCase("yesterday")) {
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DATE, -1);
					Date lastStageChangeDate = cal.getTime();
					listOpp = opportunityRepository.findBylastStageChangeDateGreaterThanEqual(lastStageChangeDate);

				} else if (value.equalsIgnoreCase("today")) {
					Date lastStageChangeDate = new Date();
					listOpp = opportunityRepository.findBylastStageChangeDateGreaterThanEqual(lastStageChangeDate);
				} else if (value.equalsIgnoreCase("tomorrow")) {
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DATE, 1);
					Date lastStageChangeDate = cal.getTime();
					listOpp = opportunityRepository.findBylastStageChangeDateGreaterThanEqual(lastStageChangeDate);
				} else {
					Date lastStageChangeDate = new SimpleDateFormat("yyyy-MM-dd").parse(value);
					listOpp = opportunityRepository.findBylastStageChangeDateGreaterThanEqual(lastStageChangeDate);
				}

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		if (field.toString().equalsIgnoreCase(Field.LAST_STAGE_CHANGE_DATE.toString())
				&& operator.toString().equalsIgnoreCase(Operator.LESS_OR_EQUAL.toString())) {
			try {
				if (value.equalsIgnoreCase("yesterday")) {
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DATE, -1);
					Date lastStageChangeDate = cal.getTime();
					listOpp = opportunityRepository.findBylastStageChangeDateLessThanEqual(lastStageChangeDate);

				} else if (value.equalsIgnoreCase("today")) {
					Date lastStageChangeDate = new Date();
					listOpp = opportunityRepository.findBylastStageChangeDateLessThanEqual(lastStageChangeDate);
				} else if (value.equalsIgnoreCase("tomorrow")) {
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DATE, 1);
					Date lastStageChangeDate = cal.getTime();
					listOpp = opportunityRepository.findBylastStageChangeDateLessThanEqual(lastStageChangeDate);
				} else {
					Date lastStageChangeDate = new SimpleDateFormat("yyyy-MM-dd").parse(value);
					listOpp = opportunityRepository.findBylastStageChangeDateLessThanEqual(lastStageChangeDate);
				}

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		// status //delivery installation status
		if (field.toString().equalsIgnoreCase(Field.DELIVERY_INSTALLATION_STATUS.toString())
				&& operator.toString().equalsIgnoreCase(Operator.CONTAINS.toString())) {
			listOpp = opportunityRepository.findByStatusContainingIgnoreCase(value);
		}
		if (field.toString().equalsIgnoreCase(Field.DELIVERY_INSTALLATION_STATUS.toString())
				&& operator.toString().equalsIgnoreCase(Operator.DOES_NOT_CONTAIN.toString())) {
			listOpp = opportunityRepository.findByStatusNotContainingIgnoreCase(value);
		}
		if (field.toString().equalsIgnoreCase(Field.DELIVERY_INSTALLATION_STATUS.toString())
				&& operator.toString().equalsIgnoreCase(Operator.STARTS_WITH.toString())) {
			listOpp = opportunityRepository.findByStatusStartsWithIgnoreCase(value);
		}

		// forecastCategoryName
		if (field.toString().equalsIgnoreCase(Field.FORECAST_CATEGORY.toString())
				&& operator.toString().equalsIgnoreCase(Operator.CONTAINS.toString())) {
			listOpp = opportunityRepository.findByforecastCategoryNameContainingIgnoreCase(value);
		}
		if (field.toString().equalsIgnoreCase(Field.FORECAST_CATEGORY.toString())
				&& operator.toString().equalsIgnoreCase(Operator.DOES_NOT_CONTAIN.toString())) {
			listOpp = opportunityRepository.findByforecastCategoryNameNotContainingIgnoreCase(value);
		}
		if (field.toString().equalsIgnoreCase(Field.FORECAST_CATEGORY.toString())
				&& operator.toString().equalsIgnoreCase(Operator.STARTS_WITH.toString())) {
			listOpp = opportunityRepository.findByforecastCategoryNameStartsWithIgnoreCase(value);
		}

		// nextStep
		if (field.toString().equalsIgnoreCase(Field.NEXT_STEP.toString())
				&& operator.toString().equalsIgnoreCase(Operator.CONTAINS.toString())) {
			listOpp = opportunityRepository.findBynextStepContainingIgnoreCase(value);
		}
		if (field.toString().equalsIgnoreCase(Field.NEXT_STEP.toString())
				&& operator.toString().equalsIgnoreCase(Operator.DOES_NOT_CONTAIN.toString())) {
			listOpp = opportunityRepository.findBynextStepNotContainingIgnoreCase(value);
		}
		if (field.toString().equalsIgnoreCase(Field.NEXT_STEP.toString())
				&& operator.toString().equalsIgnoreCase(Operator.STARTS_WITH.toString())) {
			listOpp = opportunityRepository.findBynextStepStartsWithIgnoreCase(value);
		}

		// name opportunity name
		if (field.toString().equalsIgnoreCase(Field.OPPORTUNITY_NAME.toString())
				&& operator.toString().equalsIgnoreCase(Operator.CONTAINS.toString())) {
			listOpp = opportunityRepository.findByNameContainingIgnoreCase(value);
		}
		if (field.toString().equalsIgnoreCase(Field.OPPORTUNITY_NAME.toString())
				&& operator.toString().equalsIgnoreCase(Operator.DOES_NOT_CONTAIN.toString())) {
			listOpp = opportunityRepository.findByNameNotContainingIgnoreCase(value);
		}
		if (field.toString().equalsIgnoreCase(Field.OPPORTUNITY_NAME.toString())
				&& operator.toString().equalsIgnoreCase(Operator.STARTS_WITH.toString())) {
			listOpp = opportunityRepository.findByNameStartsWithIgnoreCase(value);
		}

		// ownerFirstName
		if (field.toString().equalsIgnoreCase(Field.OWNER_FIRST_NAME.toString())
				&& operator.toString().equalsIgnoreCase(Operator.CONTAINS.toString())) {
			listOpp = opportunityRepository.findByownerFirstNameContainingIgnoreCase(value);
		}
		if (field.toString().equalsIgnoreCase(Field.OWNER_FIRST_NAME.toString())
				&& operator.toString().equalsIgnoreCase(Operator.DOES_NOT_CONTAIN.toString())) {
			listOpp = opportunityRepository.findByownerFirstNameNotContainingIgnoreCase(value);
		}
		if (field.toString().equalsIgnoreCase(Field.OWNER_FIRST_NAME.toString())
				&& operator.toString().equalsIgnoreCase(Operator.STARTS_WITH.toString())) {
			listOpp = opportunityRepository.findByownerFirstNameStartsWithIgnoreCase(value);
		}

		// ownerLastName
		if (field.toString().equalsIgnoreCase(Field.OWNER_LAST_NAME.toString())
				&& operator.toString().equalsIgnoreCase(Operator.CONTAINS.toString())) {
			listOpp = opportunityRepository.findByownerLastNameContainingIgnoreCase(value);
		}
		if (field.toString().equalsIgnoreCase(Field.OWNER_LAST_NAME.toString())
				&& operator.toString().equalsIgnoreCase(Operator.DOES_NOT_CONTAIN.toString())) {
			listOpp = opportunityRepository.findByownerLastNameNotContainingIgnoreCase(value);
		}
		if (field.toString().equalsIgnoreCase(Field.OWNER_LAST_NAME.toString())
				&& operator.toString().equalsIgnoreCase(Operator.STARTS_WITH.toString())) {
			listOpp = opportunityRepository.findByownerLastNameStartsWithIgnoreCase(value);
		}

		// ownerFullName
		if (field.toString().equalsIgnoreCase(Field.OWNER_FULL_NAME.toString())
				&& operator.toString().equalsIgnoreCase(Operator.CONTAINS.toString())) {
			listOpp = opportunityRepository.findByownerFullNameContainingIgnoreCase(value);
		}
		if (field.toString().equalsIgnoreCase(Field.OWNER_FULL_NAME.toString())
				&& operator.toString().equalsIgnoreCase(Operator.DOES_NOT_CONTAIN.toString())) {
			listOpp = opportunityRepository.findByownerFullNameNotContainingIgnoreCase(value);
		}
		if (field.toString().equalsIgnoreCase(Field.OWNER_FULL_NAME.toString())
				&& operator.toString().equalsIgnoreCase(Operator.STARTS_WITH.toString())) {
			listOpp = opportunityRepository.findByownerFullNameStartsWithIgnoreCase(value);
		}

		// opportunityOwnerAlias
		if (field.toString().equalsIgnoreCase(Field.OPPORTUNITY_OWNER_ALIAS.toString())
				&& operator.toString().equalsIgnoreCase(Operator.CONTAINS.toString())) {
			listOpp = opportunityRepository.findByopportunityOwnerAliasContainingIgnoreCase(value);
		}
		if (field.toString().equalsIgnoreCase(Field.OPPORTUNITY_OWNER_ALIAS.toString())
				&& operator.toString().equalsIgnoreCase(Operator.DOES_NOT_CONTAIN.toString())) {
			listOpp = opportunityRepository.findByopportunityOwnerAliasNotContainingIgnoreCase(value);
		}
		if (field.toString().equalsIgnoreCase(Field.OPPORTUNITY_OWNER_ALIAS.toString())
				&& operator.toString().equalsIgnoreCase(Operator.STARTS_WITH.toString())) {
			listOpp = opportunityRepository.findByopportunityOwnerAliasStartsWithIgnoreCase(value);
		}

		// createdByAlias
		if (field.toString().equalsIgnoreCase(Field.CREATED_BY_ALIAS.toString())
				&& operator.toString().equalsIgnoreCase(Operator.CONTAINS.toString())) {
			listOpp = opportunityRepository.findBycreatedByAliasContainingIgnoreCase(value);
		}
		if (field.toString().equalsIgnoreCase(Field.CREATED_BY_ALIAS.toString())
				&& operator.toString().equalsIgnoreCase(Operator.DOES_NOT_CONTAIN.toString())) {
			listOpp = opportunityRepository.findBycreatedByAliasNotContainingIgnoreCase(value);
		}
		if (field.toString().equalsIgnoreCase(Field.CREATED_BY_ALIAS.toString())
				&& operator.toString().equalsIgnoreCase(Operator.STARTS_WITH.toString())) {
			listOpp = opportunityRepository.findBycreatedByAliasStartsWithIgnoreCase(value);
		}

		// lastModifiedByAlias
		if (field.toString().equalsIgnoreCase(Field.LAST_MODIFIED_BY_ALIAS.toString())
				&& operator.toString().equalsIgnoreCase(Operator.CONTAINS.toString())) {
			listOpp = opportunityRepository.findBylastModifiedByAliasContainingIgnoreCase(value);
		}
		if (field.toString().equalsIgnoreCase(Field.LAST_MODIFIED_BY_ALIAS.toString())
				&& operator.toString().equalsIgnoreCase(Operator.DOES_NOT_CONTAIN.toString())) {
			listOpp = opportunityRepository.findBylastModifiedByAliasNotContainingIgnoreCase(value);
		}
		if (field.toString().equalsIgnoreCase(Field.LAST_MODIFIED_BY_ALIAS.toString())
				&& operator.toString().equalsIgnoreCase(Operator.STARTS_WITH.toString())) {
			listOpp = opportunityRepository.findBylastModifiedByAliasStartsWithIgnoreCase(value);
		}

		// currentGenerators
		if (field.toString().equalsIgnoreCase(Field.CURRENT_GENERATOR.toString())
				&& operator.toString().equalsIgnoreCase(Operator.CONTAINS.toString())) {
			listOpp = opportunityRepository.findBycurrentGeneratorsContainingIgnoreCase(value);
		}
		if (field.toString().equalsIgnoreCase(Field.CURRENT_GENERATOR.toString())
				&& operator.toString().equalsIgnoreCase(Operator.DOES_NOT_CONTAIN.toString())) {
			listOpp = opportunityRepository.findBycurrentGeneratorsNotContainingIgnoreCase(value);
		}
		if (field.toString().equalsIgnoreCase(Field.CURRENT_GENERATOR.toString())
				&& operator.toString().equalsIgnoreCase(Operator.STARTS_WITH.toString())) {
			listOpp = opportunityRepository.findBycurrentGeneratorsStartsWithIgnoreCase(value);
		}

		// topics
		if (field.toString().equalsIgnoreCase(Field.TOPICS.toString())
				&& operator.toString().equalsIgnoreCase(Operator.INCLUDES.toString())) {
			listOpp = opportunityRepository.findByTopicsContainingIgnoreCase(value);
		}
		if (field.toString().equalsIgnoreCase(Field.TOPICS.toString())
				&& operator.toString().equalsIgnoreCase(Operator.EXCLUDES.toString())) {
			listOpp = opportunityRepository.findByTopicsNotContainingIgnoreCase(value);
		}

		// accountSite
		if (field.toString().equalsIgnoreCase(Field.ACCOUNT_SITE.toString())
				&& operator.toString().equalsIgnoreCase(Operator.CONTAINS.toString())) {
			listOpp = opportunityRepository.findByAccountSiteContainingIgnoreCase(value);
		}
		if (field.toString().equalsIgnoreCase(Field.ACCOUNT_SITE.toString())
				&& operator.toString().equalsIgnoreCase(Operator.DOES_NOT_CONTAIN.toString())) {
			listOpp = opportunityRepository.findByAccountSiteNotContainingIgnoreCase(value);
		}
		if (field.toString().equalsIgnoreCase(Field.ACCOUNT_SITE.toString())
				&& operator.toString().equalsIgnoreCase(Operator.STARTS_WITH.toString())) {
			listOpp = opportunityRepository.findByAccountSiteStartsWithIgnoreCase(value);
		}

		// lastActivity
		if (field.toString().equalsIgnoreCase(Field.LAST_ACTIVITY.toString())
				&& operator.toString().equalsIgnoreCase(Operator.CONTAINS.toString())) {
			listOpp = opportunityRepository.findBylastActivityContainingIgnoreCase(value);
		}
		if (field.toString().equalsIgnoreCase(Field.LAST_ACTIVITY.toString())
				&& operator.toString().equalsIgnoreCase(Operator.DOES_NOT_CONTAIN.toString())) {
			listOpp = opportunityRepository.findBylastActivityNotContainingIgnoreCase(value);
		}
		if (field.toString().equalsIgnoreCase(Field.LAST_ACTIVITY.toString())
				&& operator.toString().equalsIgnoreCase(Operator.STARTS_WITH.toString())) {
			listOpp = opportunityRepository.findBylastActivityStartsWithIgnoreCase(value);
		}

		// probability
		if (field.toString().equalsIgnoreCase(Field.PROBABILITY.toString())
				&& operator.toString().equalsIgnoreCase(Operator.GREATER_THAN.toString())) {
			listOpp = opportunityRepository.findByProbabilityGreaterThan(value);
		} else if (field.toString().equalsIgnoreCase(Field.PROBABILITY.toString())
				&& operator.toString().equalsIgnoreCase(Operator.LESS_THAN.toString())) {
			listOpp = opportunityRepository.findByProbabilityLessThan(value);
		} else if (field.toString().equalsIgnoreCase(Field.PROBABILITY.toString())
				&& operator.toString().equalsIgnoreCase(Operator.EQUALS.toString())) {
			listOpp = opportunityRepository.findByProbabilityEquals(value);
		} else if (field.toString().equalsIgnoreCase(Field.PROBABILITY.toString())
				&& operator.toString().equalsIgnoreCase(Operator.NOT_EQUALS_TO.toString())) {
			listOpp = opportunityRepository.findByProbabilityNot(value);
		} else if (field.toString().equalsIgnoreCase(Field.PROBABILITY.toString())
				&& operator.toString().equalsIgnoreCase(Operator.GREATER_OR_EQUAL.toString())) {
			listOpp = opportunityRepository.findByProbabilityGreaterThanEqual(value);
		} else if (field.toString().equalsIgnoreCase(Field.PROBABILITY.toString())
				&& operator.toString().equalsIgnoreCase(Operator.LESS_OR_EQUAL.toString())) {
			listOpp = opportunityRepository.findByProbabilityLessThanEqual(value);

		}

		// Pagination
		Map<String, Object> map = new LinkedHashMap<>();
		Integer totalRecords = listOpp.size();
		try {
			if (request.getPageNo() != null && request.getPageNo() > 0 && request.getPageSize() != null
					&& request.getPageSize() > 0) {
				Integer startIndex = (request.getPageNo() - 1) * request.getPageSize();
				Integer endIndex = Math.min(startIndex + request.getPageSize(), totalRecords);
				listOpp = listOpp.subList(startIndex, endIndex);
			}
		} catch (Exception e) {
			listOpp = new LinkedList<>();
		}

		map.put("totalRecords", totalRecords);
		map.put("recordsPresent", listOpp.size());
		if (request.getPageSize() != null && request.getPageSize() != 0) {
			map.put("totalPages", (int) Math.ceil((double) totalRecords / request.getPageSize()));
			map.put("currentPage", request.getPageNo());
		} else {
			map.put("totalPages", 1);
			map.put("currentPage", 1);
		}
		map.put("records", listOpp);
		return map;

	}

	public void deleteOpportunity(Integer id) {
		opportunityRepository.deleteById(id);
	}

	@Transactional
	public void deleteOpportunitiesByIds(List<Integer> opportunityids) {
		opportunityRepository.deleteAllByIdInBatch(opportunityids);
	}

	public List<Opportunity> getAllUtilityApi() {
		
		return opportunityRepository.findAll();
	}

}
