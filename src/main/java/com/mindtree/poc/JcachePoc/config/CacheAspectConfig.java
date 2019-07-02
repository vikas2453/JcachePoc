package com.mindtree.poc.JcachePoc.config;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.spi.CachingProvider;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
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
	public static Cache<Object, Object> cache;

	@Bean(name="cacheBean")
	public CacheAspectConfig createCacheBean() {
		if (cacheAspectConfig == null) {
			// we can define seperate bean for cache, caching provider, config etc
			CachingProvider cachingProvider = Caching.getCachingProvider();
			CacheManager cacheManager = cachingProvider.getCacheManager();
			MutableConfiguration<Object, Object> config = new MutableConfiguration<>();
			cache = cacheManager.createCache("deltaPoc", config);
			log.debug("created cache"+cache);
			cacheAspectConfig = new CacheAspectConfig();
			
			GsonBuilder b = new GsonBuilder();
			b.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY);
			gson = b.create();
			
			return cacheAspectConfig;
		} else
			return cacheAspectConfig;
	}

	
	@Around("execution(* com.mindtree.poc.JcachePoc.service*.*.*(..)) && @annotation(Catche)")
	public Object cacheCheck(ProceedingJoinPoint joinPoint) {
		Object resultObj =null;
		String resultJson=null;
		try {
			
			Object[] args= joinPoint.getArgs();
			Class[] argTypes= new Class[args.length];
			for(int i=0; i<args.length; i++) {
				argTypes[i]= args.getClass();
			}
			//considering first arg as key
			// also would have to generalise it that it can be any of any type that should be easy to do as we can get the info of args
			if(cache!=null) {
			 //resultJson =(String) cache.get(argTypes[0].cast(args[0]));
				resultJson =(String) cache.get(args[0]);
			}
			//if result null then, let's take it from DB and then we would cache it
			if (resultJson == null) {				
				resultObj=joinPoint.proceed();
				resultJson=gson.toJson(resultObj);
				cache.put(args[0], resultJson);
				log.info("result was not received from cache, however entry made in cache now with key= "+args[0]+", value ="+resultJson);
			}
			else {
				log.debug("result received from cache"+resultJson);
			}
			// as of now, I am putting first argtype, in case of multiple args, we would have to iterate the args and pass their types. or may be cases 0, 1, 2, 3 or args.length
			
			resultObj=gson.fromJson(resultJson, joinPoint.getTarget().getClass().getMethod( joinPoint.getSignature().getName(), args[0].getClass()).getReturnType() );
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultObj;
	}


}
