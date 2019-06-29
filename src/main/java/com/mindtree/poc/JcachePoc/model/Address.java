package com.mindtree.poc.JcachePoc.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data

public class Address {
	
	@Id
	@GeneratedValue
	private long addressId;
	
	private  String firstLine;
	private String secondline;
	private String City;
	private String zipCode;

}
