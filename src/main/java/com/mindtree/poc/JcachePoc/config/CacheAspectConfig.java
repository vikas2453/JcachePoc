package com.mindtree.poc.JcachePoc.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.spi.CachingProvider;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Aspect
// @AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class CacheAspectConfig {

	private static CacheAspectConfig cacheAspectConfig;
	private static Gson gson;
	public static Map<String, Cache<String, String>> cacheDomainMap = new HashMap<>();
	
	@Autowired
	Config config;

	@Bean(name = "cacheBean")
	public CacheAspectConfig createCacheBean() {
		if (cacheAspectConfig == null) {
			// we can define seperate bean for cache, caching provider, config etc
			CachingProvider cachingProvider = Caching.getCachingProvider();
			CacheManager cacheManager = cachingProvider.getCacheManager();
			MutableConfiguration<String, String> configCache = new MutableConfiguration<>();

			List<String> cacheEnabledDomainList = config.getCacheEnabledDomain();
			cacheEnabledDomainList
					.forEach(domain -> cacheDomainMap.put(domain, cacheManager.createCache(domain, configCache)));

			log.debug("created caches map" + cacheDomainMap);
			cacheAspectConfig = new CacheAspectConfig();

			GsonBuilder b = new GsonBuilder();
			b.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY);
			gson = b.create();

			return cacheAspectConfig;
		} else
			return cacheAspectConfig;
	}

	//@Around("execution(* com.mindtree.poc.JcachePoc.service*.*.*(..)) && @annotation(Cache)")
	@Around("execution(* com.mindtree.poc.JcachePoc.service*.*.*(..))" )
	public Object cacheCheck(ProceedingJoinPoint joinPoint) {
		Object resultObj = null;
		String resultJson = null;
		try {

			Object[] args = joinPoint.getArgs();
			Class methodReturnTypeClass = joinPoint.getTarget().getClass()
					.getMethod(joinPoint.getSignature().getName(), args[0].getClass()).getReturnType();
			String methodReturnType = methodReturnTypeClass.getTypeName();
			log.debug(
					"methodReturnTypeClass is  " + methodReturnTypeClass + ", methodReturnType is " + methodReturnType);

			// creating an array of argTypes from args array
			Class[] argTypes = new Class[args.length];
			for (int i = 0; i < args.length; i++) {
				argTypes[i] = args.getClass();
			}

			Cache<String, String> cache = cacheDomainMap.get(methodReturnType);
			String catchingStrategy =config.getCachingStrategy();

			// if cache is not null then this domain was registered as cacheEnabledDomain 
			if ((cache != null) && catchingStrategy.equalsIgnoreCase("AOP")) {

				// considering first arg as key, shall we consider all the args?
				// what if args[0] is primitive type?
				resultJson = (String) cache.get(args[0].toString());

				// if result from cache is null then, let's call actual method and then we would
				// cache it
				if (resultJson == null) {
					resultObj = joinPoint.proceed();
					if (resultObj != null) {
						resultJson = gson.toJson(resultObj);
						// as of now, I am putting first arg, in case of multiple args, we would
						cache.put(args[0].toString(), resultJson);
						log.info("result not received from cache, entry made in cache with key= " + args[0]
								+ ", value =" + resultJson);
					}
				} else {
					log.debug("result received from cache" + resultJson);
				}
				
				

				resultObj = gson.fromJson(resultJson, methodReturnTypeClass);
			} else
				resultObj = joinPoint.proceed();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultObj;
	}

	private boolean domainCacheRequired(String methodReturnType) {
		// TODO Auto-generated method stub
		return true;
	}

}
