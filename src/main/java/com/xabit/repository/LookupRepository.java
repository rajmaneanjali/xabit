package com.xabit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xabit.model.Lookup;

@Repository
public interface LookupRepository extends JpaRepository<Lookup, Integer> {

	List<Lookup> findByRelatedTo(String relatesTo);

	Lookup findByRelatedToAndRelatedWith(String relatesTo, String relatedWith);

}
