package com.mindtree.poc.JcachePoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching 
public class JcachePocApplication {

	public static void main(String[] args) {
		SpringApplication.run(JcachePocApplication.class, args);
	}

}
