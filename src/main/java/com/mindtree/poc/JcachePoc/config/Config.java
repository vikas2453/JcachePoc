package com.mindtree.poc.JcachePoc.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@Data
public class Config {

	@Value("${flyway.sqlLocation}")
	private String sqlLocation;
	
	@Value("#{'${cacheEnabledDomain}'.split(',')}") 
	private List<String> cacheEnabledDomain;
	
	@Value("${cachingStrategy}")
	private String cachingStrategy;
	
	
}
