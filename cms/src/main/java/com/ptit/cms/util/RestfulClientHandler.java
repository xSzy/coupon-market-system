package com.ptit.cms.util;

import java.io.File;

import java.util.Map;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class RestfulClientHandler
{
	public static Map postForObject(String url, Object body, MediaType mediaType) throws Exception
	{
		HttpHeaders headers = new HttpHeaders();
//		headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.setContentType(mediaType);
		
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<?> request = new HttpEntity<>(body, headers);
		ResponseEntity<Map> response = null;
		try {
			response = restTemplate.postForEntity(url, request, Map.class);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		if(response.getStatusCode() != HttpStatus.OK)
			throw new Exception("Status code " + response.getStatusCodeValue());
		return response.getBody();
	}
	
	public static Map test()
	{
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		
		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("file", new FileSystemResource("D:\\cmsdata\\testset\\bottom-wear\\1.09_408494.jpg"));
		HttpEntity<?> request = new HttpEntity<>(body, headers);
		ResponseEntity<Map> response = restTemplate.postForEntity("http://localhost:5000/visualSearch", request, Map.class);
		return response.getBody();
	}
}
