package com.mindtree.poc.JcachePoc.config;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import javax.cache.Cache;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Order(1)
public class ResponseCachingFilter extends OncePerRequestFilter {
	private static final List<MediaType> VISIBLE_TYPES = Arrays.asList(MediaType.valueOf("text/*"),
			MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
			MediaType.valueOf("application/*+json"), MediaType.valueOf("application/*+xml"),
			MediaType.MULTIPART_FORM_DATA);
	
	@Autowired 
	Config config;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String resultJson;

		// we would be iterating through the list of params mentioned in props and check
		// if the params from
		// the list are present in map
		// Also probably it would be combination of params
		String key = request.getParameter("customerId");

		// we can add requestURI in the condition and probably maintain the list of URI
		// in props
		// can we find domain from requestURI?
		Cache<String, String> cache = CacheAspectConfig.cacheDomainMap.get("com.mindtree.poc.JcachePoc.model.Customer");
		String catchingStrategy =config.getCachingStrategy();

		if (request.getMethod().equalsIgnoreCase("get") && key != null && cache != null && catchingStrategy.equalsIgnoreCase("Filter")) {

			resultJson = cache.get(key);

			if (resultJson != null) {
				response.getOutputStream().write(resultJson.getBytes());
				log.info("Getting response {} from cache: for key: {}", resultJson, key);
				return;

			} else {
				doFilterWrapped(wrapRequest(request), wrapResponse(response), filterChain, cache, key);
				return;
			}

		} else
			filterChain.doFilter(request, response);

	}

	protected void doFilterWrapped(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response,
			FilterChain filterChain, Cache<String, String> cache, String key) throws ServletException, IOException {
		try {
			beforeRequest(request, response);
			filterChain.doFilter(request, response);
		} finally {
			afterRequest(response, cache, key);
			response.copyBodyToResponse();
		}
	}

	protected void beforeRequest(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response) {

	}

	protected void afterRequest(ContentCachingResponseWrapper response, Cache<String, String> cache, String key) {
		cacheResponse(response, cache, key);

	}

	private static void cacheResponse(ContentCachingResponseWrapper response, Cache<String, String> cache, String key) {
		val status = response.getStatus();

		if (status == 200) {
			val content = response.getContentAsByteArray();
			if (content.length > 0) {
				String result = getContent(content, response.getContentType(), response.getCharacterEncoding());
				cache.put(key, result);
				log.info("result: {}", result);
			}
		}

	}

	private static String getContent(byte[] content, String contentType, String contentEncoding) {
		String contentString = null;
		val mediaType = MediaType.valueOf(contentType);
		val visible = VISIBLE_TYPES.stream().anyMatch(visibleType -> visibleType.includes(mediaType));
		if (visible) {
			try {
				contentString = new String(content, contentEncoding);
			} catch (UnsupportedEncodingException e) {
				log.info("[{} bytes content]", content.length);
			}
		}
		return contentString;
	}

	private static ContentCachingRequestWrapper wrapRequest(HttpServletRequest request) {
		if (request instanceof ContentCachingRequestWrapper) {
			return (ContentCachingRequestWrapper) request;
		} else {
			return new ContentCachingRequestWrapper(request);
		}
	}

	private static ContentCachingResponseWrapper wrapResponse(HttpServletResponse response) {
		if (response instanceof ContentCachingResponseWrapper) {
			return (ContentCachingResponseWrapper) response;
		} else {
			return new ContentCachingResponseWrapper(response);
		}
	}
}
