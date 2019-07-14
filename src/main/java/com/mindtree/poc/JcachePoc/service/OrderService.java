package com.mindtree.poc.JcachePoc.service;

import org.springframework.stereotype.Component;

import com.mindtree.poc.JcachePoc.config.Cache;
import com.mindtree.poc.JcachePoc.model.Order;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OrderService {

	@Cache
	public Order getOrderDetails( Integer orderId) {
		log.debug("Creating a new order");
		Order order = new Order( orderId, "this is a sample order");
		return order;
		
	}
}
