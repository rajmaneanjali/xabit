package com.xabit.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.xabit.dto.ObjectDto;
import com.xabit.model.Email;
import com.xabit.model.EmailActivity;
import com.xabit.model.Lead;
import com.xabit.model.User;
import com.xabit.repository.AccountRepository;
import com.xabit.repository.CampaignRepository;
import com.xabit.repository.ContactRepository;
import com.xabit.repository.EmailActivityRepository;
import com.xabit.repository.EmailRepository;
import com.xabit.repository.LeadRepository;
import com.xabit.repository.OpportunityRepository;
import com.xabit.repository.TaskRepository;
import com.xabit.repository.UserRepository;
import com.xabit.util.MailUtil;

@Service
public class EmailService {
	@Autowired
	JavaMailSender emailSender;

	@Autowired
	private LeadRepository leadRepository;

	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private OpportunityRepository opportunityRepository;
	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private CampaignRepository campaignRepository;
	@Autowired
	private ContactRepository contactRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserDatabseUpdates userDatabseUpdates;

	@Autowired
	private EmailActivityRepository emailActivityRepository;
	
//	@Autowired
//	private EmailService emailservice;
	

	@Autowired
	private EmailRepository emailRepository;

	@Autowired
	private MailUtil mailUtil;

	public String sendEmail(String sentTo, String mailSubject, String mailBody) {

		try {
			SimpleMailMessage message = new SimpleMailMessage();

			message.setTo(sentTo);
			message.setSubject(mailSubject);
			message.setText(mailBody);
			emailSender.send(message);
		} catch (Exception e) {
			throw e;
		}
		return "Email Send Successfully";
	}

	public List<String> getByFieldName(String field) {
		List<String> listmap = new ArrayList<>();
		List<Map<String, Object>> listst = new ArrayList<>();
		if (field.equalsIgnoreCase("lead")) {
			listst = leadRepository.findByField();
		}

		else if (field.equalsIgnoreCase("account")) {
			listst = accountRepository.findByField();
		} else if (field.equalsIgnoreCase("campaign")) {
			listst = campaignRepository.findByField();
		} else if (field.equalsIgnoreCase("contact")) {
			listst = contactRepository.findByField();
		} else if (field.equalsIgnoreCase("user")) {
			listst = userRepository.findByField();
		} else if (field.equalsIgnoreCase("task")) {
			listst = taskRepository.findByField();
		} else if (field.equalsIgnoreCase("opportunity")) {
			listst = opportunityRepository.findByField();
		}
		for (Map<String, Object> map : listst) {
			listmap.add(map.get("Field").toString());

		}
		return listmap;

	}

	public List<?> getByValue(String field, String value) {
		List<?> lstData = userDatabseUpdates.findByValue(value, field);
		return lstData;

	}

	public void createFlowBuilder(ObjectDto objectDto) {
		EmailActivity od = new EmailActivity();
		od.setTableName(objectDto.getTableName());
		od.setConditionId(objectDto.getConditionId());
		od.setField(objectDto.getField());
		od.setOperator(objectDto.getOperator());
		od.setValue(objectDto.getValue());
		od.setOptimizeFor(objectDto.getOptimizeFor());
		od.setStage(objectDto.getOptimizeFor());
		emailActivityRepository.save(od);

		List<EmailActivity> listEmailActivity = new ArrayList<>();
		if (objectDto.getTableName().equalsIgnoreCase("CREATE")) {

			listEmailActivity = emailActivityRepository.findByTableNameAndStageAndValue(objectDto.getTableName(),
					od.getStage(), od.getValue());

			sendCreateEmail(listEmailActivity);
		} else if (objectDto.getTableName().equalsIgnoreCase("UPDATE")) {

			listEmailActivity = emailActivityRepository.findByTableNameAndStageAndValue(objectDto.getTableName(),
					od.getStage(), od.getValue());

			sendUpdateEmail(listEmailActivity);

		}

	}

	private void sendUpdateEmail(List<EmailActivity> listEmailActivity) {

		if (!listEmailActivity.isEmpty()) {
			List<Lead> leads = leadRepository.findAll();

			for (Lead lead : leads) {
				sendEmail(lead.getEmail(), "Update Test", "Update Test Body after one month");

				// save email Records
				Email email = new Email(0, lead.getEmail(), "Update Test", "Update Test Body after one month",
						new Date(), new Date(), lead.getUserid());
				emailRepository.save(email);

				// save Email Activity
				EmailActivity emailActivity = new EmailActivity(0, lead.getLeadid(), "LEAD", "BIRTHDATE", new Date(),
						new Date());
				emailActivityRepository.save(emailActivity);
			}
		}

	}

	private void sendBirthDateEmail(Optional<EmailActivity> emailActivityRecord1, User user, String todaysDate,
			Lead lead) {
		String birthDate = mailUtil.convertDateToString(user.getBirthDate());

		Long birthDays = mailUtil.calculateDiffBetweenTwoDays(todaysDate, birthDate);

		String daysAndMonthToday = mailUtil.getDaysAndMonth(new Date());

		String daysAndMonthBirtDate = mailUtil.getDaysAndMonth(user.getBirthDate());
		if (birthDays >= 365 && daysAndMonthToday.equalsIgnoreCase(daysAndMonthBirtDate)) {
			
			sendEmail(lead.getEmail(), "BirthDay Greetings", "Happy Birthday");

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

	private void sendCreateEmail(List<EmailActivity> listEmailActivity) {
		if (!listEmailActivity.isEmpty()) {

			// Long days = mailUtil.calculateDiffBetweenTwoDays(todaysDate, leadDate);

			List<Lead> leads = leadRepository.findAll();

			for (Lead lead : leads) {
				Integer hours = mailUtil.calculateDiffBetweenTwoHours(new Date(), lead.getCreatedDate());
				if (hours >= 2) {
					sendEmail(lead.getEmail(), "SchedularTest", "Schedular Body Test");

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

		
	}

}
