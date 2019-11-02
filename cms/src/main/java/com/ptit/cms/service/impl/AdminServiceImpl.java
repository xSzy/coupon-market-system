package com.ptit.cms.service.impl;

import org.springframework.stereotype.Service;

import com.ptit.cms.service.AdminService;
import com.ptit.cms.util.RestfulClientHandler;

@Service
public class AdminServiceImpl implements AdminService
{
	
	@Override
	public String updateData() throws Exception
	{
		return RestfulClientHandler.postForObject("http://localhost:5000/updateData", null, String.class);
	}
}
