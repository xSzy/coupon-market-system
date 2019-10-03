package com.ptit.cms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ptit.cms.model.entity.Coupon;
import com.ptit.cms.model.entity.Notification;
import com.ptit.cms.model.entity.User;
import com.ptit.cms.model.other.ErrorMessage;
import com.ptit.cms.model.other.ResponseModel;
import com.ptit.cms.service.NotificationService;
import com.ptit.cms.util.Constant;

@RestController
@RequestMapping("#{'${rest.base-path}'}")
public class NotificationController
{
	@Autowired
	private NotificationService notificationService;
	
	@RequestMapping(value = "/notification/getByUser", method = RequestMethod.GET)
	public ResponseEntity<ResponseModel> getNotificationByUser(@RequestBody User user)
	{
		ResponseModel response = new ResponseModel();
		ErrorMessage errorMessage = new ErrorMessage();
		
		List<Notification> listNotification = null;
		try
		{
			listNotification = notificationService.getNotificationByUser(user);
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
		response.setData(listNotification);
		return new ResponseEntity<ResponseModel>(response, listNotification == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
	}
	
	@RequestMapping(value = "/notification/remove", method = RequestMethod.POST)
	public ResponseEntity<ResponseModel> removeUserNotification(@RequestBody User user)
	{
		ResponseModel response = new ResponseModel();
		ErrorMessage errorMessage = new ErrorMessage();
		
		boolean result = false;
		try
		{
			result = notificationService.removeUserNotification(user);
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
		response.setData(result);
		return new ResponseEntity<ResponseModel>(response, result == false ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
	}
}
