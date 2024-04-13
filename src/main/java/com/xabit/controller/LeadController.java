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

import com.xabit.model.Lead;
import com.xabit.service.LeadService;
import com.xabit.utility.Industry;
import com.xabit.utility.Leadstatus;
import com.xabit.utility.ProductInterest;
import com.xabit.utility.Rating;

import lombok.AllArgsConstructor;

@RequestMapping("lead")
@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class LeadController {
	@Autowired
	private LeadService leadService;
	
	@GetMapping("/{getByLeadList}")
	public List<Lead> getAllLeads() {
		return leadService.getAllLeads();
	}
	
	@GetMapping("/datatype")
    public List<String> describeTable() {
        return leadService.getColumnDataTypes();
    }
	
	@GetMapping("/getByUserid/{userid}")
	public String getUserEmailResponse(@PathVariable(value="userid") Integer id) {
		return leadService.getUserEmailResponse(id);
	}
	
	@GetMapping
	public List<Map<String, Object>> getAllLead() {
		return leadService.getAllLead();
	}
	@GetMapping("/getLeadById/{id}")
	public Lead getLeadById(@PathVariable(value = "id") Integer id) {
		return leadService.getLeadById(id);
	}

	@PostMapping
	public Lead createLead(@RequestBody Lead lead) {
		return leadService.createLead(lead);
	}

	@GetMapping("/by-leadSource/{leadSource}")
	public List<Lead> getLeadByLeadSourceIgnoreCase(@PathVariable(value="leadSource") Leadstatus leadSource) {
		return leadService.findByLeadSource(leadSource.toString());
	}
	@GetMapping("/by-leadStatus/{leadStatus}")
	public List<Lead> getLeadByLeadStatusIgnoreCase(@PathVariable(value="leadStatus") Leadstatus leadStatus) {
		return leadService.findByLeadStatus(leadStatus.toString());
	}
	@GetMapping("/by-rating/{rating}")
	public List<Lead> getLeadByRatingIgnoreCase(@PathVariable(value="rating") Rating rating) {
		return leadService.findByRating(rating.toString());
	}
	@GetMapping("/by-leadindustry/{leadindustry}")
	public List<Lead> getLeadByIndustryIgnoreCase(@PathVariable(value="industry") Industry industry) {
		return leadService.findByIndustry(industry.toString());
	}
	@GetMapping("/by-productInterest/{productInterest}")
	public List<Lead> getLeadByProductInterestIgnoreCase(@PathVariable(value="productInterest") ProductInterest productInterest) {
		return leadService.findByProductInterest(productInterest.toString());
	}


	@PutMapping
	public Lead updateLead(@RequestBody Lead lead) {
		return leadService.updateLead(lead);
	}

	@DeleteMapping("/deleteLead/{id}")
	void deleteLead(@PathVariable(value = "id") Integer id) {
		leadService.deleteLead(id);
	}
	@DeleteMapping("/deleteByIds")
	public void deleteLeadsByIds(@RequestBody List<Integer> leadIds) {
		leadService.deleteLeadByIds(leadIds);
	}
}
