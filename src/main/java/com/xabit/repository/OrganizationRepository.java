package com.xabit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xabit.model.Organization;
@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Integer>{

}
