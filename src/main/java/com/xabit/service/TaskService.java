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
public class TaskService {
	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private MasterValueRepository masterValueRepository;

	@Autowired
	private MasterRepository masterRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MailUtil mailUtil;

	@Autowired
	private LookupRepository lookupRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private OpportunityRepository opportunityRepository;

	@Autowired
	private LeadRepository leadRepository;

	@Autowired
	private ContactRepository contactRepository;

	@Autowired
	private CampaignRepository campaignRepository;

	@Autowired
	private LookupRelationRepository lookupRelationRepository;

	@Autowired
	private EmailService emailservice;

	@Autowired
	private EmailActivityRepository emailActivityRepository;

	@Autowired
	private EmailRepository emailRepository;

	public List<Task> getAllTasks() {
		return taskRepository.findAll();
	}

	// get all list
	public List<Map<String, Object>> getAllTask() {
		List<Map<String, Object>> lstMap = new ArrayList<>();
		List<Map<String, Object>> responseMap = new ArrayList<>();
		lstMap = taskRepository.findAllTasks();

		List<Lookup> lstLookup = lookupRepository.findByRelatedTo("TASK");

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

		Map<String, Object> leadMap = new HashMap<>();
		Map<String, Object> taskMap = new HashMap<>();

		List<Lead> leadDataList = leadRepository.findAll();

		taskMap.put("TaskData", lstMap);
		leadMap.put("LeadData", leadDataList);

		responseMap.add(taskMap);
		responseMap.add(leadMap);

		return responseMap;

	}

	private List<Map<String, Object>> checkLookupRelations1(List<Map<String, Object>> responseMap,
			List<Map<String, Object>> lstMap) {

		Map<String, Object> contactMap = new HashMap<>();
		Map<String, Object> taskMap = new HashMap<>();

		List<Contact> contactDataList = contactRepository.findAll();

		taskMap.put("TaskData", lstMap);
		contactMap.put("ContactData", contactDataList);

		responseMap.add(taskMap);
		responseMap.add(contactMap);

		return responseMap;

	}

	private List<Map<String, Object>> checkLookupRelations2(List<Map<String, Object>> responseMap,
			List<Map<String, Object>> lstMap) {

		Map<String, Object> opportunityMap = new HashMap<>();
		Map<String, Object> taskMap = new HashMap<>();

		List<Opportunity> opportunityDataList = opportunityRepository.findAll();

		taskMap.put("TaskData", lstMap);
		opportunityMap.put("OpportunityData", opportunityDataList);

		responseMap.add(taskMap);
		responseMap.add(opportunityMap);

		return responseMap;

	}

	private List<Map<String, Object>> checkLookupRelations3(List<Map<String, Object>> responseMap,
			List<Map<String, Object>> lstMap) {

		Map<String, Object> accountMap = new HashMap<>();
		Map<String, Object> taskMap = new HashMap<>();

		List<Account> accountDataList = accountRepository.findAll();

		taskMap.put("TaskData", lstMap);
		accountMap.put("AccountData", accountDataList);

		responseMap.add(taskMap);
		responseMap.add(accountMap);

		return responseMap;

	}

	private List<Map<String, Object>> checkLookupRelations4(List<Map<String, Object>> responseMap,
			List<Map<String, Object>> lstMap) {

		Map<String, Object> campaignMap = new HashMap<>();
		Map<String, Object> taskMap = new HashMap<>();

		List<Campaign> campaignDataList = campaignRepository.findAll();

		taskMap.put("TaskData", lstMap);
		campaignMap.put("CampaignData", campaignDataList);

		responseMap.add(taskMap);
		responseMap.add(campaignMap);

		return responseMap;

	}

	public Task getTaskById(Integer id) {
		return taskRepository.findById(id).orElse(null);
	}

	public Task createTask(Task task) {
		return taskRepository.save(task);
	}

	// dropdwon list
	public List<Task> findByStatus(String status) {
		List<Task> listTask = taskRepository.findByStatusIgnoreCase(status);
		return listTask;
	}

	public List<Task> findByPriority(String priority) {
		List<Task> listTask = taskRepository.findByPriorityIgnoreCase(priority);
		return listTask;
	}

	public Task updateTask(Task task) {
		Task task1 = taskRepository.findById(task.getTaskid()).orElse(null);
		task1.setAssignedTo(task.getAssignedTo());
		task1.setStatus(task.getStatus());
		task1.setSubject(task.getSubject());
		task1.setDueDate(task.getDueDate());
		task1.setPriority(task.getPriority());
		task1.setName(task.getName());
		task1.setRelatedTo(task.getRelatedTo());
		task1.setComments(task.getComments());
		task1.setReminder(task.getReminder());
		task1.setTaskType(task.getTaskType());
		task1.setIncomingcallCount(task.getIncomingcallCount());
		task1.setOutgoingcallCount(task.getOutgoingcallCount());
		task1.setIncomingEmailCount(task.getIncomingEmailCount());
		task1.setOutgoingEmailCount(task.getOutgoingEmailCount());
		task1.setCampaignid(task.getCampaignid());
		task1.setAccountid(task.getAccountid());
		task1.setOpportunityid(task.getOpportunityid());
		task1.setLeadid(task.getLeadid());
		task1.setContactid(task.getContactid());

		return taskRepository.save(task1);
	}

	public void deleteTask(Integer id) {
		taskRepository.deleteById(id);
	}

	@Transactional
	public void deleteTaskByIds(List<Integer> taskid) {
		taskRepository.deleteAllByIdInBatch(taskid);
	}

	public void taskSchedulerImpl() {
		List<Task> tasklist = taskRepository.findAll();

		for (Task task : tasklist) {
			String taskDate = mailUtil.convertDateToString(task.getCreatedDate());

			String todaysDate = mailUtil.convertDateToString(new Date());

			// check if already exist.
			Optional<EmailActivity> emailActivityRecord = emailActivityRepository
					.findByTableIdAndTableNameAndStage(task.getTaskid(), "TASK", "CREATE");

			// if already not exist then send an email
			if (!emailActivityRecord.isPresent()) {

				// Long days = mailUtil.calculateDiffBetweenTwoDays(todaysDate, leadDate);

				Integer hours = mailUtil.calculateDiffBetweenTwoHours(new Date(), task.getCreatedDate());
				if (hours >= 2) {
					emailservice.sendEmail(task.getEmail(), "SchedularTest", "Schedular Body Test");

					// save email Records
					Email email = new Email(0, task.getEmail(), "SchedularTest", "Schedular Body Test", new Date(),
							new Date(), task.getUserid());
					emailRepository.save(email);
					// save Email Activity
					EmailActivity emailActivity = new EmailActivity(0, task.getLeadid(), "TASK", "CREATE", new Date(),
							new Date());
					emailActivityRepository.save(emailActivity);
				}
			}

			User user = userRepository.findByUserid(task.getUserid());

			if (Objects.nonNull(user)) {
				// check if already exist.
				Optional<EmailActivity> emailActivityRecord1 = emailActivityRepository
						.findByTableIdAndTableNameAndStage(task.getTaskid(), "TASK", "BIRTHDATE");
				if (!emailActivityRecord1.isPresent()) {

					String birthDate = mailUtil.convertDateToString(user.getBirthDate());

					Long birthDays = mailUtil.calculateDiffBetweenTwoDays(todaysDate, birthDate);

					String daysAndMonthToday = mailUtil.getDaysAndMonth(new Date());

					String daysAndMonthBirtDate = mailUtil.getDaysAndMonth(user.getBirthDate());
					if (birthDays >= 365 && daysAndMonthToday.equalsIgnoreCase(daysAndMonthBirtDate)) {
						emailservice.sendEmail(task.getEmail(), "BirthDay Greetings", "Happy Birthday");

						// save email Records
						Email email = new Email(0, task.getEmail(), "BirthDay Greetings", "Happy Birthday", new Date(),
								new Date(), task.getUserid());
						emailRepository.save(email);

						// save Email Activity
						EmailActivity emailActivity = new EmailActivity(0, task.getTaskid(), "TASK", "BIRTHDATE",
								new Date(), new Date());
						emailActivityRepository.save(emailActivity);
					}
				}

			}

			String lastModifiedDate = mailUtil.convertDateToString(task.getLastModifiedDate());
			Long days = mailUtil.calculateDiffBetweenTwoDays(todaysDate, lastModifiedDate);

			Optional<EmailActivity> updateEmailActivity = emailActivityRepository
					.findByTableIdAndTableNameAndStage(task.getLeadid(), "TASK", "UPDATE");

			if (!updateEmailActivity.isPresent()) {
				if (days >= 30) {
					emailservice.sendEmail(task.getEmail(), "Update Test", "Update Test Body after one month");

					// save email Records
					Email email = new Email(0, task.getEmail(), "Update Test", "Update Test Body after one month",
							new Date(), new Date(), task.getUserid());
					emailRepository.save(email);

					// save Email Activity
					EmailActivity emailActivity = new EmailActivity(0, task.getTaskid(), "TASK", "BIRTHDATE",
							new Date(), new Date());
					emailActivityRepository.save(emailActivity);

				}
			}

		}
	}

	public List<String> getColumnDataTypes() {
        return taskRepository.getColumnDataTypes();
    }
}
