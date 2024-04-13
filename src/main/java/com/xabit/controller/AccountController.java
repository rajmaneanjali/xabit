package com.xabit.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xabit.model.Account;
import com.xabit.service.AccountService;

import lombok.AllArgsConstructor;

@RequestMapping("accounts")
@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class AccountController {
	@Autowired
	private AccountService accountService;

	@GetMapping("/{getByAccountList}")
	public List<Account> getAllAccounts() {
		return accountService.getAllAccounts();
	}
	@GetMapping
	public List<Map<String, Object>> getAllAccount() {
		return accountService.getAllAccount();
	}
	
	@GetMapping("/datatype")
    public List<String> describeTable() {
        return accountService.getColumnDataTypes();
    }
	
	@GetMapping("/getbyId/{id}")
	public Account getAccountById(@PathVariable(value = "id") Integer id) {
		return accountService.getAccountById(id);
	}

	@GetMapping("/by-rating/{rating}")
	public List<Account> getAccountByStageIgnoreCase(@PathVariable String rating) {
		return accountService.findByRating(rating);
	}
	@GetMapping("/by-ownership/{ownership}")
	public List<Account> getAccountByOwnershipIgnoreCase(@PathVariable String ownership) {
		return accountService.findByRating(ownership);
	}
	@GetMapping("/by-accountType/{accountType}")
	public List<Account> getAccountByAccountTypeIgnoreCase(@PathVariable String accountType) {
		return accountService.findByAccountType(accountType);
	}
	@GetMapping("/by-accountindustry/{accountindustry}")
	public List<Account> getAccountByIndustryIgnoreCase(@PathVariable String industry) {
		return accountService.findByIndustry(industry);
	}
	@GetMapping("/by-sla/{sla}")
	public List<Account> getAccountByslaIgnoreCase(@PathVariable String sla) {
		return accountService.findBySla(sla);
	}
	@GetMapping("/by-upsellOpportunity/{upsellOpportunity}")
	public List<Account> getAccountByUpsellOpportunityIgnoreCase(@PathVariable String upsellOpportunity) {
		return accountService.findByUpsellOpportunity(upsellOpportunity);
	}
	@GetMapping("/by-customerPriority/{customerPriority}")
	public List<Account> getAccountByCustomerPriorityIgnoreCase(@PathVariable String customerPriority) {
		return accountService.findByCustomerPriority(customerPriority);
	}
//	@GetMapping("/by-active/{active}")
//	public List<Account> getAccountByActiveIgnoreCase(@PathVariable String active) {
//		return accountService.findByActive(active);
//	}

	@PostMapping
	public Account createAccount(@RequestBody Account account) {
		return accountService.createAccount(account);
	}

	@PutMapping
	public Account updateAccount(@RequestBody Account account) {
		return accountService.updateAccount(account);
	}

	@DeleteMapping("id/{accountid}")
	void deleteAccount(@PathVariable(value = "accountid") Integer id) {
		accountService.deleteAccount(id);
	}
	@DeleteMapping("/deleteByIds")
	public void deleteAccountsByIds(@RequestBody List<Integer> accountIds) {
		accountService.deleteAccountByIds(accountIds);
	}
}
