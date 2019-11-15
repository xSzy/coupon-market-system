package com.ptit.cms.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ptit.cms.model.other.ErrorMessage;
import com.ptit.cms.model.other.ResponseModel;
import com.ptit.cms.service.AdminService;
import com.ptit.cms.util.Constant;

@RestController
@RequestMapping("#{'${rest.base-path}'}")
public class AdminController
{
	@Autowired
	private AdminService adminService;
	
	@RequestMapping(value = "/updateData", method = RequestMethod.POST)
	public ResponseEntity<ResponseModel> updateData()
	{
		ResponseModel response = new ResponseModel();
		ErrorMessage errorMessage = new ErrorMessage();
		
		Map result = null;
		try 
		{
			result = adminService.updateData();
			response.setStatus(Constant.STATUS_SUCCESS);
			response.setError(null);
		}
		catch (Exception e)
		{
			response.setStatus(Constant.STATUS_ERROR);
			errorMessage.setErrorCode(-1);
			errorMessage.setMessage(e.getMessage());
			response.setError(errorMessage);
			e.printStackTrace();
		}
		response.setData(result);
		return new ResponseEntity<ResponseModel>(response, result == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
	}
}
