package com.xabit.repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.xabit.model.Account;
import com.xabit.model.Contact;
import com.xabit.model.Request;
import com.xabit.utility.ContactField;
import com.xabit.utility.Operator;

@Repository
public class ContactCustomRepositoryImpl implements ContactCustomRepository {

	@PersistenceContext
	private EntityManager entityManager; // used by criteria

	@Override
	public Map<String, Object> findAllAccountContact(Request request) {
		Map<String, Object> map = new LinkedHashMap<>();

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Account> criteriaQuery = criteriaBuilder.createQuery(Account.class);
		Root<Contact> contact = criteriaQuery.from(Contact.class);

		Root<Account> account = criteriaQuery.from(Account.class);

		criteriaQuery.multiselect(account, contact.get("accountid"));

		List<Predicate> predicates = new ArrayList<>();

		predicates.add(criteriaBuilder.equal(account, contact.get("accountid")));

		// Searching
		if (request.getValue() != null && request.getField() != null) {
			if (ContactField.ACCOUNT_ACCOUNT_NUMBER.toString().equalsIgnoreCase(request.getField())) {

				if (Operator.EQUALS.toString().equalsIgnoreCase(request.getOperator())) {
					predicates.add(criteriaBuilder.equal(account.get("accountNumber"), request.getValue().toString()));

				} else if (Operator.NOT_EQUALS_TO.toString().equalsIgnoreCase(request.getOperator())) {
					predicates.add(criteriaBuilder.notEqual(account.get("accountNumber"), request.getValue().toString()));

				}

			}
		}

		criteriaQuery.where(predicates.toArray(new Predicate[0]));

		// Perform the query
		TypedQuery<Account> query = entityManager.createQuery(criteriaQuery);
		List<Account> recordsList = query.getResultList();
		map.put("data", recordsList);
		return map;
	}

}
