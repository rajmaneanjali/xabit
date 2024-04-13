package com.xabit.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.xabit.model.DynamicException;

@RestControllerAdvice
public class RestControllerException extends RuntimeException {
	@ExceptionHandler({ Exception.class })
	public String handleException(Exception e) {
		return e.getMessage();
	}

	@ExceptionHandler({ DynamicException.class })
	public String handleException(DynamicException e) {
		return "Column Name Already Exist";
	}
}
