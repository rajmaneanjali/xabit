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
import org.springframework.web.multipart.MultipartFile;

import com.xabit.model.Contact;
import com.xabit.model.Request;
import com.xabit.service.ContactService;

import lombok.AllArgsConstructor;

@RequestMapping("contact")
@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class ContactController {
	@Autowired
	private ContactService contactService;
	
	@GetMapping("/{getByContactList}")
	public List<Contact> getAllContacts() {
		return contactService.getAllContacts();
	}
	@GetMapping
	public List<Map<String, Object>> getAllContact() {
		return contactService.getAllContact();
	}
	

	@GetMapping("/datatype")
    public List<String> describeTable() {
        return contactService.getColumnDataTypes();
    }
	
	@GetMapping("/getById/{id}")
	public Contact getContactById(@PathVariable(value = "id") Integer id) {
		return contactService.getContactById(id);
	}

	@PostMapping("/uploadImage")
	public Contact UploadContactImage(@RequestParam(value = "image") MultipartFile file,
			@RequestParam(value = "contactid") Integer contactid) {
		return contactService.uploadContactImage(file, contactid);
	}

	@GetMapping("/by-leadSource/{leadSource}")
	public List<Contact> getContactByLeadSourceIgnoreCase(@PathVariable String leadSource) {
		return contactService.findByLeadSource(leadSource);
	}

	@GetMapping("/by-level/{level}")
	public List<Contact> getContactByLevelIgnoreCase(@PathVariable String level) {
		return contactService.findByLevel(level);
	}

	@PostMapping
	public Contact createContact(@RequestBody Contact contact) {
		return contactService.createContact(contact);
	}

	@PostMapping(value = "/getContactFilter")
	public Map<String, Object> getByFilter(@RequestBody Request request) {
		return contactService.findByFilter(request);
	}

	@PutMapping
	public Contact updateContact(@RequestBody Contact contact) {
		return contactService.updateContact(contact);
	}

	@DeleteMapping("delete/{id}")
	void deleteContact(@PathVariable(value = "id") Integer id) {
		contactService.deleteContact(id);
	}

	@DeleteMapping("/deleteByIds")
	public void deleteContactsByIds(@RequestBody List<Integer> contactIds) {
		contactService.deleteContactByIds(contactIds);
	}
}
