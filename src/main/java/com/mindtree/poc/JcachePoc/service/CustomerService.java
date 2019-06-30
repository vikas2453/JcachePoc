package com.mindtree.poc.JcachePoc.service;

import javax.cache.annotation.CacheKey;
import javax.cache.annotation.CacheResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mindtree.poc.JcachePoc.model.Customer;
import com.mindtree.poc.JcachePoc.repo.CustomerRepo;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CustomerService {
	
	@Autowired
	private CustomerRepo customerRepo;

	@CacheResult
	public Customer getCustomerDetails(@CacheKey int customerId) {
		Customer cust =customerRepo.getOne((long) customerId);
		log.info("Fetching customer details: "+cust );
		return cust;
	}
	
	public Customer saveCustomerDetails(Customer customer) {
			
			return customerRepo.save(customer);
		
	}

	public Customer getFilteredCustomer(String state, String city) {
		// TODO Auto-generated method stub
		return null;
	}
	
	//returns a filtered list of customer
	/*public List<Customer> getAllCustomer(Customer customer) {
		
		return customerRepo.save(customer);
	}*/

}
