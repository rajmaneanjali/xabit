package com.xabit.scheduletasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.xabit.service.LeadService;
import com.xabit.service.TaskService;

@Component
public class ScheduledTasks {

	@Autowired
	private LeadService leadService;
	
	@Autowired
	private TaskService taskService;

	// every hour at every day
//	@Scheduled(cron = "0 0 * * * *")
	//every 5 min
//	@Scheduled(cron = "*/5 * * * * ?") // sec min hr day(of the month) month day(of the week)
//	@Scheduled(cron ="0 */5 * * * 1-5")
	
//	@Scheduled(cron ="0 */5 * * * 1-5")  //Mon-1, tues-2, wednes-3, thurs-4, fri-5, satur-6,sun-7
//	@Scheduled(cron = "0 */5 * * * 1-5")//5sec
//	@Scheduled(cron = "0 0 */1 * * 1-5") // run the task at the top of every hour (0 0),every day of the week from Monday to Friday (MON-FRI)
//	@Scheduled(cron ="0 */5 * * * 1-5",zone = "EST" )
//	@Scheduled(cron = "0 */5 * * * 1-5", zone = "America/New_York") // Eastern Time (EST)

	public void leadSchedular() {
		leadService.leadSchedulerImpl();
//		taskService.taskSchedulerImpl();
		
	}


	


}
