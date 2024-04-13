package com.xabit.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xabit.model.Account;
import com.xabit.model.Campaign;
import com.xabit.model.Contact;
import com.xabit.model.Lead;
import com.xabit.model.Lookup;
import com.xabit.model.LookupRelation;
import com.xabit.model.MasterEntity;
import com.xabit.model.MasterValueEntity;
import com.xabit.model.Opportunity;
import com.xabit.model.Request;
import com.xabit.model.Task;
import com.xabit.repository.AccountRepository;
import com.xabit.repository.CampaignRepository;
import com.xabit.repository.ContactCustomRepository;
import com.xabit.repository.ContactRepository;
import com.xabit.repository.LeadRepository;
import com.xabit.repository.LookupRelationRepository;
import com.xabit.repository.LookupRepository;
import com.xabit.repository.MasterRepository;
import com.xabit.repository.MasterValueRepository;
import com.xabit.repository.OpportunityRepository;
import com.xabit.repository.TaskRepository;
import com.xabit.utility.ContactField;
import com.xabit.utility.ImageException;
import com.xabit.utility.LookupName;
import com.xabit.utility.Operator;

@Service
public class ContactService {
	@Autowired
	private ContactRepository contactRepository;
	@Autowired
	private ContactCustomRepository contactCustomRepository;

	@Autowired
	private AmazonS3 amazonS3;

	@Autowired
	private MasterValueRepository masterValueRepository;

	@Autowired
	private MasterRepository masterRepository;

	@Autowired
	private LookupRepository lookupRepository;

	@Autowired
	private LeadRepository leadRepository;

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private OpportunityRepository opportunityRepository;

	@Autowired
	private CampaignRepository campaignRepository;

	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private LookupRelationRepository lookupRelationRepository;


	public List<Contact> getAllContacts() {
		
		return contactRepository.findAll();
	}
	public List<Map<String, Object>> getAllContact() {
		List<Map<String, Object>> lstMap = new ArrayList<>();
//		List<Map<String, Object>> responseList = new ArrayList<>();
		List<Map<String, Object>> responseMap = new ArrayList<>();

		lstMap = contactRepository.findAllContacts();

		List<Lookup> lstLookup = lookupRepository.findByRelatedTo("CONTACT");

		if (!lstLookup.isEmpty()) {
		    Map<String, String> map = new HashMap<>();
		    for (Lookup lookup : lstLookup) {
		        map.put(lookup.getRelatedWith(), lookup.getRelatedWith());
		    }

		    String leadLookupValue = map.get(LookupName.LEAD.toString());
		    if (leadLookupValue != null && leadLookupValue.equalsIgnoreCase(LookupName.LEAD.toString())) {
		        checkLookupRelations(responseMap, lstMap);
		    }

		    String campaignLookupValue = map.get(LookupName.CAMPAIGN.toString());
		    if (campaignLookupValue != null && campaignLookupValue.equalsIgnoreCase(LookupName.CAMPAIGN.toString())) {
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
		Map<String, Object> contactMap = new HashMap<>();

		List<Lead> leadDataList = leadRepository.findAll();

		contactMap.put("ContactData", lstMap);
		leadMap.put("LeadData", leadDataList);

		responseMap.add(contactMap);
		responseMap.add(leadMap);

		return responseMap;

	}

	
	
	private List<Map<String, Object>> checkLookupRelations1(List<Map<String, Object>> responseMap,
			List<Map<String, Object>> lstMap) {

		Map<String, Object> campaignMap = new HashMap<>();
		Map<String, Object> contactMap = new HashMap<>();

		List<Campaign> contactDataList = campaignRepository.findAll();

		contactMap.put("ContactData", lstMap);
		campaignMap.put("CampaignData", contactDataList);

		responseMap.add(contactMap);
		responseMap.add(campaignMap);

		return responseMap;

	}

	private List<Map<String, Object>> checkLookupRelations2(List<Map<String, Object>> responseMap,
			List<Map<String, Object>> lstMap) {

		Map<String, Object> opportunityMap = new HashMap<>();
		Map<String, Object> contactMap = new HashMap<>();

		List<Opportunity> opportunityDataList = opportunityRepository.findAll();

		contactMap.put("ContactData", lstMap);
		opportunityMap.put("OpportunityData", opportunityDataList);

		responseMap.add(contactMap);
		responseMap.add(opportunityMap);

		return responseMap;

	}

	private List<Map<String, Object>> checkLookupRelations3(List<Map<String, Object>> responseMap,
			List<Map<String, Object>> lstMap) {

		Map<String, Object> taskMap = new HashMap<>();
		Map<String, Object> contactMap = new HashMap<>();

		List<Task> taskDataList = taskRepository.findAll();

		contactMap.put("ContactData", lstMap);
		taskMap.put("TaskData", taskDataList);

		responseMap.add(contactMap);
		responseMap.add(taskMap);

		return responseMap;

	}

	private List<Map<String, Object>> checkLookupRelations4(List<Map<String, Object>> responseMap,
			List<Map<String, Object>> lstMap) {

		Map<String, Object> accountMap = new HashMap<>();
		Map<String, Object> contactMap = new HashMap<>();

		List<Account> accountDataList = accountRepository.findAll();

		contactMap.put("ContactData", lstMap);
		accountMap.put("AccountData", accountDataList);

		responseMap.add(contactMap);
		responseMap.add(accountMap);

		return responseMap;

	}
	
	public List<String> getColumnDataTypes() {
        return contactRepository.getColumnDataTypes();
    }
	
	public Contact getContactById(Integer id) {
		return contactRepository.findById(id).orElse(null);
	}

	public Contact createContact(Contact contact) {

		// billing address - TODO
		/*
		 * String billingAddress=contact.getEmail().concat(contact.getFirstName());
		 * contact.setBiilingAddress(billingAddress);
		 */
		contact.setOwnerAlias(manipulateContactNames(contact));
		contact.setCreatedByAlias(manipulateContactNames(contact)); // Initially set to owner alias
		contact.setLastModifiedByAlias(manipulateContactNames(contact));
		return contactRepository.save(contact);
	}

	public String manipulateContactNames(Contact contact) {
		String lastName = contact.getLastName();

		if (lastName.length() > 6) {
			lastName = lastName.substring(0, 6);
		}

		String firstName = contact.getFirstName().substring(0, 1);

		String modifiedName = lastName + firstName;

		// Check if the alias already exists in the database
		int suffix = 0;
		String aliasToCheck = modifiedName;

		while (isAliasExists(aliasToCheck)) {
			suffix++;
			aliasToCheck = modifiedName + String.format("%02d", suffix);
		}

		contact.setOwnerAlias(aliasToCheck);

		return aliasToCheck;
	}

	private boolean isAliasExists(String ownerAlias) {
		Optional<Contact> existingUser = contactRepository.findByOwnerAlias(ownerAlias);
		return existingUser.isPresent();
	}

	public Contact updateContact(Contact contact) {
		String lastModifiedByAlias = manipulateContactNames(contact);
		contact.setLastModifiedByAlias(lastModifiedByAlias);

		Contact contact1 = contactRepository.findById(contact.getContactid()).orElse(null);
		contact1.setOwner(contact.getOwner());
		contact1.setSalutation(contact.getSalutation());
		contact1.setFirstName(contact.getFirstName());
		contact1.setLastName(contact.getLastName());
		contact1.setCreatedBy(contact.getCreatedBy());
		contact1.setCreatedDate(contact.getCreatedDate());
		contact1.setLastModifiedBy(contact.getLastModifiedBy());
		contact1.setLastModifiedDate(contact.getLastModifiedDate());
		contact1.setTitle(contact.getTitle());
		contact1.setAsstPhone(contact.getPhone());
		contact1.setHomePhone(contact.getHomePhone());
		contact1.setMobile(contact.getMobile());
		contact1.setOtherPhone(contact.getOtherPhone());
		contact1.setBirthDate(contact.getBirthDate());
		contact1.setDepartment(contact.getDepartment());
		contact1.setEmail(contact.getEmail());
		contact1.setEmailOptOut(contact.isEmailOptOut());
		contact1.setFax(contact.getFax());
		contact1.setReportsTo(contact.getReportsTo());
		contact1.setLeadSource(contact.getLeadSource());
		contact1.setAssistant(contact.getAssistant());
		contact1.setAsstPhone(contact.getAsstPhone());
		contact1.setMailingAddress(contact.getMailingAddress());
		contact1.setMailingStreet(contact.getMailingStreet());
		contact1.setMailingCity(contact.getMailingCity());
		contact1.setMailingZipCode(contact.getMailingZipCode());
		contact1.setMailingState(contact.getMailingState());
		contact1.setMailingCountry(contact.getMailingCountry());
		contact1.setOtherAddress(contact.getOtherAddress());
		contact1.setOtherStreet(contact.getOtherStreet());
		contact1.setOtherCity(contact.getOtherCity());
		contact1.setOtherZipCode(contact.getOtherZipCode());
		contact1.setOtherState(contact.getOtherState());
		contact1.setOtherCountry(contact.getOtherCountry());
		contact1.setLanguages(contact1.getLanguages());
		contact1.setLevel(contact.getLevel());
		contact1.setDescription(contact.getDescription());
		contact1.setAccountid(contact.getAccountid());
		contact1.setCampaignid(contact.getCampaignid());
		contact1.setUserid(contact.getUserid());
		contact1.setOrgid(contact.getOrgid());
		contact1.setOwnerAlias(contact.getOwnerAlias());
		contact1.setCreatedByAlias(contact.getCreatedByAlias());
		contact1.setLastModifiedByAlias(contact.getLastModifiedByAlias());
		contact1.setEmailBouncedDate(contact.getEmailBouncedDate());
		contact1.setEmailBouncedReason(contact.getEmailBouncedReason());
		contact1.setIsEmailBounced(contact.getIsEmailBounced());
		contact1.setTopics(contact.getTopics());
		contact1.setImagePath(contact.getImagePath());
		contact1.setImageId(contact.getImageId());

		return contactRepository.save(contact1);
	}

	public Map<String, Object> findByFilter(Request request) {

		List<Map<String, Object>> accountDetails = new ArrayList<>();
		
		Map<String, Object> contactMap = new HashMap<>();

		if (request.getValue() != null && request.getField() != null) {

			if (ContactField.ACCOUNT_ACCOUNT_NUMBER.toString().equalsIgnoreCase(request.getField())) {

				if (Operator.EQUALS.toString().equalsIgnoreCase(request.getOperator())) {
					accountDetails = contactRepository.findByAccountContactaccountNumber(request.getValue());

				} else if (Operator.NOT_EQUALS_TO.toString().equalsIgnoreCase(request.getOperator())) {

					accountDetails = contactRepository
							.findByAccountContactaccountNumberContactNotIn(request.getValue());

				} else if (Operator.CONTAINS.toString().equalsIgnoreCase(request.getOperator())) {

					accountDetails = contactRepository
							.findByAccountContactaccountNumberContainingIgnoreCase(request.getValue());
				}

				else if (Operator.DOES_NOT_CONTAIN.toString().equalsIgnoreCase(request.getOperator())) {

					accountDetails = contactRepository
							.findByAccountContactaccountNumberNotContainingIgnoreCase(request.getValue());
				} else if (Operator.STARTS_WITH.toString().equalsIgnoreCase(request.getOperator())) {

					accountDetails = contactRepository
							.findByAccountContactaccountNumberStartsWithIgnoreCase(request.getValue());
				}
			}

			// account Active

			if (ContactField.ACCOUNT_ACTIVE.toString().equalsIgnoreCase(request.getField().toString())) {

				if (Operator.EQUALS.toString().equalsIgnoreCase(request.getOperator())) {
					accountDetails = contactRepository.findByAccountContactActive(Boolean.valueOf(request.getValue()));

				} else if (Operator.NOT_EQUALS_TO.toString().equalsIgnoreCase(request.getOperator())) {
					accountDetails = contactRepository.findByAccountContactActive(Boolean.valueOf(request.getValue()));

				}

			}

			// accountSource
			if (ContactField.ACCOUNT_ACCOUNT_SOURCE.toString().equalsIgnoreCase(request.getField())) {

				if (Operator.CONTAINS.toString().equalsIgnoreCase(request.getOperator())) {
					accountDetails = contactRepository
							.findByAccountContactaccountSourceContainingIgnoreCase(request.getValue());
				} else if (Operator.DOES_NOT_CONTAIN.toString().equalsIgnoreCase(request.getOperator())) {

					accountDetails = contactRepository
							.findByAccountContactaccountSourceNotContainingIgnoreCase(request.getValue());
				} else if (Operator.STARTS_WITH.toString().equalsIgnoreCase(request.getOperator())) {

					accountDetails = contactRepository
							.findByAccountContactaccountSourceStartsWithIgnoreCase(request.getValue());
				}

			}

			// billing_city
			if (ContactField.ACCOUNT_BILLING_CITY.toString().equalsIgnoreCase(request.getField())) {

				if (Operator.CONTAINS.toString().equalsIgnoreCase(request.getOperator())) {
					accountDetails = contactRepository
							.findByAccountContactbillingCityContainingIgnoreCase(request.getValue());
				} else if (Operator.DOES_NOT_CONTAIN.toString().equalsIgnoreCase(request.getOperator())) {

					accountDetails = contactRepository
							.findByAccountContactbillingCityNotContainingIgnoreCase(request.getValue());
				} else if (Operator.STARTS_WITH.toString().equalsIgnoreCase(request.getOperator())) {

					accountDetails = contactRepository
							.findByAccountContactbillingCityStartsWithIgnoreCase(request.getValue());
				}

			}
			// billingCountry
			if (ContactField.ACCOUNT_BILLING_COUNTRY.toString().equalsIgnoreCase(request.getField())) {

				if (Operator.CONTAINS.toString().equalsIgnoreCase(request.getOperator())) {
					accountDetails = contactRepository
							.findByAccountContactbillingCountryContainingIgnoreCase(request.getValue());
				} else if (Operator.DOES_NOT_CONTAIN.toString().equalsIgnoreCase(request.getOperator())) {

					accountDetails = contactRepository
							.findByAccountContactbillingCountryNotContainingIgnoreCase(request.getValue());
				} else if (Operator.STARTS_WITH.toString().equalsIgnoreCase(request.getOperator())) {

					accountDetails = contactRepository
							.findByAccountContactbillingCountryStartsWithIgnoreCase(request.getValue());
				}

			}
//			
			// billingState
			if (ContactField.ACCOUNT_BILLING_STATE_PROVINCE.toString().equalsIgnoreCase(request.getField())) {

				if (Operator.CONTAINS.toString().equalsIgnoreCase(request.getOperator())) {
					accountDetails = contactRepository
							.findByAccountContactbillingStateContainingIgnoreCase(request.getValue());
				} else if (Operator.DOES_NOT_CONTAIN.toString().equalsIgnoreCase(request.getOperator())) {

					accountDetails = contactRepository
							.findByAccountContactbillingStateNotContainingIgnoreCase(request.getValue());
				} else if (Operator.STARTS_WITH.toString().equalsIgnoreCase(request.getOperator())) {

					accountDetails = contactRepository
							.findByAccountContactbillingStateStartsWithIgnoreCase(request.getValue());
				}

			}
			// billingStreet
			if (ContactField.ACCOUNT_BILLING_STREET.toString().equalsIgnoreCase(request.getField())) {

				if (Operator.CONTAINS.toString().equalsIgnoreCase(request.getOperator())) {
					accountDetails = contactRepository
							.findByAccountContactbillingStreetContainingIgnoreCase(request.getValue());
				} else if (Operator.DOES_NOT_CONTAIN.toString().equalsIgnoreCase(request.getOperator())) {

					accountDetails = contactRepository
							.findByAccountContactbillingStreetNotContainingIgnoreCase(request.getValue());
				} else if (Operator.STARTS_WITH.toString().equalsIgnoreCase(request.getOperator())) {

					accountDetails = contactRepository
							.findByAccountContactbillingStreetStartsWithIgnoreCase(request.getValue());
				}

			}

			// customerPriority
			if (ContactField.ACCOUNT_CUSTOMER_PRIORITY.toString().equalsIgnoreCase(request.getField())) {

				if (Operator.CONTAINS.toString().equalsIgnoreCase(request.getOperator())) {
					accountDetails = contactRepository
							.findByAccountContactcustomerPriorityContainingIgnoreCase(request.getValue());
				} else if (Operator.DOES_NOT_CONTAIN.toString().equalsIgnoreCase(request.getOperator())) {

					accountDetails = contactRepository
							.findByAccountContactcustomerPriorityNotContainingIgnoreCase(request.getValue());
				} else if (Operator.STARTS_WITH.toString().equalsIgnoreCase(request.getOperator())) {

					accountDetails = contactRepository
							.findByAccountContactcustomerPriorityStartsWithIgnoreCase(request.getValue());
				}

			}
			// fax
			if (ContactField.ACCOUNT_FAX.toString().equalsIgnoreCase(request.getField())) {

				if (Operator.CONTAINS.toString().equalsIgnoreCase(request.getOperator())) {
					accountDetails = contactRepository.findByAccountContactFaxContainingIgnoreCase(request.getValue());
				} else if (Operator.DOES_NOT_CONTAIN.toString().equalsIgnoreCase(request.getOperator())) {

					accountDetails = contactRepository
							.findByAccountContactFaxNotContainingIgnoreCase(request.getValue());
				} else if (Operator.STARTS_WITH.toString().equalsIgnoreCase(request.getOperator())) {

					accountDetails = contactRepository.findByAccountContactFaxStartsWithIgnoreCase(request.getValue());
				}

			}
			// industry
			if (ContactField.ACCOUNT_INDUSTRY.toString().equalsIgnoreCase(request.getField())) {

				if (Operator.CONTAINS.toString().equalsIgnoreCase(request.getOperator())) {
					accountDetails = contactRepository
							.findByAccountContactIndustryContainingIgnoreCase(request.getValue());
				} else if (Operator.DOES_NOT_CONTAIN.toString().equalsIgnoreCase(request.getOperator())) {

					accountDetails = contactRepository
							.findByAccountContactIndustryNotContainingIgnoreCase(request.getValue());
				} else if (Operator.STARTS_WITH.toString().equalsIgnoreCase(request.getOperator())) {

					accountDetails = contactRepository
							.findByAccountContactIndustryStartsWithIgnoreCase(request.getValue());
				}

			}
			// ownership
			if (ContactField.ACCOUNT_OWNERSHIP.toString().equalsIgnoreCase(request.getField())) {

				if (Operator.CONTAINS.toString().equalsIgnoreCase(request.getOperator())) {
					accountDetails = contactRepository
							.findByAccountContactOwnershipContainingIgnoreCase(request.getValue());
				} else if (Operator.DOES_NOT_CONTAIN.toString().equalsIgnoreCase(request.getOperator())) {

					accountDetails = contactRepository
							.findByAccountContactOwnershipNotContainingIgnoreCase(request.getValue());
				} else if (Operator.STARTS_WITH.toString().equalsIgnoreCase(request.getOperator())) {

					accountDetails = contactRepository
							.findByAccountContactOwnershipStartsWithIgnoreCase(request.getValue());
				}

			}
			// rating
			if (ContactField.ACCOUNT_RATING.toString().equalsIgnoreCase(request.getField())) {

				if (Operator.CONTAINS.toString().equalsIgnoreCase(request.getOperator())) {
					accountDetails = contactRepository
							.findByAccountContactRatingContainingIgnoreCase(request.getValue());
				} else if (Operator.DOES_NOT_CONTAIN.toString().equalsIgnoreCase(request.getOperator())) {

					accountDetails = contactRepository
							.findByAccountContactRatingNotContainingIgnoreCase(request.getValue());
				} else if (Operator.STARTS_WITH.toString().equalsIgnoreCase(request.getOperator())) {

					accountDetails = contactRepository
							.findByAccountContactRatingStartsWithIgnoreCase(request.getValue());
				}

			}
			// shippingCity
			if (ContactField.ACCOUNT_SHIPPING_CITY.toString().equalsIgnoreCase(request.getField())) {

				if (Operator.CONTAINS.toString().equalsIgnoreCase(request.getOperator())) {
					accountDetails = contactRepository
							.findByAccountContactshippingCityContainingIgnoreCase(request.getValue());
				} else if (Operator.DOES_NOT_CONTAIN.toString().equalsIgnoreCase(request.getOperator())) {

					accountDetails = contactRepository
							.findByAccountContactshippingCityNotContainingIgnoreCase(request.getValue());
				} else if (Operator.STARTS_WITH.toString().equalsIgnoreCase(request.getOperator())) {

					accountDetails = contactRepository
							.findByAccountContactshippingCityStartsWithIgnoreCase(request.getValue());
				}

			}

			// shippingCountry shipping_country
			if (ContactField.ACCOUNT_SHIPPING_COUNTRY.toString().equalsIgnoreCase(request.getField())) {

				if (Operator.CONTAINS.toString().equalsIgnoreCase(request.getOperator())) {
					accountDetails = contactRepository
							.findByAccountContactshippingCountryContainingIgnoreCase(request.getValue());
				} else if (Operator.DOES_NOT_CONTAIN.toString().equalsIgnoreCase(request.getOperator())) {

					accountDetails = contactRepository
							.findByAccountContactshippingCountryNotContainingIgnoreCase(request.getValue());
				} else if (Operator.STARTS_WITH.toString().equalsIgnoreCase(request.getOperator())) {

					accountDetails = contactRepository
							.findByAccountContactshippingCountryStartsWithIgnoreCase(request.getValue());
				}

			}

			// shippingStreet shipping_street
			if (ContactField.ACCOUNT_SHIPPING_STREET.toString().equalsIgnoreCase(request.getField())) {

				if (Operator.CONTAINS.toString().equalsIgnoreCase(request.getOperator())) {
					accountDetails = contactRepository
							.findByAccountContactshippingStreetContainingIgnoreCase(request.getValue());
				} else if (Operator.DOES_NOT_CONTAIN.toString().equalsIgnoreCase(request.getOperator())) {

					accountDetails = contactRepository
							.findByAccountContactshippingStreetNotContainingIgnoreCase(request.getValue());
				} else if (Operator.STARTS_WITH.toString().equalsIgnoreCase(request.getOperator())) {

					accountDetails = contactRepository
							.findByAccountContactshippingStreetStartsWithIgnoreCase(request.getValue());
				}

			}

			// shippingState shipping_state

			if (ContactField.ACCOUNT_SHIPPING_STATE_PROVINCE.toString().equalsIgnoreCase(request.getField())) {

				if (Operator.CONTAINS.toString().equalsIgnoreCase(request.getOperator())) {
					accountDetails = contactRepository
							.findByAccountContactshippingStateContainingIgnoreCase(request.getValue());
				} else if (Operator.DOES_NOT_CONTAIN.toString().equalsIgnoreCase(request.getOperator())) {

					accountDetails = contactRepository
							.findByAccountContactshippingStateNotContainingIgnoreCase(request.getValue());
				} else if (Operator.STARTS_WITH.toString().equalsIgnoreCase(request.getOperator())) {

					accountDetails = contactRepository
							.findByAccountContactshippingStateStartsWithIgnoreCase(request.getValue());
				}

			}

			// sla sla
			if (ContactField.ACCOUNT_SLA.toString().equalsIgnoreCase(request.getField())) {

				if (Operator.CONTAINS.toString().equalsIgnoreCase(request.getOperator())) {
					accountDetails = contactRepository.findByAccountContactSlaContainingIgnoreCase(request.getValue());
				} else if (Operator.DOES_NOT_CONTAIN.toString().equalsIgnoreCase(request.getOperator())) {

					accountDetails = contactRepository
							.findByAccountContactSlaNotContainingIgnoreCase(request.getValue());
				} else if (Operator.STARTS_WITH.toString().equalsIgnoreCase(request.getOperator())) {

					accountDetails = contactRepository.findByAccountContactSlaStartsWithIgnoreCase(request.getValue());
				}

			}

			// tickerSymbol ticker_symbol
			if (ContactField.ACCOUNT_TICKER_SYMBOL.toString().equalsIgnoreCase(request.getField())) {

				if (Operator.CONTAINS.toString().equalsIgnoreCase(request.getOperator())) {
					accountDetails = contactRepository
							.findByAccountContactTickerSymbolContainingIgnoreCase(request.getValue());
				} else if (Operator.DOES_NOT_CONTAIN.toString().equalsIgnoreCase(request.getOperator())) {

					accountDetails = contactRepository
							.findByAccountContactTickerSymbolNotContainingIgnoreCase(request.getValue());
				} else if (Operator.STARTS_WITH.toString().equalsIgnoreCase(request.getOperator())) {

					accountDetails = contactRepository
							.findByAccountContactTickerSymbolStartsWithIgnoreCase(request.getValue());
				}

			}

			// accountType account_type
			if (ContactField.ACCOUNT_TYPE.toString().equalsIgnoreCase(request.getField())) {

				if (Operator.CONTAINS.toString().equalsIgnoreCase(request.getOperator())) {
					accountDetails = contactRepository
							.findByAccountContactaccountTypeContainingIgnoreCase(request.getValue());
				} else if (Operator.DOES_NOT_CONTAIN.toString().equalsIgnoreCase(request.getOperator())) {

					accountDetails = contactRepository
							.findByAccountContactaccountTypeNotContainingIgnoreCase(request.getValue());
				} else if (Operator.STARTS_WITH.toString().equalsIgnoreCase(request.getOperator())) {

					accountDetails = contactRepository
							.findByAccountContactaccountTypeStartsWithIgnoreCase(request.getValue());
				}

			}

			// description description
			if (ContactField.ACCOUNT_DESCRIPTION.toString().equalsIgnoreCase(request.getField())) {

				if (Operator.CONTAINS.toString().equalsIgnoreCase(request.getOperator())) {
					accountDetails = contactRepository
							.findByAccountContactDescriptionContainingIgnoreCase(request.getValue());
				} else if (Operator.DOES_NOT_CONTAIN.toString().equalsIgnoreCase(request.getOperator())) {

					accountDetails = contactRepository
							.findByAccountContactDescriptionNotContainingIgnoreCase(request.getValue());
				} else if (Operator.STARTS_WITH.toString().equalsIgnoreCase(request.getOperator())) {

					accountDetails = contactRepository
							.findByAccountContactDescriptionStartsWithIgnoreCase(request.getValue());
				}

			}

			// upsellOpportunity upsell_opportunity
			if (ContactField.ACCOUNT_UPSELL_OPPORTUNITY.toString().equalsIgnoreCase(request.getField())) {

				if (Operator.CONTAINS.toString().equalsIgnoreCase(request.getOperator())) {
					accountDetails = contactRepository
							.findByAccountContactupsellOpportunityContainingIgnoreCase(request.getValue());
				} else if (Operator.DOES_NOT_CONTAIN.toString().equalsIgnoreCase(request.getOperator())) {

					accountDetails = contactRepository
							.findByAccountContactupsellOpportunityNotContainingIgnoreCase(request.getValue());
				} else if (Operator.STARTS_WITH.toString().equalsIgnoreCase(request.getOperator())) {

					accountDetails = contactRepository
							.findByAccountContactupsellOpportunityStartsWithIgnoreCase(request.getValue());
				}

			}

			// website website
			if (ContactField.ACCOUNT_WEBSITE.toString().equalsIgnoreCase(request.getField())) {

				if (Operator.CONTAINS.toString().equalsIgnoreCase(request.getOperator())) {
					accountDetails = contactRepository
							.findByAccountContactWebsiteContainingIgnoreCase(request.getValue());
				} else if (Operator.DOES_NOT_CONTAIN.toString().equalsIgnoreCase(request.getOperator())) {

					accountDetails = contactRepository
							.findByAccountContactWebsiteNotContainingIgnoreCase(request.getValue());
				} else if (Operator.STARTS_WITH.toString().equalsIgnoreCase(request.getOperator())) {

					accountDetails = contactRepository
							.findByAccountContactWebsiteStartsWithIgnoreCase(request.getValue());
				}

			}

			// yearStarted year_started
			if (ContactField.ACCOUNT_YEAR_STARTED.toString().equalsIgnoreCase(request.getField())) {

				if (Operator.CONTAINS.toString().equalsIgnoreCase(request.getOperator())) {
					accountDetails = contactRepository
							.findByAccountContactyearStartedContainingIgnoreCase(request.getValue());
				} else if (Operator.DOES_NOT_CONTAIN.toString().equalsIgnoreCase(request.getOperator())) {

					accountDetails = contactRepository
							.findByAccountContactyearStartedNotContainingIgnoreCase(request.getValue());
				} else if (Operator.STARTS_WITH.toString().equalsIgnoreCase(request.getOperator())) {

					accountDetails = contactRepository
							.findByAccountContactyearStartedStartsWithIgnoreCase(request.getValue());
				}

			}

			// accountName account_name
			if (ContactField.ACCOUNT_NAME.toString().equalsIgnoreCase(request.getField())) {

				if (Operator.CONTAINS.toString().equalsIgnoreCase(request.getOperator())) {
					accountDetails = contactRepository
							.findByAccountContactaccountNameContainingIgnoreCase(request.getValue());
				} else if (Operator.DOES_NOT_CONTAIN.toString().equalsIgnoreCase(request.getOperator())) {

					accountDetails = contactRepository
							.findByAccountContactaccountNameNotContainingIgnoreCase(request.getValue());
				} else if (Operator.STARTS_WITH.toString().equalsIgnoreCase(request.getOperator())) {

					accountDetails = contactRepository
							.findByAccountContactaccountNameStartsWithIgnoreCase(request.getValue());
				}

			}

			// accountSite account_site
			if (ContactField.ACCOUNT_SITE.toString().equalsIgnoreCase(request.getField())) {

				if (Operator.CONTAINS.toString().equalsIgnoreCase(request.getOperator())) {
					accountDetails = contactRepository
							.findByAccountContactaccountSiteContainingIgnoreCase(request.getValue());
				} else if (Operator.DOES_NOT_CONTAIN.toString().equalsIgnoreCase(request.getOperator())) {

					accountDetails = contactRepository
							.findByAccountContactaccountSiteNotContainingIgnoreCase(request.getValue());
				} else if (Operator.STARTS_WITH.toString().equalsIgnoreCase(request.getOperator())) {

					accountDetails = contactRepository
							.findByAccountContactaccountSiteStartsWithIgnoreCase(request.getValue());
				}

			}
			// ---------------------------------------------------------------------------------------------------------
			// annualRevenue(long)
			if (ContactField.ACCOUNT_ANNUAL_REVENUE.toString().equalsIgnoreCase(request.getField())) {

				if (Operator.GREATER_THAN.toString().equalsIgnoreCase(request.getOperator())) {
					Long longValue = Long.parseLong(request.getValue());
					accountDetails = contactRepository.findByAccountContactAnnualRevenueGreaterThan(request.getValue());

				} else if (Operator.LESS_THAN.toString().equalsIgnoreCase(request.getOperator())) {
					Long longValue = Long.parseLong(request.getValue());
					accountDetails = contactRepository.findByAccountContactAnnualRevenueLessThan(request.getValue());

				} else if (Operator.EQUALS.toString().equalsIgnoreCase(request.getOperator())) {
					Long longValue = Long.parseLong(request.getValue());
					accountDetails = contactRepository.findByAccountContactAnnualRevenue(request.getValue());

				} else if (Operator.NOT_EQUALS_TO.toString().equalsIgnoreCase(request.getOperator())) {
					Long longValue = Long.parseLong(request.getValue());
					accountDetails = contactRepository.findByAccountContactAnnualRevenueNot(request.getValue());

				} else if (Operator.GREATER_OR_EQUAL.toString().equalsIgnoreCase(request.getOperator())) {
					Long longValue = Long.parseLong(request.getValue());
					accountDetails = contactRepository
							.findByAccountContactAnnualRevenueGreaterThanEqual(request.getValue());

				} else if (Operator.LESS_OR_EQUAL.toString().equalsIgnoreCase(request.getOperator())) {
					Long longValue = Long.parseLong(request.getValue());
					accountDetails = contactRepository
							.findByAccountContactAnnualRevenueLessThanEqual(request.getValue());

				}
			}

			// billingZipCode(Integer) billing_zip_code
			if (ContactField.ACCOUNT_BILLING_ZIP_POSTAL_CODE.toString().equalsIgnoreCase(request.getField())) {

				if (Operator.GREATER_THAN.toString().equalsIgnoreCase(request.getOperator())) {
					Integer integerValue = Integer.parseInt(request.getValue());
					accountDetails = contactRepository
							.findByAccountContactbillingZipCodeGreaterThan(request.getValue());

				} else if (Operator.LESS_THAN.toString().equalsIgnoreCase(request.getOperator())) {
					Integer integerValue = Integer.parseInt(request.getValue());
					accountDetails = contactRepository.findByAccountContactbillingZipCodeLessThan(request.getValue());

				} else if (Operator.EQUALS.toString().equalsIgnoreCase(request.getOperator())) {
					Integer integerValue = Integer.parseInt(request.getValue());
					accountDetails = contactRepository.findByAccountContactbillingZipCode(request.getValue());

				} else if (Operator.NOT_EQUALS_TO.toString().equalsIgnoreCase(request.getOperator())) {
					Integer integerValue = Integer.parseInt(request.getValue());
					accountDetails = contactRepository.findByAccountContactbillingZipCodeNot(request.getValue());

				} else if (Operator.GREATER_OR_EQUAL.toString().equalsIgnoreCase(request.getOperator())) {
					Integer integerValue = Integer.parseInt(request.getValue());
					accountDetails = contactRepository
							.findByAccountContactbillingZipCodeGreaterThanEqual(request.getValue());

				} else if (Operator.LESS_OR_EQUAL.toString().equalsIgnoreCase(request.getOperator())) {
					Integer integerValue = Integer.parseInt(request.getValue());
					accountDetails = contactRepository
							.findByAccountContactbillingZipCodeLessThanEqual(request.getValue());

				}
			}

			// shippingZipCode(Integer) shipping_zip_code
			if (ContactField.ACCOUNT_SHIPPING_ZIP_POSTAL_CODE.toString().equalsIgnoreCase(request.getField())) {

				if (Operator.GREATER_THAN.toString().equalsIgnoreCase(request.getOperator())) {
					Integer integerValue = Integer.parseInt(request.getValue());
					accountDetails = contactRepository
							.findByAccountContactshippingZipCodeGreaterThan(request.getValue());

				} else if (Operator.LESS_THAN.toString().equalsIgnoreCase(request.getOperator())) {
					Integer integerValue = Integer.parseInt(request.getValue());
					accountDetails = contactRepository.findByAccountContactshippingZipCodeLessThan(request.getValue());

				} else if (Operator.EQUALS.toString().equalsIgnoreCase(request.getOperator())) {
					Integer integerValue = Integer.parseInt(request.getValue());
					accountDetails = contactRepository.findByAccountContactshippingZipCode(request.getValue());

				} else if (Operator.NOT_EQUALS_TO.toString().equalsIgnoreCase(request.getOperator())) {
					Integer integerValue = Integer.parseInt(request.getValue());
					accountDetails = contactRepository.findByAccountContactshippingZipCodeNot(request.getValue());

				} else if (Operator.GREATER_OR_EQUAL.toString().equalsIgnoreCase(request.getOperator())) {
					Integer integerValue = Integer.parseInt(request.getValue());
					accountDetails = contactRepository
							.findByAccountContactshippingZipCodeGreaterThanEqual(request.getValue());

				} else if (Operator.LESS_OR_EQUAL.toString().equalsIgnoreCase(request.getOperator())) {
					Integer integerValue = Integer.parseInt(request.getValue());
					accountDetails = contactRepository
							.findByAccountContactshippingZipCodeLessThanEqual(request.getValue());

				}
			}

			// employees(Integer) employees
			if (ContactField.ACCOUNT_EMPLOYEES.toString().equalsIgnoreCase(request.getField())) {

				if (Operator.GREATER_THAN.toString().equalsIgnoreCase(request.getOperator())) {
					Integer integerValue = Integer.parseInt(request.getValue());
					accountDetails = contactRepository.findByAccountContactEmployeesGreaterThan(request.getValue());

				} else if (Operator.LESS_THAN.toString().equalsIgnoreCase(request.getOperator())) {
					Integer integerValue = Integer.parseInt(request.getValue());
					accountDetails = contactRepository.findByAccountContactEmployeesLessThan(request.getValue());

				} else if (Operator.EQUALS.toString().equalsIgnoreCase(request.getOperator())) {
					Integer integerValue = Integer.parseInt(request.getValue());
					accountDetails = contactRepository.findByAccountContactEmployees(request.getValue());

				} else if (Operator.NOT_EQUALS_TO.toString().equalsIgnoreCase(request.getOperator())) {
					Integer integerValue = Integer.parseInt(request.getValue());
					accountDetails = contactRepository.findByAccountContactEmployeesNot(request.getValue());

				} else if (Operator.GREATER_OR_EQUAL.toString().equalsIgnoreCase(request.getOperator())) {
					Integer integerValue = Integer.parseInt(request.getValue());
					accountDetails = contactRepository
							.findByAccountContactEmployeesGreaterThanEqual(request.getValue());

				} else if (Operator.LESS_OR_EQUAL.toString().equalsIgnoreCase(request.getOperator())) {
					Integer integerValue = Integer.parseInt(request.getValue());
					accountDetails = contactRepository.findByAccountContactEmployeesLessThanEqual(request.getValue());

				}
			}
			// numberOfLocation(Integer) number_of_location
			if (ContactField.ACCOUNT_NUMBER_OF_LOCATIONS.toString().equalsIgnoreCase(request.getField())) {

				if (Operator.GREATER_THAN.toString().equalsIgnoreCase(request.getOperator())) {
					Integer integerValue = Integer.parseInt(request.getValue());
					accountDetails = contactRepository
							.findByAccountContactnumberOfLocationGreaterThan(request.getValue());

				} else if (Operator.LESS_THAN.toString().equalsIgnoreCase(request.getOperator())) {
					Integer integerValue = Integer.parseInt(request.getValue());
					accountDetails = contactRepository.findByAccountContactnumberOfLocationLessThan(request.getValue());

				} else if (Operator.EQUALS.toString().equalsIgnoreCase(request.getOperator())) {
					Integer integerValue = Integer.parseInt(request.getValue());
					accountDetails = contactRepository.findByAccountContactnumberOfLocation(request.getValue());

				} else if (Operator.NOT_EQUALS_TO.toString().equalsIgnoreCase(request.getOperator())) {
					Integer integerValue = Integer.parseInt(request.getValue());
					accountDetails = contactRepository.findByAccountContactnumberOfLocationNot(request.getValue());

				} else if (Operator.GREATER_OR_EQUAL.toString().equalsIgnoreCase(request.getOperator())) {
					Integer integerValue = Integer.parseInt(request.getValue());
					accountDetails = contactRepository
							.findByAccountContactnumberOfLocationGreaterThanEqual(request.getValue());

				} else if (Operator.LESS_OR_EQUAL.toString().equalsIgnoreCase(request.getOperator())) {
					Integer integerValue = Integer.parseInt(request.getValue());
					accountDetails = contactRepository
							.findByAccountContactnumberOfLocationLessThanEqual(request.getValue());

				}
			}
			// phone(long) phone
			if (ContactField.ACCOUNT_PHONE.toString().equalsIgnoreCase(request.getField())) {

				if (Operator.GREATER_THAN.toString().equalsIgnoreCase(request.getOperator())) {
					Long longValue = Long.parseLong(request.getValue());
					accountDetails = contactRepository.findByAccountContactPhoneGreaterThan(request.getValue());

				} else if (Operator.LESS_THAN.toString().equalsIgnoreCase(request.getOperator())) {
					Long longValue = Long.parseLong(request.getValue());
					accountDetails = contactRepository.findByAccountContactPhoneLessThan(request.getValue());

				} else if (Operator.EQUALS.toString().equalsIgnoreCase(request.getOperator())) {
					Long longValue = Long.parseLong(request.getValue());
					accountDetails = contactRepository.findByAccountContactPhone(request.getValue());

				} else if (Operator.NOT_EQUALS_TO.toString().equalsIgnoreCase(request.getOperator())) {
					Long longValue = Long.parseLong(request.getValue());
					accountDetails = contactRepository.findByAccountContactPhoneNot(request.getValue());

				} else if (Operator.GREATER_OR_EQUAL.toString().equalsIgnoreCase(request.getOperator())) {
					Long longValue = Long.parseLong(request.getValue());
					accountDetails = contactRepository.findByAccountContactPhoneGreaterThanEqual(request.getValue());

				} else if (Operator.LESS_OR_EQUAL.toString().equalsIgnoreCase(request.getOperator())) {
					Long longValue = Long.parseLong(request.getValue());
					accountDetails = contactRepository.findByAccountContactPhoneLessThanEqual(request.getValue());

				}
			}

			// sicCode(long) sic_code
			if (ContactField.ACCOUNT_SIC_CODE.toString().equalsIgnoreCase(request.getField())) {

				if (Operator.GREATER_THAN.toString().equalsIgnoreCase(request.getOperator())) {
					Long longValue = Long.parseLong(request.getValue());
					accountDetails = contactRepository.findByAccountContactsicCodeGreaterThan(request.getValue());

				} else if (Operator.LESS_THAN.toString().equalsIgnoreCase(request.getOperator())) {
					Long longValue = Long.parseLong(request.getValue());
					accountDetails = contactRepository.findByAccountContactsicCodeLessThan(request.getValue());

				} else if (Operator.EQUALS.toString().equalsIgnoreCase(request.getOperator())) {
					Long longValue = Long.parseLong(request.getValue());
					accountDetails = contactRepository.findByAccountContactsicCode(request.getValue());

				} else if (Operator.NOT_EQUALS_TO.toString().equalsIgnoreCase(request.getOperator())) {
					Long longValue = Long.parseLong(request.getValue());
					accountDetails = contactRepository.findByAccountContactsicCodeNot(request.getValue());

				} else if (Operator.GREATER_OR_EQUAL.toString().equalsIgnoreCase(request.getOperator())) {
					Long longValue = Long.parseLong(request.getValue());
					accountDetails = contactRepository.findByAccountContactsicCodeGreaterThanEqual(request.getValue());

				} else if (Operator.LESS_OR_EQUAL.toString().equalsIgnoreCase(request.getOperator())) {
					Long longValue = Long.parseLong(request.getValue());
					accountDetails = contactRepository.findByAccountContactsicCodeLessThanEqual(request.getValue());

				}
			}

			// slaSerialNumber(Integer) sla_serial_number
			if (ContactField.ACCOUNT_SLA_SERIAL_NUMBER.toString().equalsIgnoreCase(request.getField())) {

				if (Operator.GREATER_THAN.toString().equalsIgnoreCase(request.getOperator())) {
					Integer integerValue = Integer.parseInt(request.getValue());
					accountDetails = contactRepository
							.findByAccountContactslaSerialNumberGreaterThan(request.getValue());

				} else if (Operator.LESS_THAN.toString().equalsIgnoreCase(request.getOperator())) {
					Integer integerValue = Integer.parseInt(request.getValue());
					accountDetails = contactRepository.findByAccountContactslaSerialNumberLessThan(request.getValue());

				} else if (Operator.EQUALS.toString().equalsIgnoreCase(request.getOperator())) {
					Integer integerValue = Integer.parseInt(request.getValue());
					accountDetails = contactRepository.findByAccountContactslaSerialNumber(request.getValue());

				} else if (Operator.NOT_EQUALS_TO.toString().equalsIgnoreCase(request.getOperator())) {
					Integer integerValue = Integer.parseInt(request.getValue());
					accountDetails = contactRepository.findByAccountContactslaSerialNumberNot(request.getValue());

				} else if (Operator.GREATER_OR_EQUAL.toString().equalsIgnoreCase(request.getOperator())) {
					Integer integerValue = Integer.parseInt(request.getValue());
					accountDetails = contactRepository
							.findByAccountContactslaSerialNumberGreaterThanEqual(request.getValue());

				} else if (Operator.LESS_OR_EQUAL.toString().equalsIgnoreCase(request.getOperator())) {
					Integer integerValue = Integer.parseInt(request.getValue());
					accountDetails = contactRepository
							.findByAccountContactslaSerialNumberLessThanEqual(request.getValue());

				}
			}

			// slaExpirationDate(Date) sla_expiration_date

			if (ContactField.ACCOUNT_SLA_EXPIRATION_DATE.toString().equalsIgnoreCase(request.getField())) {

				if (Operator.GREATER_THAN.toString().equalsIgnoreCase(request.getOperator())) {

					accountDetails = contactRepository
							.findByAccountContactslaExpirationDateGreaterThan(request.getValue());

					try {
						if (request.getValue().equalsIgnoreCase("yesterday")) {
							Calendar cal = Calendar.getInstance();
							cal.add(Calendar.DATE, -1);
							Date slaExpirationDate = cal.getTime();
							accountDetails = contactRepository
									.findByAccountContactslaExpirationDateGreaterThan(request.getValue());

						} else if (request.getValue().equalsIgnoreCase("today")) {
							Date slaExpirationDate = new Date();
							accountDetails = contactRepository
									.findByAccountContactslaExpirationDateGreaterThan(request.getValue());
						} else if (request.getValue().equalsIgnoreCase("tomorrow")) {
							Calendar cal = Calendar.getInstance();
							cal.add(Calendar.DATE, 1);
							Date slaExpirationDate = cal.getTime();
							accountDetails = contactRepository
									.findByAccountContactslaExpirationDateGreaterThan(request.getValue());
						} else {
							Date slaExpirationDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getValue());
							accountDetails = contactRepository
									.findByAccountContactslaExpirationDateGreaterThan(request.getValue());
						}

					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return null;
				}

				else if (Operator.LESS_THAN.toString().equalsIgnoreCase(request.getOperator())) {

					accountDetails = contactRepository
							.findByAccountContactslaExpirationDateLessThan(request.getValue());

					try {
						if (request.getValue().equalsIgnoreCase("yesterday")) {
							Calendar cal = Calendar.getInstance();
							cal.add(Calendar.DATE, -1);
							Date slaExpirationDate = cal.getTime();
							accountDetails = contactRepository
									.findByAccountContactslaExpirationDateLessThan(request.getValue());

						} else if (request.getValue().equalsIgnoreCase("today")) {
							Date slaExpirationDate = new Date();
							accountDetails = contactRepository
									.findByAccountContactslaExpirationDateLessThan(request.getValue());
						} else if (request.getValue().equalsIgnoreCase("tomorrow")) {
							Calendar cal = Calendar.getInstance();
							cal.add(Calendar.DATE, 1);
							Date slaExpirationDate = cal.getTime();
							accountDetails = contactRepository
									.findByAccountContactslaExpirationDateLessThan(request.getValue());
						} else {
							Date slaExpirationDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getValue());
							accountDetails = contactRepository
									.findByAccountContactslaExpirationDateLessThan(request.getValue());
						}

					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return null;
				}

				else if (Operator.LESS_THAN.toString().equalsIgnoreCase(request.getOperator())) {

					accountDetails = contactRepository
							.findByAccountContactslaExpirationDateLessThan(request.getValue());

					try {
						if (request.getValue().equalsIgnoreCase("yesterday")) {
							Calendar cal = Calendar.getInstance();
							cal.add(Calendar.DATE, -1);
							Date slaExpirationDate = cal.getTime();
							accountDetails = contactRepository
									.findByAccountContactslaExpirationDateLessThan(request.getValue());

						} else if (request.getValue().equalsIgnoreCase("today")) {
							Date slaExpirationDate = new Date();
							accountDetails = contactRepository
									.findByAccountContactslaExpirationDateLessThan(request.getValue());
						} else if (request.getValue().equalsIgnoreCase("tomorrow")) {
							Calendar cal = Calendar.getInstance();
							cal.add(Calendar.DATE, 1);
							Date slaExpirationDate = cal.getTime();
							accountDetails = contactRepository
									.findByAccountContactslaExpirationDateLessThan(request.getValue());
						} else {
							Date slaExpirationDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getValue());
							accountDetails = contactRepository
									.findByAccountContactslaExpirationDateLessThan(request.getValue());
						}

					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return null;
				}

				else if (Operator.EQUALS.toString().equalsIgnoreCase(request.getOperator())) {

					accountDetails = contactRepository.findByAccountContactslaExpirationDate(request.getValue());

					try {
						if (request.getValue().equalsIgnoreCase("yesterday")) {
							Calendar cal = Calendar.getInstance();
							cal.add(Calendar.DATE, -1);
							Date slaExpirationDate = cal.getTime();
							accountDetails = contactRepository
									.findByAccountContactslaExpirationDate(request.getValue());

						} else if (request.getValue().equalsIgnoreCase("today")) {
							Date slaExpirationDate = new Date();
							accountDetails = contactRepository
									.findByAccountContactslaExpirationDate(request.getValue());
						} else if (request.getValue().equalsIgnoreCase("tomorrow")) {
							Calendar cal = Calendar.getInstance();
							cal.add(Calendar.DATE, 1);
							Date slaExpirationDate = cal.getTime();
							accountDetails = contactRepository
									.findByAccountContactslaExpirationDate(request.getValue());
						} else {
							Date slaExpirationDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getValue());
							accountDetails = contactRepository
									.findByAccountContactslaExpirationDate(request.getValue());
						}

					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return null;
				}

				else if (Operator.NOT_EQUALS_TO.toString().equalsIgnoreCase(request.getOperator())) {

					accountDetails = contactRepository.findByAccountContactslaExpirationDateNot(request.getValue());

					try {
						if (request.getValue().equalsIgnoreCase("yesterday")) {
							Calendar cal = Calendar.getInstance();
							cal.add(Calendar.DATE, -1);
							Date slaExpirationDate = cal.getTime();
							accountDetails = contactRepository
									.findByAccountContactslaExpirationDateNot(request.getValue());

						} else if (request.getValue().equalsIgnoreCase("today")) {
							Date slaExpirationDate = new Date();
							accountDetails = contactRepository
									.findByAccountContactslaExpirationDateNot(request.getValue());
						} else if (request.getValue().equalsIgnoreCase("tomorrow")) {
							Calendar cal = Calendar.getInstance();
							cal.add(Calendar.DATE, 1);
							Date slaExpirationDate = cal.getTime();
							accountDetails = contactRepository
									.findByAccountContactslaExpirationDateNot(request.getValue());
						} else {
							Date slaExpirationDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getValue());
							accountDetails = contactRepository
									.findByAccountContactslaExpirationDateNot(request.getValue());
						}

					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return null;
				} else if (Operator.GREATER_OR_EQUAL.toString().equalsIgnoreCase(request.getOperator())) {

					accountDetails = contactRepository
							.findByAccountContactslaExpirationDateGreaterThanEqual(request.getValue());

					try {
						if (request.getValue().equalsIgnoreCase("yesterday")) {
							Calendar cal = Calendar.getInstance();
							cal.add(Calendar.DATE, -1);
							Date slaExpirationDate = cal.getTime();
							accountDetails = contactRepository
									.findByAccountContactslaExpirationDateGreaterThanEqual(request.getValue());

						} else if (request.getValue().equalsIgnoreCase("today")) {
							Date slaExpirationDate = new Date();
							accountDetails = contactRepository
									.findByAccountContactslaExpirationDateGreaterThanEqual(request.getValue());
						} else if (request.getValue().equalsIgnoreCase("tomorrow")) {
							Calendar cal = Calendar.getInstance();
							cal.add(Calendar.DATE, 1);
							Date slaExpirationDate = cal.getTime();
							accountDetails = contactRepository
									.findByAccountContactslaExpirationDateGreaterThanEqual(request.getValue());
						} else {
							Date slaExpirationDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getValue());
							accountDetails = contactRepository
									.findByAccountContactslaExpirationDateGreaterThanEqual(request.getValue());
						}

					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return null;
				}

				else if (Operator.LESS_OR_EQUAL.toString().equalsIgnoreCase(request.getOperator())) {

					accountDetails = contactRepository
							.findByAccountContactslaExpirationDateLessThanEqual(request.getValue());

					try {
						if (request.getValue().equalsIgnoreCase("yesterday")) {
							Calendar cal = Calendar.getInstance();
							cal.add(Calendar.DATE, -1);
							Date slaExpirationDate = cal.getTime();
							accountDetails = contactRepository
									.findByAccountContactslaExpirationDateLessThanEqual(request.getValue());

						} else if (request.getValue().equalsIgnoreCase("today")) {
							Date slaExpirationDate = new Date();
							accountDetails = contactRepository
									.findByAccountContactslaExpirationDateLessThanEqual(request.getValue());
						} else if (request.getValue().equalsIgnoreCase("tomorrow")) {
							Calendar cal = Calendar.getInstance();
							cal.add(Calendar.DATE, 1);
							Date slaExpirationDate = cal.getTime();
							accountDetails = contactRepository
									.findByAccountContactslaExpirationDateLessThanEqual(request.getValue());
						} else {
							Date slaExpirationDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getValue());
							accountDetails = contactRepository
									.findByAccountContactslaExpirationDateLessThanEqual(request.getValue());
						}

					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return null;
				}
			}

			// In contact field
			// asstPhone
			if (ContactField.ASST_PHONE.toString().equalsIgnoreCase(request.getOperator())) {

				if (Operator.EQUALS.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository.findByAsstPhone(Long.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				} else if (Operator.NOT_EQUALS_TO.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findByAsstPhoneNot(Long.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				}

			}
			// homePhone //long
			if (ContactField.ASST_PHONE.toString().equalsIgnoreCase(request.getOperator())) {

				if (Operator.EQUALS.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository.findByHomePhone(Long.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				} else if (Operator.NOT_EQUALS_TO.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findByHomePhoneNot(Long.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				}

			}
			// mailingZipCode //long
			if (ContactField.ASST_PHONE.toString().equalsIgnoreCase(request.getOperator())) {

				if (Operator.EQUALS.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findByMailingZipCode(Long.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				} else if (Operator.NOT_EQUALS_TO.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findByMailingZipCodeNot(Long.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				}

			}
			// mobile //long
			if (ContactField.ASST_PHONE.toString().equalsIgnoreCase(request.getOperator())) {

				if (Operator.EQUALS.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository.findByMobile(Long.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				} else if (Operator.NOT_EQUALS_TO.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository.findByMobileNot(Long.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				}

			}
			// otherPhone //long
			if (ContactField.ASST_PHONE.toString().equalsIgnoreCase(request.getOperator())) {

				if (Operator.EQUALS.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository.findByOtherPhone(Long.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				} else if (Operator.NOT_EQUALS_TO.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findByOtherPhoneNot(Long.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				}

			}
			// otherZipCode //long
			if (ContactField.ASST_PHONE.toString().equalsIgnoreCase(request.getOperator())) {

				if (Operator.EQUALS.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findByOtherZipCode(Long.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				} else if (Operator.NOT_EQUALS_TO.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findByOtherZipCodeNot(Long.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				}

			}
			// phone //long
			if (ContactField.ASST_PHONE.toString().equalsIgnoreCase(request.getOperator())) {

				if (Operator.EQUALS.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository.findByPhone(Long.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				} else if (Operator.NOT_EQUALS_TO.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository.findByPhoneNot(Long.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				}

			}

			// assistant
			if (ContactField.ASSISTANT.toString().equalsIgnoreCase(request.getOperator())) {

				if (Operator.CONTAINS.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findByAssistantContainingIgnoreCase(String.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				} else if (Operator.DOES_NOT_CONTAIN.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findByAssistantNotContainingIgnoreCase(String.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				} else if (Operator.STARTS_WITH.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findByAssistantStartsWithIgnoreCase(String.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				}

			}
			// department
			if (ContactField.DEPARTMENT.toString().equalsIgnoreCase(request.getOperator())) {

				if (Operator.CONTAINS.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findByDepartmentContainingIgnoreCase(String.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				} else if (Operator.DOES_NOT_CONTAIN.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findByDepartmentNotContainingIgnoreCase(String.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				} else if (Operator.STARTS_WITH.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findByDepartmentStartsWithIgnoreCase(String.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				}

			}

			// firstName //string
			if (ContactField.FIRST_NAME.toString().equalsIgnoreCase(request.getOperator())) {

				if (Operator.CONTAINS.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findByFirstNameContainingIgnoreCase(String.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				} else if (Operator.DOES_NOT_CONTAIN.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findByFirstNameNotContainingIgnoreCase(String.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				} else if (Operator.STARTS_WITH.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findByFirstNameStartsWithIgnoreCase(String.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				}
			}
			// lastName //string
			if (ContactField.LAST_NAME.toString().equalsIgnoreCase(request.getOperator())) {

				if (Operator.CONTAINS.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findByLastNameContainingIgnoreCase(String.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				} else if (Operator.DOES_NOT_CONTAIN.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findByLastNameNotContainingIgnoreCase(String.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				} else if (Operator.STARTS_WITH.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findByLastNameStartsWithIgnoreCase(String.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				}
			}
			// languages //string
			if (ContactField.LANGUAGES.toString().equalsIgnoreCase(request.getOperator())) {

				if (Operator.CONTAINS.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findByLanguagesContainingIgnoreCase(String.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				} else if (Operator.DOES_NOT_CONTAIN.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findByLanguagesNotContainingIgnoreCase(String.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				} else if (Operator.STARTS_WITH.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findByLanguagesStartsWithIgnoreCase(String.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				}
			}

			// fax //string
			if (ContactField.FAX.toString().equalsIgnoreCase(request.getOperator())) {

				if (Operator.CONTAINS.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findByFaxContainingIgnoreCase(String.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				} else if (Operator.DOES_NOT_CONTAIN.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findByFaxNotContainingIgnoreCase(String.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				} else if (Operator.STARTS_WITH.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findByFaxStartsWithIgnoreCase(String.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				}
			}
			// email //string
			if (ContactField.EMAIL.toString().equalsIgnoreCase(request.getOperator())) {

				if (Operator.CONTAINS.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findByEmailContainingIgnoreCase(String.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				} else if (Operator.DOES_NOT_CONTAIN.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findByEmailNotContainingIgnoreCase(String.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				} else if (Operator.STARTS_WITH.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findByEmailStartsWithIgnoreCase(String.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				}
			}
			// leadSource //string
			if (ContactField.LEAD_SOURCE.toString().equalsIgnoreCase(request.getOperator())) {

				if (Operator.CONTAINS.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findByLeadSourceContainingIgnoreCase(String.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				} else if (Operator.DOES_NOT_CONTAIN.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findByLeadSourceNotContainingIgnoreCase(String.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				} else if (Operator.STARTS_WITH.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findByLeadSourceStartsWithIgnoreCase(String.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				}
			}

			// level //string
			if (ContactField.LEVEL.toString().equalsIgnoreCase(request.getOperator())) {

				if (Operator.CONTAINS.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findByLevelContainingIgnoreCase(String.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				} else if (Operator.DOES_NOT_CONTAIN.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findByLevelNotContainingIgnoreCase(String.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				} else if (Operator.STARTS_WITH.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findByLevelStartsWithIgnoreCase(String.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				}
			}
			// mailingCity //string
			if (ContactField.MAILING_CITY.toString().equalsIgnoreCase(request.getOperator())) {

				if (Operator.CONTAINS.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findByMailingCityContainingIgnoreCase(String.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				} else if (Operator.DOES_NOT_CONTAIN.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findByMailingCityNotContainingIgnoreCase(String.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				} else if (Operator.STARTS_WITH.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findByMailingCityStartsWithIgnoreCase(String.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				}
			}
			// mailingCountry //string
			if (ContactField.MAILING_COUNTRY.toString().equalsIgnoreCase(request.getOperator())) {

				if (Operator.CONTAINS.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findByMailingCountryContainingIgnoreCase(String.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				} else if (Operator.DOES_NOT_CONTAIN.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findByMailingCountryNotContainingIgnoreCase(String.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				} else if (Operator.STARTS_WITH.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findByMailingCountryStartsWithIgnoreCase(String.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				}
			}
			// mailingState //string
			if (ContactField.MAILING_STATE_PROVINCE.toString().equalsIgnoreCase(request.getOperator())) {

				if (Operator.CONTAINS.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findByMailingStateContainingIgnoreCase(String.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				} else if (Operator.DOES_NOT_CONTAIN.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findByMailingStateNotContainingIgnoreCase(String.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				} else if (Operator.STARTS_WITH.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findByMailingStateStartsWithIgnoreCase(String.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				}
			}
			// mailingStreet //string
			if (ContactField.MAILING_STREET.toString().equalsIgnoreCase(request.getOperator())) {

				if (Operator.CONTAINS.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findByMailingStreetContainingIgnoreCase(String.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				} else if (Operator.DOES_NOT_CONTAIN.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findByMailingStreetNotContainingIgnoreCase(String.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				} else if (Operator.STARTS_WITH.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findByMailingStreetStartsWithIgnoreCase(String.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				}
			}

			// otherCity //string
			if (ContactField.OTHER_CITY.toString().equalsIgnoreCase(request.getOperator())) {

				if (Operator.CONTAINS.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findByOtherCityContainingIgnoreCase(String.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				} else if (Operator.DOES_NOT_CONTAIN.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findByOtherCityNotContainingIgnoreCase(String.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				} else if (Operator.STARTS_WITH.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findByOtherCityStartsWithIgnoreCase(String.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				}
			}
			// otherCountry //string
			if (ContactField.OTHER_COUNTRY.toString().equalsIgnoreCase(request.getOperator())) {

				if (Operator.CONTAINS.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findByOtherCountryContainingIgnoreCase(String.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				} else if (Operator.DOES_NOT_CONTAIN.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findByOtherCountryNotContainingIgnoreCase(String.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				} else if (Operator.STARTS_WITH.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findByOtherCountryStartsWithIgnoreCase(String.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				}
			}

			// otherState //string
			if (ContactField.OTHER_STATE_PROVINCE.toString().equalsIgnoreCase(request.getOperator())) {

				if (Operator.CONTAINS.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findByOtherStateContainingIgnoreCase(String.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				} else if (Operator.DOES_NOT_CONTAIN.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findByOtherStateNotContainingIgnoreCase(String.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				} else if (Operator.STARTS_WITH.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findByOtherStateStartsWithIgnoreCase(String.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				}
			}

			// otherStreet //string
			if (ContactField.OTHER_STREET.toString().equalsIgnoreCase(request.getOperator())) {

				if (Operator.CONTAINS.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findByOtherStreetContainingIgnoreCase(String.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				} else if (Operator.DOES_NOT_CONTAIN.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findByOtherStreetNotContainingIgnoreCase(String.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				} else if (Operator.STARTS_WITH.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findByOtherStreetStartsWithIgnoreCase(String.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				}
			}
			// reportsTo //string
			if (ContactField.REPORTS_TO.toString().equalsIgnoreCase(request.getOperator())) {

				if (Operator.CONTAINS.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findByReportsToContainingIgnoreCase(String.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				} else if (Operator.DOES_NOT_CONTAIN.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findByReportsToNotContainingIgnoreCase(String.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				} else if (Operator.STARTS_WITH.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findByReportsToStartsWithIgnoreCase(String.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				}
			}
			// salutation //string
			if (ContactField.SALUTATION.toString().equalsIgnoreCase(request.getOperator())) {

				if (Operator.CONTAINS.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findBySalutationContainingIgnoreCase(String.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				} else if (Operator.DOES_NOT_CONTAIN.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findBySalutationNotContainingIgnoreCase(String.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				} else if (Operator.STARTS_WITH.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findBySalutationStartsWithIgnoreCase(String.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				}
			}
			// title //string
			if (ContactField.TITLE.toString().equalsIgnoreCase(request.getOperator())) {

				if (Operator.CONTAINS.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findByTitleContainingIgnoreCase(String.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				} else if (Operator.DOES_NOT_CONTAIN.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findByTitleNotContainingIgnoreCase(String.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				} else if (Operator.STARTS_WITH.toString().equalsIgnoreCase(request.getOperator())) {

					List<Contact> listContact = contactRepository
							.findByTitleStartsWithIgnoreCase(String.valueOf(request.getOperator()));

					contactMap.put("contactData", listContact);
					accountDetails.add(contactMap);

				}
			}

		}

		// Pagination
		Map<String, Object> map = new LinkedHashMap<>();
		Integer totalRecords = accountDetails.size();
		try {
			if (request.getPageNo() != null && request.getPageNo() > 0 && request.getPageSize() != null
					&& request.getPageSize() > 0) {
				Integer startIndex = (request.getPageNo() - 1) * request.getPageSize();
				Integer endIndex = Math.min(startIndex + request.getPageSize(), totalRecords);
				accountDetails = accountDetails.subList(startIndex, endIndex);
			}
		} catch (Exception e) {
			accountDetails = new LinkedList<>();
		}

		map.put("totalRecords", totalRecords);
		map.put("recordsPresent", accountDetails.size());
		if (request.getPageSize() != null && request.getPageSize() != 0) {
			map.put("totalPages", (int) Math.ceil((double) totalRecords / request.getPageSize()));
			map.put("currentPage", request.getPageNo());
		} else {
			map.put("totalPages", 1);
			map.put("currentPage", 1);
		}
		map.put("records", accountDetails);
		return map;

		// return contactCustomRepository.findAllAccountContact(request);
	}

	public List<Contact> findByLeadSource(String leadSource) {
		return contactRepository.findByLeadSourceIgnoreCase(leadSource);
	}

	public List<Contact> findByLevel(String level) {
		return contactRepository.findByLevelIgnoreCase(level);
	}

	public void deleteContact(Integer id) {
		contactRepository.deleteById(id);
	}

	@Transactional
	public void deleteContactByIds(List<Integer> contactid) {
		contactRepository.deleteAllByIdInBatch(contactid);
	}

	public Contact uploadContactImage(MultipartFile image, Integer contactid) {

		Contact imageContact = null;
		Optional<Contact> contact = contactRepository.findByContactid(contactid);

		try {

			if (Objects.nonNull(image)) {
				File file = null;

				String mimeType = image.getContentType();

				if ((mimeType.equalsIgnoreCase("image/jpeg") || mimeType.equalsIgnoreCase("image/png"))) {

					file = convertMultiPartFileToFile(image);
					String uniqueName = uploadFileToS3Bucket("xabitbucket", file, "/image");
					contact.get().setImageId(uniqueName);
					contact.get().setImagePath(
							"https://xabitbucket.s3.ap-south-1.amazonaws.com" + "/image" + "/" + uniqueName);
					file.delete();

				} else {
					throw new ImageException("Image Format Not Supported");
				}

				imageContact = contactRepository.save(contact.get());

			}
		} catch (Exception e) {
		}

		return imageContact;
	}

	private File convertMultiPartFileToFile(MultipartFile multipartFile) throws java.io.IOException {
		final File file = new File(multipartFile.getOriginalFilename());
		try (final FileOutputStream outputStream = new FileOutputStream(file)) {
			outputStream.write(multipartFile.getBytes());
		} catch (final IOException ex) {
			throw ex;
		}
		return file;
	}

	private String uploadFileToS3Bucket(final String bucketName, final File file, String bucketPath) {
		final String uniqueFileName = new Date().getTime() + "-" + file.getName();
		final PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName + bucketPath, uniqueFileName, file);
		amazonS3.putObject(putObjectRequest);
		return uniqueFileName;
	}

}
