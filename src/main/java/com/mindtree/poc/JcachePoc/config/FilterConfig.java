package com.mindtree.poc.JcachePoc.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
	
	// uncomment this and comment the @Component in the filter class definition to register only for a url pattern
  /*  @Bean
	public FilterRegistrationBean<CacheFilter> loggingFilter() {
        FilterRegistrationBean<CacheFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new CacheFilter());

        registrationBean.addUrlPatterns("/*");

        return registrationBean;

    }*/
    
  /*  @Bean(name="RegisterResponseCachingFilter")
   	public FilterRegistrationBean<ResponseCachingFilter> responseCachingFilter() {
           FilterRegistrationBean<ResponseCachingFilter> registrationBean = new FilterRegistrationBean<>();

           registrationBean.setFilter(new ResponseCachingFilter());

           registrationBean.addUrlPatterns("/*");

           return registrationBean;

       }*/

    
}
