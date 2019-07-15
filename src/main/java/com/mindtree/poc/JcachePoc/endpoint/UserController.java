package com.mindtree.poc.JcachePoc.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mindtree.poc.JcachePoc.model.User;
import com.mindtree.poc.JcachePoc.service.UserDetailsServiceImpl;

@RestController
public class UserController {
	
	@Autowired
	UserDetailsServiceImpl userDetailService;
	
	
	@PostMapping("/user")
	public User addUser(User user) {
		return userDetailService.addUser(user);
		
	}

}
