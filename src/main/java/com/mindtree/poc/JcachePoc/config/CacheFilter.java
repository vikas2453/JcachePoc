package com.mindtree.poc.JcachePoc.config;

import java.io.IOException;
import java.util.Map;

import javax.cache.Cache;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.web.util.ContentCachingResponseWrapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
// @Component
@Order(1)
public class CacheFilter implements Filter {
	@Autowired
	CacheAspectConfig cacheBean;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;

		log.info("Logging Request  {} : {}", req.getMethod(), req.getRequestURI());
		// we can add requestURI in the condition and probably maintain the list of URI
		// as we don't want to return result from cache for every URI
		// we need to find the someway to get domain from requestURI so that we can go
		// to that partiucalr cache
		// we also need to manage a list of params for which we need to check it from
		// cache
		// also there might be scenarios that result would be sent only when some of
		// manadatory parameters are in the URI like customerId and adressId is required
		// to get the address details so we can's return result on the basis of
		// customerId only
		if (req.getMethod().equalsIgnoreCase("get")) {
			String resultJson;

			// we would be iterating through the list of params and check if the params are
			// are present in request Map
			String key = req.getParameter("customerId");

			Cache<String, String> cache = CacheAspectConfig.cacheDomainMap
					.get("com.mindtree.poc.JcachePoc.model.Customer");

			if (key != null) {
				resultJson = cache.get(key);

				if (resultJson != null) {
					response.getOutputStream().write(resultJson.getBytes());
					return;
				} else {
					chain.doFilter(request, response);
					HttpServletResponse res = (HttpServletResponse) response;
					ContentCachingResponseWrapper contentCachingResponseWrapper = wrapResponse(res);
					contentCachingResponseWrapper.copyBodyToResponse();
					byte[] content = contentCachingResponseWrapper.getContentAsByteArray();

					resultJson = new String(content, contentCachingResponseWrapper.getCharacterEncoding());

					cache.put(key, resultJson);
					log.info("Logging Response {}:{}", res.getContentType(), resultJson);
					return;
				}

			}
		} else
			chain.doFilter(request, response);

	}

	private ContentCachingResponseWrapper wrapResponse(HttpServletResponse response) {
		if (response instanceof ContentCachingResponseWrapper) {
			return (ContentCachingResponseWrapper) response;
		} else {
			ContentCachingResponseWrapper contentCachingResponseWrapper = new ContentCachingResponseWrapper(response);
			
			return contentCachingResponseWrapper;
		}
	}

}
