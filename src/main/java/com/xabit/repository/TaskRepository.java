package com.xabit.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xabit.model.Lead;
import com.xabit.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

	List<Task> findAll();
	List<Task> findByStatusIgnoreCase(String status);
	
	@Query(value = "SELECT column_name, data_type FROM information_schema.columns WHERE table_schema = 'xabit' AND table_name = 'task'", nativeQuery = true)
	List<String> getColumnDataTypes();

	List<Task> findByPriorityIgnoreCase(String priority);

	@Query(value = "select * from xabit.task", nativeQuery = true)
	List<Map<String, Object>> findByDynamicAllTask();

	@Query(value = "select * from xabit.task as u where u.taskid=:id", nativeQuery = true)
	List<Map<String, Object>> findByDynamicTaskById(Integer id);

	@Query(value = "select * from xabit.task", nativeQuery = true)
	List<Map<String, Object>> findAllTasks();

	List<Task> findByTaskid(Integer id);
	
	@Query(value = "describe xabit.task" ,nativeQuery = true)
	List<Map<String,Object>> findByField();
}
