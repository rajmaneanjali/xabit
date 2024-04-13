package com.xabit.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xabit.model.Account;
import com.xabit.model.Campaign;
import com.xabit.model.Contact;
import com.xabit.model.Email;
import com.xabit.model.EmailActivity;
import com.xabit.model.Lead;
import com.xabit.model.Lookup;
import com.xabit.model.Opportunity;
import com.xabit.model.Task;
import com.xabit.model.User;
import com.xabit.repository.AccountRepository;
import com.xabit.repository.CampaignRepository;
import com.xabit.repository.ContactRepository;
import com.xabit.repository.EmailActivityRepository;
import com.xabit.repository.EmailRepository;
import com.xabit.repository.LeadRepository;
import com.xabit.repository.LookupRelationRepository;
import com.xabit.repository.LookupRepository;
import com.xabit.repository.MasterRepository;
import com.xabit.repository.MasterValueRepository;
import com.xabit.repository.OpportunityRepository;
import com.xabit.repository.TaskRepository;
import com.xabit.repository.UserRepository;
import com.xabit.util.MailUtil;
import com.xabit.utility.LookupName;

@Service
public class LeadService {
	@Autowired
	private LeadRepository leadRepository;
	@Autowired
	private MasterValueRepository masterValueRepository;

	@Autowired
	private MasterRepository masterRepository;

	@Autowired
	private LookupRepository lookupRepository;

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private OpportunityRepository opportunityRepository;

	@Autowired
	private ContactRepository contactRepository;

	@Autowired
	private CampaignRepository campaignRepository;
	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private MailUtil mailUtil;

	@Autowired
	private EmailService emailservice;

	@Autowired
	private LookupRelationRepository lookupRelationRepository;

	@Autowired
	private EmailActivityRepository emailActivityRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EmailRepository emailRepository;

	public List<Lead> getAllLeads() {
		return leadRepository.findAll();
	}

	public Map<String, Object> getAllDataTypes() {
		List<Lead> listLead = leadRepository.findAll();
		Map<String, Object> map = new HashMap<>();

		for (Lead lead : listLead) {

			if (null != lead.getLeadid()) {

				map.put("leadid", "Integer");

			}

			if (null != lead.getSalutation()) {

				map.put("salutation", "String");

			}
			if (null != lead.getOwner()) {

				map.put("owner", "String");

			}
			if (null != lead.getFirstName()) {

				map.put("firstName", "String");

			}
			if (null != lead.getLastName()) {

				map.put("lastName", "String");

			}
			if (null != lead.getCompany()) {

				map.put("company", "String");

			}
			if (null != lead.getTitle()) {

				map.put("title", "String");

			}
			if (null != lead.getCreatedBy()) {

				map.put("createdBy", "String");

			}
			if (null != lead.getCreatedDate()) {

				map.put("createdDate", "Date");
			}
			if (null != lead.getLastModifiedBy()) {

				map.put("lastModifiedBy", "String");

			}
			if (null != lead.getLastModifiedDate()) {

				map.put("lastModifiedDate", "Date");

			}
			if (lead.getMobile() > 0) {

				map.put("mobile", "Long");

			}
			if (lead.getPhone() > 0) {

				map.put("phone", "Long");

			}
			if (null != lead.getEmail()) {

				map.put("email", "String");

			}
			if (null != lead.getFax()) {

				map.put("fax", "String");

			}
			if (null != lead.getLeadStatus()) {

				map.put("leadStatus", "String");

			}
			if (null != lead.getLeadSource()) {

				map.put("leadSource", "String");

			}
			if (null != lead.getDescription()) {

				map.put("description", "String");

			}
			if (null != lead.getIndustry()) {

				map.put("industry", "String");

			}
			if (null != lead.getWebsite()) {

				map.put("website", "String");

			}
			if (null != lead.getAnnualRevenue()) {

				map.put("annualRevenue", "String");

			}
			if (null != lead.getNoOfEmployees()) {

				map.put("noOfEmployees", "String");

			}
			if (null != lead.getRating()) {

				map.put("rating", "String");

			}
			if (null != lead.getAddress()) {

				map.put("address", "String");

			}
			if (null != lead.getStreet()) {

				map.put("street", "String");

			}
			if (null != lead.getCity()) {

				map.put("city", "String");

			}
			if (null != lead.getState()) {

				map.put("state", "String");

			}
			if (lead.getZipCode() > 0) {

				map.put("zipCode", "Integer");

			}
			if (null != lead.getCountry()) {

				map.put("country", "String");

			}
			if (null != lead.getProductInterest()) {

				map.put("productInterest", "String");

			}
			if (null != lead.getCurrentGenerator()) {

				map.put("currentGenerator", "String");

			}
			if (null != lead.getSicCode()) {

				map.put("sicCode", "String");

			}
			if (null != lead.getPrimary()) {

				map.put("primary", "String");

			}
			if (null != lead.getNumberOfLocations()) {

				map.put("numberOfLocations", "String");

			}
			if (lead.getCampaignid() > 0) {

				map.put("campaignid", "Integer");

			}
			if (lead.getOrgid() > 0) {

				map.put("orgid", "Integer");

			}
			if (lead.getUserid() > 0) {

				map.put("userid", "Integer");

			}

		}
		return map;

	}

	public List<Map<String, Object>> getAllLead() {
		List<Map<String, Object>> lstMap = new ArrayList<>();
//		List<Map<String, Object>> responseList = new ArrayList<>();
		List<Map<String, Object>> responseMap = new ArrayList<>();

		lstMap = leadRepository.findAllLeads();

		List<Lookup> lstLookup = lookupRepository.findByRelatedTo("LEAD");

		if (!lstLookup.isEmpty()) {
			Map<String, String> map = new HashMap<>();
			for (Lookup lookup : lstLookup) {

				map.put(lookup.getRelatedWith(), lookup.getRelatedWith());
			}

			String taskLookupValue = map.get(LookupName.TASK.toString());
			if (taskLookupValue != null && taskLookupValue.equalsIgnoreCase(LookupName.TASK.toString())) {
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

			String accountLookupValue = map.get(LookupName.ACCOUNT.toString());
			if (accountLookupValue != null && accountLookupValue.equalsIgnoreCase(LookupName.ACCOUNT.toString())) {
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

		Map<String, Object> taskMap = new HashMap<>();
		Map<String, Object> leadMap = new HashMap<>();

		List<Task> taskDataList = taskRepository.findAll();

		leadMap.put("LeadData", lstMap);
		taskMap.put("TaskData", taskDataList);

		responseMap.add(leadMap);
		responseMap.add(taskMap);

		return responseMap;

	}

	private List<Map<String, Object>> checkLookupRelations1(List<Map<String, Object>> responseMap,
			List<Map<String, Object>> lstMap) {

		Map<String, Object> contactMap = new HashMap<>();
		Map<String, Object> leadMap = new HashMap<>();

		List<Contact> contactDataList = contactRepository.findAll();

		leadMap.put("LeadData", lstMap);
		contactMap.put("ContactData", contactDataList);

		responseMap.add(leadMap);
		responseMap.add(contactMap);

		return responseMap;

	}

	private List<Map<String, Object>> checkLookupRelations2(List<Map<String, Object>> responseMap,
			List<Map<String, Object>> lstMap) {

		Map<String, Object> opportunityMap = new HashMap<>();
		Map<String, Object> leadMap = new HashMap<>();

		List<Opportunity> opportunityDataList = opportunityRepository.findAll();

		leadMap.put("LeadData", lstMap);
		opportunityMap.put("OpportunityData", opportunityDataList);

		responseMap.add(leadMap);
		responseMap.add(opportunityMap);

		return responseMap;

	}

	private List<Map<String, Object>> checkLookupRelations3(List<Map<String, Object>> responseMap,
			List<Map<String, Object>> lstMap) {

		Map<String, Object> accountMap = new HashMap<>();
		Map<String, Object> leadMap = new HashMap<>();

		List<Account> accountDataList = accountRepository.findAll();

		leadMap.put("LeadData", lstMap);
		accountMap.put("TaskData", accountDataList);

		responseMap.add(leadMap);
		responseMap.add(accountMap);

		return responseMap;

	}

	private List<Map<String, Object>> checkLookupRelations4(List<Map<String, Object>> responseMap,
			List<Map<String, Object>> lstMap) {

		Map<String, Object> campaignMap = new HashMap<>();
		Map<String, Object> leadMap = new HashMap<>();

		List<Campaign> campaignDataList = campaignRepository.findAll();

		leadMap.put("LeadData", lstMap);
		campaignMap.put("CampaignData", campaignDataList);

		responseMap.add(leadMap);
		responseMap.add(campaignMap);

		return responseMap;

	}

	public Lead getLeadById(Integer id) {
		return leadRepository.findById(id).orElse(null);
	}

	public Lead createLead(Lead lead) {
		return leadRepository.save(lead);
	}

	public List<Lead> findByLeadSource(String leadSource) {
		List<Lead> listLead = leadRepository.findByLeadSourceIgnoreCase(leadSource);
		return listLead;
	}

	public List<Lead> findByLeadStatus(String leadStatus) {
		List<Lead> listLead = leadRepository.findByLeadStatusIgnoreCase(leadStatus);
		return listLead;
	}

	public List<Lead> findByRating(String rating) {
		List<Lead> listLead = leadRepository.findByRatingIgnoreCase(rating);
		return listLead;
	}

	public List<Lead> findByIndustry(String industry) {
		List<Lead> listLead = leadRepository.findByIndustryIgnoreCase(industry);
		return listLead;
	}

	public List<Lead> findByProductInterest(String productInterest) {
		List<Lead> listLead = leadRepository.findByProductInterestIgnoreCase(productInterest);
		return listLead;
	}

	public Lead updateLead(Lead lead) {
		Lead lead1 = leadRepository.findById(lead.getLeadid()).orElse(null);
		lead1.setSalutation(lead.getSalutation());
		lead1.setOwner(lead.getOwner());
		lead1.setFirstName(lead.getFirstName());
		lead1.setLastName(lead.getLastName());
		lead1.setCompany(lead.getCompany());
		lead1.setTitle(lead.getTitle());
		lead1.setCreatedBy(lead.getCreatedBy());
		lead1.setLastModifiedBy(lead.getLastModifiedBy());
		lead1.setLastModifiedDate(lead.getLastModifiedDate());
		lead1.setMobile(lead.getMobile());
		lead1.setPhone(lead.getPhone());
		lead1.setEmail(lead.getEmail());
		lead1.setFax(lead.getFax());
		lead1.setLeadStatus(lead.getLeadStatus());
		lead1.setLeadSource(lead.getLeadSource());
		lead1.setDescription(lead.getDescription());
		lead1.setIndustry(lead.getIndustry());
		lead1.setWebsite(lead.getWebsite());
		lead1.setAnnualRevenue(lead.getAnnualRevenue());
		lead1.setNoOfEmployees(lead.getNoOfEmployees());
		lead1.setRating(lead.getRating());
		lead1.setAddress(lead.getAddress());
		lead1.setStreet(lead.getStreet());
		lead1.setCity(lead.getCity());
		lead1.setState(lead.getState());
		lead1.setZipCode(lead.getZipCode());
		lead1.setCountry(lead.getCountry());
		lead1.setProductInterest(lead.getProductInterest());
		lead1.setCurrentGenerator(lead.getCurrentGenerator());
		lead1.setSicCode(lead.getSicCode());
		lead1.setPrimary(lead.getPrimary());
		lead1.setNumberOfLocations(lead.getNumberOfLocations());
		lead1.setCampaignid(lead.getCampaignid());
		return leadRepository.save(lead1);
	}

	public void deleteLead(Integer id) {
		leadRepository.deleteById(id);
	}

	@Transactional
	public void deleteLeadByIds(List<Integer> leadid) {
		leadRepository.deleteAllByIdInBatch(leadid);
	}

	public void leadSchedulerImpl() {
		List<Lead> leadlist = leadRepository.findAll();

		for (Lead lead : leadlist) {
			String todaysDate = mailUtil.convertDateToString(new Date());

			// check if already exist.

			Optional<EmailActivity> emailActivityRecord = emailActivityRepository
					.findByTableIdAndTableNameAndStage(lead.getLeadid(), "LEAD", "CREATE");
			
			

			// if already not exist then send an email

			sendCreateEmail(emailActivityRecord, lead);

			User user = userRepository.findByUserid(lead.getUserid());

			if (Objects.nonNull(user)) {
				// check if already exist.
				Optional<EmailActivity> emailActivityRecord1 = emailActivityRepository
						.findByTableIdAndTableNameAndStage(lead.getLeadid(), "LEAD", "BIRTHDATE");
				if (!emailActivityRecord1.isPresent()) {
					sendBirthDateEmail(emailActivityRecord1, user, todaysDate, lead);
				}
			}

			String lastModifiedDate = mailUtil.convertDateToString(lead.getLastModifiedDate());
			Long days = mailUtil.calculateDiffBetweenTwoDays(todaysDate, lastModifiedDate);

			Optional<EmailActivity> updateEmailActivity = emailActivityRepository
					.findByTableIdAndTableNameAndStage(lead.getLeadid(), "LEAD", "UPDATE");

			if (!updateEmailActivity.isPresent()) {
				if (days >= 30) {
					sendUpdateEmail(updateEmailActivity, lead);
				}
			}

		}
	}

	private void sendUpdateEmail(Optional<EmailActivity> updateEmailActivity, Lead lead) {
		emailservice.sendEmail(lead.getEmail(), "Update Test", "Update Test Body after one month");

		// save email Records
		Email email = new Email(0, lead.getEmail(), "Update Test", "Update Test Body after one month", new Date(),
				new Date(), lead.getUserid());
		emailRepository.save(email);

		// save Email Activity
		EmailActivity emailActivity = new EmailActivity(0, lead.getLeadid(), "LEAD", "BIRTHDATE", new Date(),
				new Date());
		emailActivityRepository.save(emailActivity);
	}

	private void sendBirthDateEmail(Optional<EmailActivity> emailActivityRecord1, User user, String todaysDate,
			Lead lead) {
		String birthDate = mailUtil.convertDateToString(user.getBirthDate());

		Long birthDays = mailUtil.calculateDiffBetweenTwoDays(todaysDate, birthDate);

		String daysAndMonthToday = mailUtil.getDaysAndMonth(new Date());

		String daysAndMonthBirtDate = mailUtil.getDaysAndMonth(user.getBirthDate());
		if (birthDays >= 365 && daysAndMonthToday.equalsIgnoreCase(daysAndMonthBirtDate)) {
			emailservice.sendEmail(lead.getEmail(), "BirthDay Greetings", "Happy Birthday");

			// save email Records
			Email email = new Email(0, lead.getEmail(), "BirthDay Greetings", "Happy Birthday", new Date(), new Date(),
					lead.getUserid());
			emailRepository.save(email);

			// save Email Activity
			EmailActivity emailActivity = new EmailActivity(0, lead.getLeadid(), "LEAD", "BIRTHDATE", new Date(),
					new Date());
			emailActivityRepository.save(emailActivity);
		}
	}

	private void sendCreateEmail(Optional<EmailActivity> emailActivityRecord, Lead lead) {
		if (!emailActivityRecord.isPresent()) {

			// Long days = mailUtil.calculateDiffBetweenTwoDays(todaysDate, leadDate);

			Integer hours = mailUtil.calculateDiffBetweenTwoHours(new Date(), lead.getCreatedDate());
			if (hours >= 2) {
				emailservice.sendEmail(lead.getEmail(), "SchedularTest", "Schedular Body Test");

				// save email Records
				Email email = new Email(0, lead.getEmail(), "SchedularTest", "Schedular Body Test", new Date(),
						new Date(), lead.getUserid());
				emailRepository.save(email);
				// save Email Activity
				EmailActivity emailActivity = new EmailActivity(0, lead.getLeadid(), "LEAD", "CREATE", new Date(),
						new Date());
				emailActivityRepository.save(emailActivity);

			}
		}

	}

	public String getUserEmailResponse(Integer id) {

		List<Lead> listLead = leadRepository.findByUserid(id);
		if (listLead.isEmpty()) {
			return "email not sent successfully ";
		} else {
			return "email sent successfully ";
		}
	}

	public List<String> getColumnDataTypes() {
		return leadRepository.getColumnDataTypes();
	}
}
