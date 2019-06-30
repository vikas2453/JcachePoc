package com.mindtree.poc.JcachePoc.service;

import javax.cache.annotation.CacheKey;
import javax.cache.annotation.CacheResult;

import org.springframework.stereotype.Component;

import com.mindtree.poc.JcachePoc.model.Order;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OrderService {

	@CacheResult
	public Order getOrderDetails(@CacheKey int orderId) {
		log.debug("Creating a new order");
		Order order = new Order( orderId, "this is a sample order");
		return order;
		
	}
}
