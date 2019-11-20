package com.ptit.cms.service.impl;

import java.util.Map;

import com.ptit.cms.util.Constant;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.ptit.cms.service.AdminService;
import com.ptit.cms.util.RestfulClientHandler;

@Service
public class AdminServiceImpl implements AdminService
{
	
	@Override
	public Map updateData() throws Exception
	{
		return RestfulClientHandler.postForObject(Constant.API_PYTHON_HOME + Constant.API_UPDATE_DATA, null, MediaType.APPLICATION_JSON);
	}

	@Override
	public Map trainModel() throws Exception {
		return RestfulClientHandler.postForObject(Constant.API_PYTHON_HOME + Constant.API_TRAIN_MODEL, null, MediaType.APPLICATION_JSON);
	}
}
