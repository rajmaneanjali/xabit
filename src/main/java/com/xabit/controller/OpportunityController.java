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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xabit.model.Opportunity;
import com.xabit.model.Request;
import com.xabit.repository.OpportunityRepository;
import com.xabit.service.OpportunityService;
import com.xabit.utility.Field;
import com.xabit.utility.Leadsource;
import com.xabit.utility.Operator;
import com.xabit.utility.Stage;
import com.xabit.utility.Status;
import com.xabit.utility.Type;

import lombok.AllArgsConstructor;

@RequestMapping("opportunity")
@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class OpportunityController {
	@Autowired
	private OpportunityService opportunityService;

	@GetMapping
	public List<Map<String, Object>> getAllOpportunity() {
		return opportunityService.getAllOpportunity();
	}
	
	@GetMapping("/enum")
	public List<Opportunity> getAllUtilityApi() {
		return opportunityService.getAllUtilityApi();
	}
	
	@GetMapping("/{getByOpportunitylist}")
	public List<Opportunity> getAllOpportunities() {
		return opportunityService.getAllOpportunities();
	}
	

	@GetMapping("/datatype")
    public List<String> describeTable() {
        return opportunityService.getColumnDataTypes();
    }
	
	
//	@GetMapping("/datatype")
//	public Map<String, Object> findAllDataTypes(){
//		return opportunityService.findAllDataTypes();	}
//	
	@GetMapping("/opportunity/{id}")
	public Opportunity getOpportunityById(@PathVariable(value = "id") Integer id) {
		return opportunityService.getOpportunityById(id);
	}

	@GetMapping("/by-type/{type}")
	public List<Opportunity> getOpportunityByType(@PathVariable(value = "type") Type type) {
		return opportunityService.findByType(type.toString());
	}

	@GetMapping("/by-leadSource/{leadSource}")
	public List<Opportunity> getOpportunityByleadSourceIgnoreCase(
			@PathVariable(value = "leadSource") Leadsource leadSource) {
		return opportunityService.findByleadSource(leadSource.toString());
	}

	@GetMapping("/by-stage/{stage}")
	public List<Opportunity> getOpportunityByStageIgnoreCase(@PathVariable(value = "stage") Stage stage) {
		return opportunityService.findByStage(stage.toString());
	}

	@GetMapping("/by-status/{status}")
	public List<Opportunity> getOpportunityByStatusIgnoreCase(@PathVariable(value = "status") Status status) {
		return opportunityService.findByStatus(status.toString());
	}

//	@GetMapping("/filter")
//	public List<Opportunity> findByFieldAndOperator(@RequestParam Field field, @RequestParam Operator operator,
//			@RequestParam(required = true) String value) {
//		return opportunityService.findByFilter(field, operator, value);
//
//	}

//	@PostMapping("/filter/{FilterPagination}")
	@PostMapping("/getOpportunityFilter")
	public Map<String, Object> getByFilter(@RequestBody Request request) {
		return opportunityService.findByFilterPagination(request.getField(), request.getOperator(), request.getValue(),
				request);
	}

	@PostMapping
	public Opportunity createOpportunity(@RequestBody Opportunity opportunity) {
		return opportunityService.createOpportunity(opportunity);
	}

	@PutMapping
	public Opportunity updateOpportunity(@RequestBody Opportunity opportunity) {
		return opportunityService.updateOpportunity(opportunity);
	}

	@DeleteMapping("/delete/{id}")
	void deleteOpportunity(@PathVariable(value = "id") Integer id) {
		opportunityService.deleteOpportunity(id);
	}

	@DeleteMapping("/deleteByIds")
	public void deleteOpportunityByIds(@RequestBody List<Integer> opportunityIds) {
		opportunityService.deleteOpportunitiesByIds(opportunityIds);
	}
}
