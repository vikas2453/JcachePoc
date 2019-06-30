package com.mindtree.poc.JcachePoc.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mindtree.poc.JcachePoc.model.Order;
import com.mindtree.poc.JcachePoc.service.OrderService;

@RestController
public class OrderController {
	@Autowired
	private OrderService orderService;
	
	@RequestMapping("/OrderDetails")
	public Order getOrderDetails(@RequestParam int orderId) {
		return orderService.getOrderDetails(orderId);
	}
}
