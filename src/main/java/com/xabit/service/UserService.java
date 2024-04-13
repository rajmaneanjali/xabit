package com.xabit.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xabit.model.Lead;
import com.xabit.model.MasterEntity;
import com.xabit.model.MasterValueEntity;
import com.xabit.model.User;
import com.xabit.repository.AccountRepository;
import com.xabit.repository.CampaignRepository;
import com.xabit.repository.ContactRepository;
import com.xabit.repository.LeadRepository;
import com.xabit.repository.MasterRepository;
import com.xabit.repository.MasterValueRepository;
import com.xabit.repository.OpportunityRepository;
import com.xabit.repository.TaskRepository;
import com.xabit.repository.UserRepository;
import com.xabit.utility.ClassName;
import com.xabit.utility.Leadsource;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MasterRepository masterRepository;

	@Autowired
	private MasterValueRepository masterValueRepository;

	@Autowired
	private ContactRepository contactRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private CampaignRepository campaignRepository;

	@Autowired
	private LeadRepository leadRepository;

	@Autowired
	private OpportunityRepository opportunityRepository;

	@Autowired
	private TaskRepository taskRepository;

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	// get all list
	public List<Map<String, Object>> getAllUser() {
		List<Map<String, Object>> lstMap = new ArrayList<>();
		List<Map<String, Object>> responseList = new ArrayList<>();

		lstMap = userRepository.findAllUsers();

		/*
		 * for (Map<String, Object> map : lstMap) {
		 * 
		 * Map<String, Object> newMap = new HashMap<>(); List<MasterValueEntity>
		 * lstMasterValueEntity = masterValueRepository .findByRelationId((Integer)
		 * map.get("userid"));
		 * 
		 * ObjectMapper mapper = new ObjectMapper();
		 * 
		 * newMap = mapper.convertValue(map, Map.class);
		 * 
		 * if (!lstMasterValueEntity.isEmpty()) { for (MasterValueEntity
		 * masterValueEntity : lstMasterValueEntity) {
		 * 
		 * Optional<MasterEntity> masterEntity =
		 * masterRepository.findById(masterValueEntity.getMasterId());
		 * 
		 * if (masterEntity.isPresent()) { newMap.put(masterEntity.get().getFieldName(),
		 * masterValueEntity.getValue());
		 * 
		 * }
		 * 
		 * } } List<MasterEntity> listMasterEntity =
		 * masterRepository.findByClassNameIgnoreCase("USER");
		 * 
		 * if (!listMasterEntity.isEmpty()) {
		 * 
		 * for (MasterEntity masterEntity : listMasterEntity) {
		 * 
		 * List<MasterValueEntity> listMasterValueEntity = masterValueRepository
		 * .findByMasterId(masterEntity.getId());
		 * 
		 * if (listMasterValueEntity.isEmpty()) {
		 * newMap.put(masterEntity.getFieldName(), null); } else { for
		 * (MasterValueEntity masterValueEntity : listMasterValueEntity) { if
		 * (!newMap.get("userid").toString()
		 * .equalsIgnoreCase(masterValueEntity.getRelationId().toString())) {
		 * newMap.put(masterEntity.getFieldName(), null); break; }
		 * 
		 * } }
		 * 
		 * }
		 * 
		 * } responseList.add(newMap); }
		 */

		List<Map<String, Object>> deduped = lstMap.stream().distinct().collect(Collectors.toList());

		return deduped;

	}

	public List<User> findByLanguage(String language) {
		List<User> listUser = userRepository.findByLanguageIgnoreCase(language);
		return listUser; // Return the combined list
	}

	// get by id
	public User getUserByUserid(Integer id) {
		// return userRepository.findById(id).orElse(null);
		return userRepository.findById(id).orElse(null);
	}

	// insert the data and string id logic
	public User createUser(User user) {
//		user.setUserid(generateID());
		user.setAlias(manipulateUserNames(user));
		user.setCreatedDate(new Date());
		user.setLastModifiedDate(new Date());
		return userRepository.save(user);
	}

	public String manipulateUserNames(User user) {
		String lastName = user.getLastName();

		if (lastName.length() > 6) {
			lastName = lastName.substring(0, 6);
		}

		String firstName = user.getFirstName().substring(0, 1);

		String modifiedName = lastName + firstName;

		// Check if the alias already exists in the database
		int suffix = 0;
		String aliasToCheck = modifiedName;

		while (isAliasExists(aliasToCheck)) {
			suffix++;
			aliasToCheck = modifiedName + String.format("%02d", suffix);
		}

		user.setAlias(aliasToCheck);

		return aliasToCheck;
	}

	private boolean isAliasExists(String alias) {
		Optional<User> existingUser = userRepository.findByAlias(alias);
		return existingUser.isPresent();
	}

	public User updateUser(User user) {

		User user1 = userRepository.findById(user.getUserid()).orElse(null);
		user1.setUserid(user.getUserid());
		user1.setAlias(user.getAlias());
		user1.setFirstName(user.getFirstName());
		user1.setLastName(user.getLastName());
		user1.setCreatedBy(user.getCreatedBy());
//		user1.setCreatedDate(user.getCreatedDate());
		user1.setLastModifiedBy(user.getLastModifiedBy());
		user1.setLastModifiedDate(user.getLastModifiedDate());
		user1.setCompanyName(user.getCompanyName());
		user1.setEmail(user.getEmail());
		user1.setUserName(user.getUserName());
		user1.setLanguage(user.getLanguage());
		user1.setLocale(user.getLocale());
		user1.setMobile(user.getMobile());
		user1.setTitle(user.getTitle());
		return userRepository.save(user1);
	}

	public void deleteUser(Integer id) {
		userRepository.deleteById(id);
	}

	public List<User> getMasterData() {
		return userRepository.findAll();
	}

	public List<Map<String, Object>> getByDynamicUser(ClassName className) {

		List<Map<String, Object>> lstMap = new ArrayList<>();

		List<Map<String, Object>> responseList = new ArrayList<>();

		// lstMap = userRepository.findByDynamicUser();

		if (className.toString().equalsIgnoreCase("USER")) {
			lstMap = userRepository.findByDynamicAllUser();
		} else if (className.toString().equalsIgnoreCase("CONTACT")) {

			lstMap = contactRepository.findByDynamicAllContact();
		} else if (className.toString().equalsIgnoreCase("ACCOUNT")) {

			lstMap = accountRepository.findByDynamicAllAccount();
		} else if (className.toString().equalsIgnoreCase("CAMPAIGN")) {

			lstMap = campaignRepository.findByDynamicAllCampaign();
		} else if (className.toString().equalsIgnoreCase("LEAD")) {

			lstMap = leadRepository.findByDynamicAllLead();
		} else if (className.toString().equalsIgnoreCase("TASK")) {

			lstMap = taskRepository.findByDynamicAllTask();
		} else if (className.toString().equalsIgnoreCase("OPPORTUNITY")) {

			lstMap = opportunityRepository.findByDynamicAllOpportunity();
		}

		for (Map<String, Object> map : lstMap) {

			Map<String, Object> newMap = new HashMap<>();
			Integer id = 0;

			if (className.toString().equalsIgnoreCase("USER")) {
				id = (Integer) map.get("userid");
			} else if (className.toString().equalsIgnoreCase("CONTACT")) {
				id = (Integer) map.get("contactid");
			} else if (className.toString().equalsIgnoreCase("ACCOUNT")) {
				id = (Integer) map.get("accountid");
			} else if (className.toString().equalsIgnoreCase("CAMPAIGN")) {
				id = (Integer) map.get("campaignid");
			} else if (className.toString().equalsIgnoreCase("LEAD")) {
				id = (Integer) map.get("leadid");
			} else if (className.toString().equalsIgnoreCase("TASK")) {
				id = (Integer) map.get("taskid");
			} else if (className.toString().equalsIgnoreCase("OPPORTUNITY")) {
				id = (Integer) map.get("opportunityid");
			}

			List<MasterValueEntity> lstMasterValueEntity = masterValueRepository.findByRelationId(id);

			ObjectMapper mapper = new ObjectMapper();

			newMap = mapper.convertValue(map, Map.class);

			if (!lstMasterValueEntity.isEmpty()) {
				for (MasterValueEntity masterValueEntity : lstMasterValueEntity) {

					Optional<MasterEntity> masterEntity = masterRepository.findById(masterValueEntity.getMasterId());

					if (masterEntity.isPresent()) {
						newMap.put(masterEntity.get().getFieldName(), masterValueEntity.getValue());

					}

				}
			}
			responseList.add(newMap);

		}

		List<Map<String, Object>> deduped = responseList.stream().distinct().collect(Collectors.toList());

		return deduped;
	}

	public List<Map<String, Object>> getByDynamicUserById(ClassName className, Integer id2) {

		List<Map<String, Object>> lstMap = new ArrayList<>();

		List<Map<String, Object>> responseList = new ArrayList<>();

		// lstMap = userRepository.findByDynamicUser();

		if (className.toString().equalsIgnoreCase("USER")) {
			lstMap = userRepository.findByDynamicUserById(id2);
		} else if (className.toString().equalsIgnoreCase("CONTACT")) {

			lstMap = contactRepository.findByDynamicContactById(id2);

		} else if (className.toString().equalsIgnoreCase("ACCOUNT")) {

			lstMap = accountRepository.findByDynamicAccountById(id2);
		} else if (className.toString().equalsIgnoreCase("CAMPAIGN")) {

			lstMap = campaignRepository.findByDynamicCampaignById(id2);
		} else if (className.toString().equalsIgnoreCase("LEAD")) {

			lstMap = leadRepository.findByDynamicLeadById(id2);
		} else if (className.toString().equalsIgnoreCase("TASK")) {

			lstMap = taskRepository.findByDynamicTaskById(id2);
		} else if (className.toString().equalsIgnoreCase("OPPORTUNITY")) {

			lstMap = opportunityRepository.findByDynamicOpportunityById(id2);
		}

		for (Map<String, Object> map : lstMap) {

			Map<String, Object> newMap = new HashMap<>();
			Integer id = 0;

			if (className.toString().equalsIgnoreCase("USER")) {
				id = (Integer) map.get("userid");
			} else if (className.toString().equalsIgnoreCase("CONTACT")) {
				id = (Integer) map.get("contactid");
			} else if (className.toString().equalsIgnoreCase("ACCOUNT")) {
				id = (Integer) map.get("accountid");
			} else if (className.toString().equalsIgnoreCase("CAMPAIGN")) {
				id = (Integer) map.get("campaignid");
			} else if (className.toString().equalsIgnoreCase("LEAD")) {
				id = (Integer) map.get("leadid");
			} else if (className.toString().equalsIgnoreCase("TASK")) {
				id = (Integer) map.get("taskid");
			} else if (className.toString().equalsIgnoreCase("OPPORTUNITY")) {
				id = (Integer) map.get("opportunityid");
			}

			List<MasterValueEntity> lstMasterValueEntity = masterValueRepository.findByRelationId(id);

			ObjectMapper mapper = new ObjectMapper();

			newMap = mapper.convertValue(map, Map.class);

			if (!lstMasterValueEntity.isEmpty()) {
				for (MasterValueEntity masterValueEntity : lstMasterValueEntity) {

					Optional<MasterEntity> masterEntity = masterRepository.findById(masterValueEntity.getMasterId());

					if (masterEntity.isPresent()) {
						newMap.put(masterEntity.get().getFieldName(), masterValueEntity.getValue());

					}

				}
			}
			responseList.add(newMap);

		}

		List<Map<String, Object>> deduped = responseList.stream().distinct().collect(Collectors.toList());

		return deduped;
	}

	@Transactional
	public void deleteUserByIds(List<Integer> userid) {
		userRepository.deleteAllByIdInBatch(userid);
	}

	public List<String> getColumnDataTypes() {
        return userRepository.getColumnDataTypes();
    }
	
	public Map<String, Object> getAllEnum() {

		Map<String, Object> map = new HashMap<>();

		map.put("Lead", Leadsource.values());

		return map;
	}

}