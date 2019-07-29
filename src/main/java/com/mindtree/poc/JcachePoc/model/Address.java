package com.mindtree.poc.JcachePoc.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Entity
@Data
@XmlRootElement(name = "Address")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Address  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 19L;

	@Id
	@GeneratedValue
	private long addressId;
	
	private  String firstLine;
	private String secondLine;
	private String City;
	private String zipCode;

}
