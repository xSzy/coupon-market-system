package com.ptit.cms.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ptit.cms.model.entity.Account;
import com.ptit.cms.model.entity.User;
import com.ptit.cms.model.other.ErrorMessage;
import com.ptit.cms.model.other.ResponseModel;
import com.ptit.cms.service.UserService;
import com.ptit.cms.util.Constant;
import com.ptit.cms.util.ValidationHandler;

@RestController
@RequestMapping("#{'${rest.base-path}'}")
public class UserController extends ValidationHandler
{
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/user/register", method = RequestMethod.POST)
	public ResponseEntity<ResponseModel> register(@Valid @RequestBody Account account)
	{
		ResponseModel response = new ResponseModel();
		ErrorMessage errorMessage = new ErrorMessage();
		
		User user = null;
		try
		{
			user = userService.register(account);
			response.setStatus(Constant.STATUS_SUCCESS);
			response.setError(null);
		} catch (Exception e)
		{
			response.setStatus(Constant.STATUS_ERROR);
			errorMessage.setErrorCode(-1);
			errorMessage.setMessage(e.getMessage());
			response.setError(errorMessage);
			e.printStackTrace();
		}
		response.setData(user);
		return new ResponseEntity<ResponseModel>(response, user == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
	}
	
	@RequestMapping(value = "/user/login", method = RequestMethod.POST)
	public ResponseEntity<ResponseModel> login(@Valid @RequestBody Account account)
	{
		ResponseModel response = new ResponseModel();
		ErrorMessage errorMessage = new ErrorMessage();
		
		User user = null;
		try
		{
			user = userService.login(account);
			response.setStatus(Constant.STATUS_SUCCESS);
			response.setError(null);
		} catch (Exception e)
		{
			response.setStatus(Constant.STATUS_ERROR);
			errorMessage.setErrorCode(-1);
			errorMessage.setMessage(e.getMessage());
			response.setError(errorMessage);
			e.printStackTrace();
		}
		response.setData(user);
		return new ResponseEntity<ResponseModel>(response, user == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
	}
}
