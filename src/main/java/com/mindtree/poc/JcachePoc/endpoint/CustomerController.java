package com.mindtree.poc.JcachePoc.endpoint;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mindtree.poc.JcachePoc.model.Customer;
import com.mindtree.poc.JcachePoc.service.CustomerService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class CustomerController {
	
	private CustomerService customerService;
	
	//@RequestMapping("/customerDetails", method=RequstMethod.GET)
	@GET
	@Path("customerDetails/{customerId}")
	public Customer getCustomerDetails( @PathParam("customerId")int customerId ) {
		Customer customer= customerService.getCustomerDetails(customerId);
		log.info("Fetching customer details: "+customer );
		return customer;
		
	}
	
	@GetMapping("/customerDetails")
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
		
	}
}
