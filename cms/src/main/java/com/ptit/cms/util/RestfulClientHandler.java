package com.ptit.cms.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class RestfulClientHandler
{
	public static <T> T postForObject(String url, Object body, Class<T> clazz) throws Exception
	{
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<?> request = new HttpEntity<>(body, headers);
		ResponseEntity<T> response = restTemplate.postForEntity(url, request, clazz);
		if(response.getStatusCode() != HttpStatus.OK)
			throw new Exception("Status code " + response.getStatusCodeValue());
		return response.getBody();
	}
}
