package com.xabit.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xabit.model.Lookup;
import com.xabit.model.LookupDto;
import com.xabit.model.LookupRelation;
import com.xabit.repository.LookupRelationRepository;
import com.xabit.repository.LookupRepository;

@Service
public class LookupService {

	@Autowired
	private LookupRepository lookupRepository;

	@Autowired
	private LookupRelationRepository lookupRelationRepository;

	public void createLookup(LookupDto lookupRequest) {
		try {

			Lookup lookup = lookupRepository.findByRelatedToAndRelatedWith(lookupRequest.getRelatedTo(),
					lookupRequest.getRelatedWith());
			if (Objects.isNull(lookup)) {

				lookup = new Lookup();
				lookup.setRelatedTo(lookupRequest.getRelatedTo());
				lookup.setRelatedWith(lookupRequest.getRelatedWith());
				lookup = lookupRepository.save(lookup);
			}

//			for (LookupRelation lookupRelation : lookupRequest.getLookupRelationList()) {
//				lookupRelation.setLookupId(lookup.getId());
//				lookupRelationRepository.save(lookupRelation);
//			}
		} catch (Exception e) {
			throw e;
		}

	}

}
