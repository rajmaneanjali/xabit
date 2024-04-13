package com.xabit.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.springframework.stereotype.Component;

@Component
public class MailUtil {

	public Long calculateDiffBetweenTwoDays(String todaysDate, String inputDate) {

		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");

		Long days = 0l;
		try {
			Date date1 = myFormat.parse(todaysDate);
			Date date2 = myFormat.parse(inputDate);
			Long diff = date1.getTime() - date2.getTime();
			System.out.println("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
			days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return days;
	}

	public Integer calculateDiffBetweenTwoHours(Date todaysDate, Date inputDate) {

		final int MILLI_TO_HOUR = 1000 * 60 * 60;
		return (int) (todaysDate.getTime() - inputDate.getTime()) / MILLI_TO_HOUR;
	}

	public String convertDateToString(Date date) {

		String pattern = "yyyy-MM-dd";
		// Create an instance of SimpleDateFormat used for formatting
		// the string representation of date according to the chosen pattern
		DateFormat df = new SimpleDateFormat(pattern);
		// representation of a date with the defined format.
		String stringDate = df.format(date);
		// Print the result!
		System.out.println("Today is: " + stringDate);
		return stringDate;
	}

	public String getDaysAndMonth(Date date) {

		String pattern = "yyyy-MM-dd";
		// Create an instance of SimpleDateFormat used for formatting
		// the string representation of date according to the chosen pattern
		DateFormat df = new SimpleDateFormat(pattern);
		// representation of a date with the defined format.
		String stringDate = df.format(date);
		// Print the result!
		System.out.println("Today is: " + stringDate);

		String[] split = stringDate.split("-");

		String daysandMonth = split[1].toString().concat(split[2].toString());

		return daysandMonth;
	}
}
