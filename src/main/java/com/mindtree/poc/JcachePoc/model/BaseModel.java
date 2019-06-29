package com.mindtree.poc.JcachePoc.model;

import java.time.Instant;

import lombok.Data;

@Data
public class BaseModel {
	
	private Instant createdTime;
	
	private Instant updatedTime;
	
	private String createdBy;
	
	private String updatedBy;

}
