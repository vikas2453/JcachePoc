package com.mindtree.poc.JcachePoc.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Order {

	private long orderId;
	
	private String OrderDetails;
}
