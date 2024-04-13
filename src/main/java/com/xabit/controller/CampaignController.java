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

import com.xabit.model.Campaign;
import com.xabit.service.CampaignService;

import lombok.AllArgsConstructor;

@RequestMapping("campaign")
@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class CampaignController {
	@Autowired
	private CampaignService campaignService;

	@GetMapping("/{getByCampaignList}")
	public List<Campaign> getAllCampaigns() {
		return campaignService.getAllCampaigns();
	}

	@GetMapping
	public List<Map<String, Object>> getAllCampaign() {
		return campaignService.getAllCampaign();
	}

	@GetMapping("/datatype")
	public List<String> describeTable() {
		return campaignService.getColumnDataTypes();
	}

	@GetMapping("/getbyId/{id}")
	public Campaign getCampaignById(@PathVariable(value = "id") Integer id) {
		return campaignService.getCampaignById(id);
	}

	@GetMapping("/by-status/{status}")
	public List<Campaign> getCampaignByStatusIgnoreCase(@PathVariable String status) {
		return campaignService.findByStatus(status);
	}

	@GetMapping("/by-campaigntype/{campaigntype}")
	public List<Campaign> getCampaignByTypeIgnoreCase(@PathVariable String type) {
		return campaignService.findByType(type);
	}

	@PostMapping
	public Campaign createCampaign(@RequestBody Campaign campaign) {
		return campaignService.createCampaign(campaign);
	}

	@PutMapping
	public Campaign updateCampaign(@RequestBody Campaign campaign) {
		return campaignService.updateCampaign(campaign);
	}

	@DeleteMapping("/id/{campaignid}")
	void deleteCampaign(@PathVariable(value = "campaignid") Integer id) {
		campaignService.deleteCampaign(id);
	}

	@DeleteMapping("/deleteByIds")
	public void deleteCampaignsByIds(@RequestBody List<Integer> campaignIds) {
		campaignService.deleteCampaignByIds(campaignIds);
	}
}
