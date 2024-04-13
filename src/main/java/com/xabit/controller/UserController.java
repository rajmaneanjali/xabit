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

import com.xabit.model.User;
import com.xabit.service.UserService;
import com.xabit.utility.ClassName;
import com.xabit.utility.Language;

import lombok.AllArgsConstructor;

@RequestMapping("user")
@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class UserController {
	@Autowired
	private UserService userService;

	@GetMapping("/{getByUserList}")
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}


	@GetMapping("/datatype")
    public List<String> describeTable() {
        return userService.getColumnDataTypes();
    }

	@GetMapping
	public List<Map<String, Object>> getAllUser() {
		return userService.getAllUser();
	}

	@GetMapping("/language/{language}")
	public List<User> getUserByLanguage(@PathVariable(value = "language") Language language) {
		return userService.findByLanguage(language.toString());
	}

	@GetMapping("/getbyId/{id}")
	public User getUserById(@PathVariable(value = "id") Integer id) {
		return userService.getUserByUserid(id);
	}

	@PostMapping
	public User createUser(@RequestBody User user) {
		return userService.createUser(user);
	}

	@PutMapping
	public User updateUser(@RequestBody User user) {
		return userService.updateUser(user);
	}

	@DeleteMapping("/delete/{id}")
	void deleteUser(@PathVariable(value = "id") Integer id) {
		userService.deleteUser(id);
	}

	@DeleteMapping("/deleteByIds")
	public void deleteUsersByIds(@RequestBody List<Integer> userIds) {
		userService.deleteUserByIds(userIds);
	}

	@GetMapping("/masterfield")
	public List<User> getMasterData() {
		return userService.getMasterData();
	}

	@GetMapping("/DynamicField")
	public List<Map<String, Object>> getDynamicfield(@RequestParam ClassName className) {
		return userService.getByDynamicUser(className);
	}

	@GetMapping("/DynamicFieldById")
	public List<Map<String, Object>> getDynamicfieldById(@RequestParam ClassName className,
			@RequestParam(value = "id") Integer id) {
		return userService.getByDynamicUserById(className, id);
	}

	@GetMapping("/getAllEnum")
	public Map<String, Object> getAllEnum() {
		return userService.getAllEnum();
	}
}
