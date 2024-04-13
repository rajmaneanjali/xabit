package com.xabit.service;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.xabit.model.LookupRelation;
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
public class CampaignService {
	@Autowired
	private CampaignRepository campaignRepository;

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
	private TaskRepository taskRepository;

	@Autowired
	private OpportunityRepository opportunityRepository;
	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private LookupRelationRepository lookupRelationRepository;

	public List<Campaign> getAllCampaigns() {
		
		return campaignRepository.findAll();
	}

	public List<Map<String, Object>> getAllCampaign() {
		List<Map<String, Object>> lstMap = new ArrayList<>();
//		List<Map<String, Object>> responseList = new ArrayList<>();
		List<Map<String, Object>> responseMap = new ArrayList<>();
		lstMap = campaignRepository.findAllCampaigns();

		List<Lookup> lstLookup = lookupRepository.findByRelatedTo("CAMPAIGN");

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
		    if (opportunityLookupValue != null && opportunityLookupValue.equalsIgnoreCase(LookupName.OPPORTUNITY.toString())) {
		        checkLookupRelations2(responseMap, lstMap);
		    }

		    String taskLookupValue = map.get(LookupName.TASK.toString());
		    if (taskLookupValue != null && taskLookupValue.equalsIgnoreCase(LookupName.TASK.toString())) {
		        checkLookupRelations3(responseMap, lstMap);
		    }

		    String accountLookupValue = map.get(LookupName.ACCOUNT.toString());
		    if (accountLookupValue != null && accountLookupValue.equalsIgnoreCase(LookupName.ACCOUNT.toString())) {
		        checkLookupRelations4(responseMap, lstMap);
		    }
		}

		

		List<Map<String, Object>> deduped = responseMap.stream().distinct().collect(Collectors.toList());

		return deduped;

	}

	private List<Map<String, Object>> checkLookupRelations(List<Map<String, Object>> responseMap,
			List<Map<String, Object>> lstMap) {

		Map<String, Object> leadMap = new HashMap<>();
		Map<String, Object> campaignMap = new HashMap<>();

		List<Lead> leadDataList = leadRepository.findAll();

		campaignMap.put("CampaignData", lstMap);
		leadMap.put("LeadData", leadDataList);

		responseMap.add(campaignMap);
		responseMap.add(leadMap);

		return responseMap;

	}

	private List<Map<String, Object>> checkLookupRelations1(List<Map<String, Object>> responseMap,
			List<Map<String, Object>> lstMap) {

		Map<String, Object> contactMap = new HashMap<>();
		Map<String, Object> campaignMap = new HashMap<>();

		List<Contact> contactDataList = contactRepository.findAll();

		campaignMap.put("CampaignData", lstMap);
		contactMap.put("ContactData", contactDataList);

		responseMap.add(campaignMap);
		responseMap.add(contactMap);

		return responseMap;

	}

	private List<Map<String, Object>> checkLookupRelations2(List<Map<String, Object>> responseMap,
			List<Map<String, Object>> lstMap) {

		Map<String, Object> opportunityMap = new HashMap<>();
		Map<String, Object> campaignMap = new HashMap<>();

		List<Opportunity> opportunityDataList = opportunityRepository.findAll();

		campaignMap.put("CampaignData", lstMap);
		opportunityMap.put("OpportunityData", opportunityDataList);

		responseMap.add(campaignMap);
		responseMap.add(opportunityMap);

		return responseMap;

	}

	private List<Map<String, Object>> checkLookupRelations3(List<Map<String, Object>> responseMap,
			List<Map<String, Object>> lstMap) {

		Map<String, Object> taskMap = new HashMap<>();
		Map<String, Object> campaignMap = new HashMap<>();

		List<Task> taskDataList = taskRepository.findAll();

		campaignMap.put("CampaignData", lstMap);
		taskMap.put("TaskData", taskDataList);

		responseMap.add(campaignMap);
		responseMap.add(taskMap);

		return responseMap;

	}

	private List<Map<String, Object>> checkLookupRelations4(List<Map<String, Object>> responseMap,
			List<Map<String, Object>> lstMap) {

		Map<String, Object> accountMap = new HashMap<>();
		Map<String, Object> campaignMap = new HashMap<>();

		List<Account> accountDataList = accountRepository.findAll();

		campaignMap.put("CampaignData", lstMap);
		accountMap.put("AccountData", accountDataList);

		responseMap.add(campaignMap);
		responseMap.add(accountMap);

		return responseMap;

	}

	public Campaign getCampaignById(Integer id) {
		return campaignRepository.findById(id).orElse(null);
	}

	public Campaign createCampaign(Campaign campaign) {
		return campaignRepository.save(campaign);
	}

	public List<Campaign> findByStatus(String status) {
		return campaignRepository.findByStatusIgnoreCase(status);
	}

	public List<Campaign> findByType(String type) {
		return campaignRepository.findByTypeIgnoreCase(type);
	}

	public Campaign updateCampaign(Campaign campaign) {
		Campaign campaign1 = campaignRepository.findById(campaign.getCampaignid()).orElse(null);
		campaign1.setOwner(campaign.getOwner());
		campaign1.setCreatedBy(campaign.getCreatedBy());
		campaign1.setCreatedDate(campaign.getCreatedDate());
		campaign1.setLastModifiedBy(campaign.getLastModifiedBy());
		campaign1.setLastModifiedDate(campaign.getLastModifiedDate());
		campaign1.setCampaignName(campaign.getCampaignName());
		campaign1.setActive(campaign.isActive());
		campaign1.setType(campaign.getType());
		campaign1.setStatus(campaign.getStatus());
		campaign1.setStartDate(campaign.getStartDate());
		campaign1.setEndDate(campaign.getEndDate());
		campaign1.setParentCampaign(campaign.getParentCampaign());
		campaign1.setExpectedRevenueInCampaign(campaign.getExpectedRevenueInCampaign());
		campaign1.setBudgetedCostInCampaign(campaign.getBudgetedCostInCampaign());
		campaign1.setActualCostInCampaign(campaign.getActualCostInCampaign());
		campaign1.setExpectedResponse(campaign.getExpectedResponse());
		campaign1.setNumSentInCampaign(campaign.getNumSentInCampaign());
		campaign1.setDescription(campaign.getDescription());
		return campaignRepository.save(campaign1);
	}

	public void deleteCampaign(Integer id) {
		campaignRepository.deleteById(id);
	}

	@Transactional
	public void deleteCampaignByIds(List<Integer> campaignid) {
		campaignRepository.deleteAllByIdInBatch(campaignid);
	}

	public List<String> getColumnDataTypes() {
        return campaignRepository.getColumnDataTypes();
    }
	
	}
