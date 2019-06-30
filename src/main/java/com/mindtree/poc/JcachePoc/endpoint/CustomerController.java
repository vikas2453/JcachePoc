package com.mindtree.poc.JcachePoc.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mindtree.poc.JcachePoc.model.Customer;
import com.mindtree.poc.JcachePoc.service.CustomerService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController

public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping("/customerDetails")
	//@GET
	//@Path("customerDetails/{customerId}")
	//public Customer getCustomerDetails( @PathParam("customerId")int customerId ) {
	public Customer getCustomerDetails( @RequestParam int customerId ) {
		Customer customer= customerService.getCustomerDetails(customerId);
		log.info("Fetching customer details: "+customer );
		return customer;
		
	}
	
	/*@GetMapping("/customerDetails")
	public Customer getfilterCustomerDetails( @RequestParam String state, @RequestParam String city) {
		Customer customer= customerService.getFilteredCustomer(state, city);
		log.info("Fetching customer details: "+customer );
		return customer;		
	}
	
	@PostMapping("/customerDetails")
	public Customer saveCustomerDetails( @RequestBody Customer customer) {
		customerService.saveCustomerDetails(customer);
		log.info("Saving customer details: "+customer );
		return customer;
		
	}*/
}
