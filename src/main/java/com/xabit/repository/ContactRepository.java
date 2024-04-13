package com.xabit.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xabit.model.Account;
import com.xabit.model.Contact;
import com.xabit.model.Lead;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {
	
	List<Contact> findAll();
	List<Contact> findByLeadSourceIgnoreCase(String leadSource);

	List<Contact> findByLevelIgnoreCase(String level);
	
	@Query(value = "SELECT column_name, data_type FROM information_schema.columns WHERE table_schema = 'xabit' AND table_name = 'contact'", nativeQuery = true)
	List<String> getColumnDataTypes();

	// In Account fields
	// accountNumber
	@Query(value = "SELECT xabit.account.account_name,xabit.account.account_number FROM  xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.account_number = :value", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactaccountNumber(String value);

	@Query(value = "SELECT xabit.account.account_name,xabit.account.account_number FROM  xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.account_number != :value", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactaccountNumberContactNotIn(String value);

	@Query(value = "SELECT xabit.account.account_name,xabit.account.account_number FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.account_number Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactaccountNumberContainingIgnoreCase(String value);

	@Query(value = "SELECT xabit.account.account_name,xabit.account.account_number FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.account_number Not Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactaccountNumberNotContainingIgnoreCase(String value);

	@Query(value = "SELECT xabit.account.account_name,xabit.account.account_number FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.account_number Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactaccountNumberStartsWithIgnoreCase(String value);

	// accountSource
	@Query(value = "SELECT xabit.account.account_source,xabit.account.account_number FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.account_source Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactaccountSourceContainingIgnoreCase(String value);

	@Query(value = "SELECT xabit.account.account_source,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.account_source Not Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactaccountSourceNotContainingIgnoreCase(String value);

	@Query(value = "SELECT xabit.account.account_source,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.account_source Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactaccountSourceStartsWithIgnoreCase(String value);

	// billingCity
	@Query(value = "SELECT xabit.account.billing_city,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.billing_city Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactbillingCityContainingIgnoreCase(String value);

	@Query(value = "SELECT xabit.account.billing_city,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.billing_city Not Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactbillingCityNotContainingIgnoreCase(String value);

	@Query(value = "SELECT xabit.account.billing_city,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.billing_city Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactbillingCityStartsWithIgnoreCase(String value);

	// billingCountry
	@Query(value = "SELECT xabit.account.billing_country,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.billing_country Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactbillingCountryContainingIgnoreCase(String value);

	@Query(value = "SELECT xabit.account.billing_country,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.billing_country Not Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactbillingCountryNotContainingIgnoreCase(String value);

	@Query(value = "SELECT xabit.account.billing_country,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.billing_country Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactbillingCountryStartsWithIgnoreCase(String value);

	// billingState
	@Query(value = "SELECT xabit.account.billing_state,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.billing_state Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactbillingStateContainingIgnoreCase(String value);

	@Query(value = "SELECT xabit.account.billing_state,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.billing_state Not Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactbillingStateNotContainingIgnoreCase(String value);

	@Query(value = "SELECT xabit.account.billing_state,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.billing_state Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactbillingStateStartsWithIgnoreCase(String value);

	// customerPriority
	@Query(value = "SELECT xabit.account.customer_priority,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.customer_priority Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactcustomerPriorityContainingIgnoreCase(String value);

	@Query(value = "SELECT xabit.account.billing_city,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.customer_priority Not Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactcustomerPriorityNotContainingIgnoreCase(String value);

	@Query(value = "SELECT xabit.account.billing_city,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.customer_priority Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactcustomerPriorityStartsWithIgnoreCase(String value);

	// billingStreet
	@Query(value = "SELECT xabit.account.billing_street,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.billing_street Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactbillingStreetContainingIgnoreCase(String value);

	@Query(value = "SELECT xabit.account.billing_street,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.billing_street Not Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactbillingStreetNotContainingIgnoreCase(String value);

	@Query(value = "SELECT xabit.account.billing_street,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.billing_street Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactbillingStreetStartsWithIgnoreCase(String value);

	// fax
	@Query(value = "SELECT xabit.account.fax,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.fax Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactFaxContainingIgnoreCase(String value);

	@Query(value = "SELECT xabit.account.fax,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.fax Not Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactFaxNotContainingIgnoreCase(String value);

	@Query(value = "SELECT xabit.account.fax,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.fax Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactFaxStartsWithIgnoreCase(String value);

	// industry
	@Query(value = "SELECT xabit.account.industry,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.industry Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactIndustryContainingIgnoreCase(String value);

	@Query(value = "SELECT xabit.account.industry,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.industry Not Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactIndustryNotContainingIgnoreCase(String value);

	@Query(value = "SELECT xabit.account.industry,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.industry Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactIndustryStartsWithIgnoreCase(String value);

	// ownership
	@Query(value = "SELECT xabit.account.ownership,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.ownership Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactOwnershipContainingIgnoreCase(String value);

	@Query(value = "SELECT xabit.account.billing_city,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.ownership Not Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactOwnershipNotContainingIgnoreCase(String value);

	@Query(value = "SELECT xabit.account.billing_city,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.ownership Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactOwnershipStartsWithIgnoreCase(String value);

	// rating
	@Query(value = "SELECT xabit.account.rating,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.rating Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactRatingContainingIgnoreCase(String value);

	@Query(value = "SELECT xabit.account.billing_city,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.rating Not Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactRatingNotContainingIgnoreCase(String value);

	@Query(value = "SELECT xabit.account.billing_city,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.rating Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactRatingStartsWithIgnoreCase(String value);

	// shippingCity
	@Query(value = "SELECT xabit.account.shipping_city,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.shipping_city Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactshippingCityContainingIgnoreCase(String value);

	@Query(value = "SELECT xabit.account.billing_city,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.shipping_city Not Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactshippingCityNotContainingIgnoreCase(String value);

	@Query(value = "SELECT xabit.account.billing_city,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.shipping_city Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactshippingCityStartsWithIgnoreCase(String value);

	// shippingCountry shipping_country
	@Query(value = "SELECT xabit.account.shipping_country,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.shipping_country Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactshippingCountryContainingIgnoreCase(String value);

	@Query(value = "SELECT xabit.account.shipping_country,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.shipping_country Not Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactshippingCountryNotContainingIgnoreCase(String value);

	@Query(value = "SELECT xabit.account.shipping_country,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.shipping_country Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactshippingCountryStartsWithIgnoreCase(String value);

	// shippingStreet shipping_street
	@Query(value = "SELECT xabit.account.shipping_street,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.shipping_street Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactshippingStreetContainingIgnoreCase(String value);

	@Query(value = "SELECT xabit.account.shipping_street,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.shipping_street Not Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactshippingStreetNotContainingIgnoreCase(String value);

	@Query(value = "SELECT xabit.account.shipping_street,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.shipping_street Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactshippingStreetStartsWithIgnoreCase(String value);

	// shippingState shipping_state
	@Query(value = "SELECT xabit.account.shipping_state,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.shipping_state Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactshippingStateContainingIgnoreCase(String value);

	@Query(value = "SELECT xabit.account.shipping_state,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.shipping_state Not Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactshippingStateNotContainingIgnoreCase(String value);

	@Query(value = "SELECT xabit.account.shipping_state,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.shipping_state Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactshippingStateStartsWithIgnoreCase(String value);

	// sla sla
	@Query(value = "SELECT xabit.account.sla,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.sla Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactSlaContainingIgnoreCase(String value);

	@Query(value = "SELECT xabit.account.sla,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.sla Not Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactSlaNotContainingIgnoreCase(String value);

	@Query(value = "SELECT xabit.account.sla,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.sla Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactSlaStartsWithIgnoreCase(String value);

	// tickerSymbol ticker_symbol
	@Query(value = "SELECT xabit.account.ticker_symbol,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.ticker_symbol Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactTickerSymbolContainingIgnoreCase(String value);

	@Query(value = "SELECT xabit.account.ticker_symbol,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.ticker_symbol Not Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactTickerSymbolNotContainingIgnoreCase(String value);

	@Query(value = "SELECT xabit.account.ticker_symbol,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.ticker_symbol Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactTickerSymbolStartsWithIgnoreCase(String value);

	// accountType account_type
	@Query(value = "SELECT xabit.account.account_type,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.account_type Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactaccountTypeContainingIgnoreCase(String value);

	@Query(value = "SELECT xabit.account.account_type,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.account_type Not Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactaccountTypeNotContainingIgnoreCase(String value);

	@Query(value = "SELECT xabit.account.account_type,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.account_type Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactaccountTypeStartsWithIgnoreCase(String value);

	// description description
	@Query(value = "SELECT xabit.account.description,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.description Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactDescriptionContainingIgnoreCase(String value);

	@Query(value = "SELECT xabit.account.description,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.description Not Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactDescriptionNotContainingIgnoreCase(String value);

	@Query(value = "SELECT xabit.account.description,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.description Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactDescriptionStartsWithIgnoreCase(String value);

	// upsellOpportunity upsell_opportunity
	@Query(value = "SELECT xabit.account.upsell_opportunity,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.upsell_opportunity Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactupsellOpportunityContainingIgnoreCase(String value);

	@Query(value = "SELECT xabit.account.upsell_opportunity,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.upsell_opportunity Not Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactupsellOpportunityNotContainingIgnoreCase(String value);

	@Query(value = "SELECT xabit.account.upsell_opportunity,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.upsell_opportunity Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactupsellOpportunityStartsWithIgnoreCase(String value);

	// website website
	@Query(value = "SELECT xabit.account.website,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.website Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactWebsiteContainingIgnoreCase(String value);

	@Query(value = "SELECT xabit.account.website,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.website Not Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactWebsiteNotContainingIgnoreCase(String value);

	@Query(value = "SELECT xabit.account.website,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.website Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactWebsiteStartsWithIgnoreCase(String value);

	// yearStarted year_started
	@Query(value = "SELECT xabit.account.year_started,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.year_started Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactyearStartedContainingIgnoreCase(String value);

	@Query(value = "SELECT xabit.account.year_started,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.year_started Not Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactyearStartedNotContainingIgnoreCase(String value);

	@Query(value = "SELECT xabit.account.year_started,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.year_started Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactyearStartedStartsWithIgnoreCase(String value);

	// accountName account_name
	@Query(value = "SELECT xabit.account.account_name,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.account_name Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactaccountNameContainingIgnoreCase(String value);

	@Query(value = "SELECT xabit.account.account_name,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.account_name Not Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactaccountNameNotContainingIgnoreCase(String value);

	@Query(value = "SELECT xabit.account.account_name,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.account_name Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactaccountNameStartsWithIgnoreCase(String value);

	// accountSite account_site
	@Query(value = "SELECT xabit.account.account_site,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.account_site Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactaccountSiteContainingIgnoreCase(String value);

	@Query(value = "SELECT xabit.account.account_site,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.account_site Not Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactaccountSiteNotContainingIgnoreCase(String value);

	@Query(value = "SELECT xabit.account.account_site,xabit.account.account_name FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.account_site Like %:value%", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactaccountSiteStartsWithIgnoreCase(String value);

//---------------------------------------------------------------------------------------------------------------------------------
	// active(boolean)
	@Query(value = "SELECT xabit.account.account_site,xabit.account.account_name, xabit.account.active FROM xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.active=?", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactActive(Boolean isActive);

	// annualRevenue(long)
	@Query(value = "SELECT xabit.account.annual_revenue,xabit.account.account_number FROM  xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.annual_revenue = :value", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactAnnualRevenue(String value);

	@Query(value = "SELECT xabit.account.annual_revenue,xabit.account.account_number FROM  xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.annual_revenue!= :value", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactAnnualRevenueNot(String value);

	@Query(value = "SELECT xabit.account.annual_revenue,xabit.account.account_number FROM  xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.annual_revenue > :value", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactAnnualRevenueGreaterThan(String value);

	@Query(value = "SELECT xabit.account.annual_revenue,xabit.account.account_number FROM  xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.annual_revenue < :value", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactAnnualRevenueLessThan(String value);

	@Query(value = "SELECT xabit.account.annual_revenue,xabit.account.account_number FROM  xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.annual_revenue >= :value", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactAnnualRevenueGreaterThanEqual(String value);

	@Query(value = "SELECT xabit.account.annual_revenue,xabit.account.account_number FROM  xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.annual_revenue <= :value", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactAnnualRevenueLessThanEqual(String value);

	// billingZipCode(Integer) billing_zip_code
	@Query(value = "SELECT xabit.account.billing_zip_code,xabit.account.account_number FROM  xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.billing_zip_code = :value", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactbillingZipCode(String value);

	@Query(value = "SELECT xabit.account.billing_zip_code,xabit.account.account_number FROM  xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.billing_zip_code!= :value", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactbillingZipCodeNot(String value);

	@Query(value = "SELECT xabit.account.billing_zip_code,xabit.account.account_number FROM  xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.billing_zip_code > :value", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactbillingZipCodeGreaterThan(String value);

	@Query(value = "SELECT xabit.account.billing_zip_code,xabit.account.account_number FROM  xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.billing_zip_code < :value", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactbillingZipCodeLessThan(String value);

	@Query(value = "SELECT xabit.account.billing_zip_code,xabit.account.account_number FROM  xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.billing_zip_code >= :value", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactbillingZipCodeGreaterThanEqual(String value);

	@Query(value = "SELECT xabit.account.billing_zip_code,xabit.account.account_number FROM  xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.billing_zip_code <= :value", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactbillingZipCodeLessThanEqual(String value);

	// shippingZipCode(Integer) shipping_zip_code
	@Query(value = "SELECT xabit.account.shipping_zip_code,xabit.account.account_number FROM  xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.shipping_zip_code = :value", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactshippingZipCode(String value);

	@Query(value = "SELECT xabit.account.shipping_zip_code,xabit.account.account_number FROM  xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.shipping_zip_code!= :value", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactshippingZipCodeNot(String value);

	@Query(value = "SELECT xabit.account.shipping_zip_code,xabit.account.account_number FROM  xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.shipping_zip_code > :value", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactshippingZipCodeGreaterThan(String value);

	@Query(value = "SELECT xabit.account.shipping_zip_code,xabit.account.account_number FROM  xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.shipping_zip_code < :value", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactshippingZipCodeLessThan(String value);

	@Query(value = "SELECT xabit.account.shipping_zip_code,xabit.account.account_number FROM  xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.shipping_zip_code >= :value", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactshippingZipCodeGreaterThanEqual(String value);

	@Query(value = "SELECT xabit.account.shipping_zip_code,xabit.account.account_number FROM  xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.shipping_zip_code <= :value", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactshippingZipCodeLessThanEqual(String value);

	// employees(Integer) employees
	@Query(value = "SELECT xabit.account.employees,xabit.account.account_number FROM  xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.employees = :value", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactEmployees(String value);

	@Query(value = "SELECT xabit.account.employees,xabit.account.account_number FROM  xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.employees!= :value", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactEmployeesNot(String value);

	@Query(value = "SELECT xabit.account.employees,xabit.account.account_number FROM  xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.employees > :value", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactEmployeesGreaterThan(String value);

	@Query(value = "SELECT xabit.account.employees,xabit.account.account_number FROM  xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.employees < :value", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactEmployeesLessThan(String value);

	@Query(value = "SELECT xabit.account.employees,xabit.account.account_number FROM  xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.employees >= :value", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactEmployeesGreaterThanEqual(String value);

	@Query(value = "SELECT xabit.account.employees,xabit.account.account_number FROM  xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.employees <= :value", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactEmployeesLessThanEqual(String value);

	// numberOfLocation(Integer) number_of_location
	@Query(value = "SELECT xabit.account.number_of_location,xabit.account.account_number FROM  xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.number_of_location = :value", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactnumberOfLocation(String value);

	@Query(value = "SELECT xabit.account.number_of_location,xabit.account.account_number FROM  xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.number_of_location!= :value", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactnumberOfLocationNot(String value);

	@Query(value = "SELECT xabit.account.number_of_location,xabit.account.account_number FROM  xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.number_of_location > :value", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactnumberOfLocationGreaterThan(String value);

	@Query(value = "SELECT xabit.account.number_of_location,xabit.account.account_number FROM  xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.number_of_location < :value", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactnumberOfLocationLessThan(String value);

	@Query(value = "SELECT xabit.account.number_of_location,xabit.account.account_number FROM  xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.number_of_location >= :value", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactnumberOfLocationGreaterThanEqual(String value);

	@Query(value = "SELECT xabit.account.number_of_location,xabit.account.account_number FROM  xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.number_of_location <= :value", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactnumberOfLocationLessThanEqual(String value);

	// phone(long) phone
	@Query(value = "SELECT xabit.account.phone,xabit.account.account_number FROM  xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.phone = :value", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactPhone(String value);

	@Query(value = "SELECT xabit.account.phone,xabit.account.account_number FROM  xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.phone!= :value", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactPhoneNot(String value);

	@Query(value = "SELECT xabit.account.phone,xabit.account.account_number FROM  xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.phone > :value", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactPhoneGreaterThan(String value);

	@Query(value = "SELECT xabit.account.phone,xabit.account.account_number FROM  xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.phone < :value", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactPhoneLessThan(String value);

	@Query(value = "SELECT xabit.account.phone,xabit.account.account_number FROM  xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.phone >= :value", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactPhoneGreaterThanEqual(String value);

	@Query(value = "SELECT xabit.account.phone,xabit.account.account_number FROM  xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.phone <= :value", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactPhoneLessThanEqual(String value);

	// sicCode(long) sic_code
	@Query(value = "SELECT xabit.account.sic_code,xabit.account.account_number FROM  xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.sic_code = :value", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactsicCode(String value);

	@Query(value = "SELECT xabit.account.sic_code,xabit.account.account_number FROM  xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.sic_code!= :value", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactsicCodeNot(String value);

	@Query(value = "SELECT xabit.account.sic_code,xabit.account.account_number FROM  xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.sic_code > :value", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactsicCodeGreaterThan(String value);

	@Query(value = "SELECT xabit.account.sic_code,xabit.account.account_number FROM  xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.sic_code < :value", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactsicCodeLessThan(String value);

	@Query(value = "SELECT xabit.account.sic_code,xabit.account.account_number FROM  xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.sic_code >= :value", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactsicCodeGreaterThanEqual(String value);

	@Query(value = "SELECT xabit.account.sic_code,xabit.account.account_number FROM  xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.sic_code <= :value", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactsicCodeLessThanEqual(String value);

	// slaSerialNumber(Integer) sla_serial_number
	@Query(value = "SELECT xabit.account.sla_serial_number,xabit.account.account_number FROM  xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.sla_serial_number = :value", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactslaSerialNumber(String value);

	@Query(value = "SELECT xabit.account.sla_serial_number,xabit.account.account_number FROM  xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.sla_serial_number!= :value", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactslaSerialNumberNot(String value);

	@Query(value = "SELECT xabit.account.sla_serial_number,xabit.account.account_number FROM  xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.sla_serial_number > :value", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactslaSerialNumberGreaterThan(String value);

	@Query(value = "SELECT xabit.account.sla_serial_number,xabit.account.account_number FROM  xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.sla_serial_number < :value", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactslaSerialNumberLessThan(String value);

	@Query(value = "SELECT xabit.account.sla_serial_number,xabit.account.account_number FROM  xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.sla_serial_number >= :value", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactslaSerialNumberGreaterThanEqual(String value);

	@Query(value = "SELECT xabit.account.sla_serial_number,xabit.account.account_number FROM  xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.sla_serial_number <= :value", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactslaSerialNumberLessThanEqual(String value);

	// slaExpirationDate(Date) sla_expiration_date
	@Query(value = "SELECT xabit.account.sla_expiration_date,xabit.account.account_number FROM  xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.sla_expiration_date = :value", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactslaExpirationDate(String value);

	@Query(value = "SELECT xabit.account.sla_expiration_date,xabit.account.account_number FROM  xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.sla_expiration_date!= :value", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactslaExpirationDateNot(String value);

	@Query(value = "SELECT xabit.account.sla_expiration_date,xabit.account.account_number FROM  xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.sla_expiration_date > :value", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactslaExpirationDateGreaterThan(String value);

	@Query(value = "SELECT xabit.account.sla_expiration_date,xabit.account.account_number FROM  xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.sla_expiration_date < :value", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactslaExpirationDateLessThan(String value);

	@Query(value = "SELECT xabit.account.sla_expiration_date,xabit.account.account_number FROM  xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.sla_expiration_date >= :value", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactslaExpirationDateGreaterThanEqual(String value);

	@Query(value = "SELECT xabit.account.sla_expiration_date,xabit.account.account_number FROM  xabit.contact inner join xabit.account ON xabit.contact.accountid=xabit.account.accountid where xabit.account.sla_expiration_date <= :value", nativeQuery = true)
	List<Map<String, Object>> findByAccountContactslaExpirationDateLessThanEqual(String value);

	// In contact fields
	// asstPhone //long
	List<Contact> findByAsstPhone(Long asstPhone);

	List<Contact> findByAsstPhoneNot(Long asstPhone);

	// homePhone //long
	List<Contact> findByHomePhone(Long homePhone);

	List<Contact> findByHomePhoneNot(Long homePhone);

	// mailingZipCode //long
	List<Contact> findByMailingZipCode(Long mailingZipCode);

	List<Contact> findByMailingZipCodeNot(Long mailingZipCode);

	// mobile //long
	List<Contact> findByMobile(Long mobile);

	List<Contact> findByMobileNot(Long mobile);

	// otherPhone //long
	List<Contact> findByOtherPhone(Long otherPhone);

	List<Contact> findByOtherPhoneNot(Long otherPhone);

	// otherZipCode //long
	List<Contact> findByOtherZipCode(Long otherZipCode);

	List<Contact> findByOtherZipCodeNot(Long otherZipCode);

	// phone //long
	List<Contact> findByPhone(Long phone);

	List<Contact> findByPhoneNot(Long phone);

	// assistant
	List<Contact> findByAssistantContainingIgnoreCase(String assistant);

	List<Contact> findByAssistantNotContainingIgnoreCase(String assistant);

	List<Contact> findByAssistantStartsWithIgnoreCase(String assistant);

	// department
	List<Contact> findByDepartmentContainingIgnoreCase(String department);

	List<Contact> findByDepartmentNotContainingIgnoreCase(String department);

	List<Contact> findByDepartmentStartsWithIgnoreCase(String department);

	// firstName //string
	List<Contact> findByFirstNameContainingIgnoreCase(String firstName);

	List<Contact> findByFirstNameNotContainingIgnoreCase(String firstName);

	List<Contact> findByFirstNameStartsWithIgnoreCase(String firstName);

	// lastName //string
	List<Contact> findByLastNameContainingIgnoreCase(String lastName);

	List<Contact> findByLastNameNotContainingIgnoreCase(String lastName);

	List<Contact> findByLastNameStartsWithIgnoreCase(String lastName);

	// languages //string
	List<Contact> findByLanguagesContainingIgnoreCase(String languages);

	List<Contact> findByLanguagesNotContainingIgnoreCase(String languages);

	List<Contact> findByLanguagesStartsWithIgnoreCase(String languages);

	// fax //string
	List<Contact> findByFaxContainingIgnoreCase(String fax);

	List<Contact> findByFaxNotContainingIgnoreCase(String fax);

	List<Contact> findByFaxStartsWithIgnoreCase(String fax);

	// email //string
	List<Contact> findByEmailContainingIgnoreCase(String email);

	List<Contact> findByEmailNotContainingIgnoreCase(String email);

	List<Contact> findByEmailStartsWithIgnoreCase(String email);

	// leadSource //string
	List<Contact> findByLeadSourceContainingIgnoreCase(String leadSource);

	List<Contact> findByLeadSourceNotContainingIgnoreCase(String leadSource);

	List<Contact> findByLeadSourceStartsWithIgnoreCase(String leadSource);

	// level //string
	List<Contact> findByLevelContainingIgnoreCase(String level);

	List<Contact> findByLevelNotContainingIgnoreCase(String level);

	List<Contact> findByLevelStartsWithIgnoreCase(String level);

	// mailingCity //string
	List<Contact> findByMailingCityContainingIgnoreCase(String mailingCity);

	List<Contact> findByMailingCityNotContainingIgnoreCase(String mailingCity);

	List<Contact> findByMailingCityStartsWithIgnoreCase(String mailingCity);

	// mailingCountry //string
	List<Contact> findByMailingCountryContainingIgnoreCase(String mailingCountry);

	List<Contact> findByMailingCountryNotContainingIgnoreCase(String mailingCountry);

	List<Contact> findByMailingCountryStartsWithIgnoreCase(String mailingCountry);

	// mailingState //string
	List<Contact> findByMailingStateContainingIgnoreCase(String mailingState);

	List<Contact> findByMailingStateNotContainingIgnoreCase(String mailingState);

	List<Contact> findByMailingStateStartsWithIgnoreCase(String mailingState);

	// mailingStreet //string
	List<Contact> findByMailingStreetContainingIgnoreCase(String mailingStreet);

	List<Contact> findByMailingStreetNotContainingIgnoreCase(String mailingStreet);

	List<Contact> findByMailingStreetStartsWithIgnoreCase(String mailingStreet);

	// otherCity //string
	List<Contact> findByOtherCityContainingIgnoreCase(String otherCity);

	List<Contact> findByOtherCityNotContainingIgnoreCase(String otherCity);

	List<Contact> findByOtherCityStartsWithIgnoreCase(String otherCity);

	// otherCountry //string
	List<Contact> findByOtherCountryContainingIgnoreCase(String otherCountry);

	List<Contact> findByOtherCountryNotContainingIgnoreCase(String otherCountry);

	List<Contact> findByOtherCountryStartsWithIgnoreCase(String otherCountry);

	// otherState //string
	List<Contact> findByOtherStateContainingIgnoreCase(String otherState);

	List<Contact> findByOtherStateNotContainingIgnoreCase(String otherState);

	List<Contact> findByOtherStateStartsWithIgnoreCase(String otherState);

	// otherStreet //string
	List<Contact> findByOtherStreetContainingIgnoreCase(String otherStreet);

	List<Contact> findByOtherStreetNotContainingIgnoreCase(String otherStreet);

	List<Contact> findByOtherStreetStartsWithIgnoreCase(String otherStreet);

	// reportsTo //string
	List<Contact> findByReportsToContainingIgnoreCase(String reportsTo);

	List<Contact> findByReportsToNotContainingIgnoreCase(String reportsTo);

	List<Contact> findByReportsToStartsWithIgnoreCase(String reportsTo);

	// salutation //string
	List<Contact> findBySalutationContainingIgnoreCase(String salutation);

	List<Contact> findBySalutationNotContainingIgnoreCase(String salutation);

	List<Contact> findBySalutationStartsWithIgnoreCase(String salutation);

	// title //string
	List<Contact> findByTitleContainingIgnoreCase(String title);

	List<Contact> findByTitleNotContainingIgnoreCase(String title);

	List<Contact> findByTitleStartsWithIgnoreCase(String title);

	Optional<Contact> findByContactid(Integer id);
	
	Optional<Contact> findByOwnerAlias(String ownerAlias);
	
	Optional<Contact> findByCreatedByAlias(String createdByAlias);
	
    Optional<Contact> findByLastModifiedByAlias(String lastModifiedByAlias);

    @Query(value = "select * from xabit.contact", nativeQuery = true)
	List<Map<String, Object>> findByDynamicAllContact();

    @Query(value = "select * from xabit.contact as u where u.contactid=:id", nativeQuery = true)
	List<Map<String, Object>> findByDynamicContactById(Integer id);

    @Query(value = "select * from xabit.contact", nativeQuery = true)
	List<Map<String, Object>> findAllContacts();

    @Query(value = "describe xabit.contact" ,nativeQuery = true)
	List<Map<String,Object>> findByField();
}
