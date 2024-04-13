package com.xabit.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.xabit.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	List<User> findAll();

	List<User> findByLanguageIgnoreCase(String language);

	@Query(value = "SELECT column_name, data_type FROM information_schema.columns WHERE table_schema = 'xabit' AND table_name = 'user'", nativeQuery = true)
	List<String> getColumnDataTypes();
	
	@Query(value = "select * from xabit.user where userid=:id", nativeQuery = true)
	User findByUserid(Integer id);

//	@Query("SELECT u FROM User u WHERE u.alias = :alias AND u.id <> :userId")
	User findByAliasAndUseridNot(@Param("alias") String alias, @Param("userid") String userid);

	Optional<User> findByAlias(String alias);

	@Query(value = "select distinct U.alias,U.company_name,U.created_by,U.created_date, U.email,U.first_name,U.language,U.last_modified_by, U.last_modified_date,U.last_name,U.locale,U.mobile,U.title, U.user_name,U.userid from xabit.user as U INNER JOIN xabit.master_value_entity as MEU ON U.userid=MEU.relation_id INNER JOIN xabit.master_entity as ME ON ME.id=MEU.master_id", nativeQuery = true)
	List<Map<String, Object>> findByDynamicUser();

	@Query(value = "select * from xabit.user", nativeQuery = true)
	List<Map<String, Object>> findByDynamicAllUser();
//	List<User> getMasterData();

	@Query(value = "select * from xabit.user as u where u.userid=:id", nativeQuery = true)
	List<Map<String, Object>> findByDynamicUserById(Integer id);

	@Query(value = "select * from xabit.user", nativeQuery = true)
	List<Map<String, Object>> findAllUsers();
	
	@Query(value = "describe xabit.user" ,nativeQuery = true)
	List<Map<String,Object>> findByField();
}
