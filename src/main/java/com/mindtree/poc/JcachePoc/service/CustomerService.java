package com.mindtree.poc.JcachePoc.service;

import org.springframework.stereotype.Component;

import com.mindtree.poc.JcachePoc.model.Customer;
import com.mindtree.poc.JcachePoc.repo.CustomerRepo;

@Component
public class CustomerService {
	
	private CustomerRepo customerRepo;

	public Customer getCustomerDetails(int customerId) {
		
		return customerRepo.getOne((long) customerId);
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
