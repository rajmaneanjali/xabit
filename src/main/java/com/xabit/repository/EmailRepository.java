package com.xabit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xabit.model.Email;

@Repository
public interface EmailRepository extends JpaRepository<Email, Integer> {

}
