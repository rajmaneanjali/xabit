package com.xabit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class XabitApplication {

	public static void main(String[] args) {
		SpringApplication.run(XabitApplication.class, args);
	}

}
