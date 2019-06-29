package com.mindtree.poc.JcachePoc.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Entity
public class Customer {
	
	@Id
	@GeneratedValue
	private long customerId;
	
	private String firstName;
	
	private String LastName;
	
	@OneToOne
	@JoinColumn(name="addressId")
	private Address address;

}
