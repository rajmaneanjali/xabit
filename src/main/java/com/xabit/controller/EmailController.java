package com.xabit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xabit.dto.ObjectDto;
import com.xabit.service.EmailService;

import lombok.AllArgsConstructor;

@RequestMapping("email")
@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class EmailController {

	@Autowired
	EmailService emailService;

	@PostMapping("/uploadmail")
	public String UploadContactMail(@RequestParam(value = "sentTo") String sentTo,
			@RequestParam(value = "mailSubject") String mailSubject,
			@RequestParam(value = "mailBody") String mailBody) {
		return emailService.sendEmail(sentTo, mailSubject, mailBody);
	}

	@GetMapping("/fieldAPI/{field}")
	public List<String> getByFieldName(@PathVariable(value = "field") String field) {
		return emailService.getByFieldName(field);
	}

	@GetMapping("/valueApi")
	public List<?> getByValue(@RequestParam(value = "field") String field,
			@RequestParam(value = "value") String value) {
		return emailService.getByValue(field, value);
	}

	@PostMapping("/FlowBuilder")
	public String createFlowBuilder(@RequestBody ObjectDto objectDto) {
		emailService.createFlowBuilder(objectDto);
		return "Record Created Successfully";
	}
}
