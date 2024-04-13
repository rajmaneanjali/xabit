
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

import com.xabit.model.Task;
import com.xabit.service.TaskService;
import com.xabit.utility.Priority;

import ch.qos.logback.core.status.Status;
import lombok.AllArgsConstructor;

@RequestMapping("task")
@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class TaskController {
	@Autowired
	private TaskService taskService;

	
	@GetMapping("/{getByTaskList}")
	public List<Task> getAllTasks(){
		return taskService.getAllTasks();
	}
	

	@GetMapping("/datatype")
    public List<String> describeTable() {
        return taskService.getColumnDataTypes();
    }
	
	
	@GetMapping
	public List<Map<String, Object>> getAllTask(){
		return taskService.getAllTask();
	}
	@GetMapping("/byTaskId/{id}")
	public Task getTaskById(@PathVariable(value = "id") Integer id) {
		return taskService.getTaskById(id);
	}

	@GetMapping("/by-taskStatus/{taskStatus}")
	public List<Task> getTaskByStatusIgnoreCase(@PathVariable(value = "status") Status status) {
		return taskService.findByStatus(status.toString());
	}

	@GetMapping("/by-priority/{priority}")
	public List<Task> getTaskByPriorityIgnoreCase(@PathVariable(value="priority") Priority priority) {
		return taskService.findByPriority(priority.toString());
	}
	@PostMapping
	public Task createTask(@RequestBody Task task) {
		return taskService.createTask(task);
	}

	@PutMapping
	public Task updateTask(@RequestBody Task task) {
		return taskService.updateTask(task);
	}
	@DeleteMapping("/deleteTask/{id}")
	void deleteTask(@PathVariable(value = "id") Integer id) {
		taskService.deleteTask(id);
	}
	
	@DeleteMapping("/deleteByIds")
	public void deleteTaskByIds(@RequestBody List<Integer> taskIds) {
		taskService.deleteTaskByIds(taskIds);
	}
}
