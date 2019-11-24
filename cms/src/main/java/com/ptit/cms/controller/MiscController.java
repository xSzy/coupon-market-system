package com.ptit.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ptit.cms.model.other.ErrorMessage;
import com.ptit.cms.model.other.ResponseModel;
import com.ptit.cms.service.MiscService;
import com.ptit.cms.util.Constant;

@RestController
@RequestMapping("#{'${rest.base-path}'}")
public class MiscController
{
	@Autowired
	private MiscService miscService;
	
	@RequestMapping(value = "/image/upload", method = RequestMethod.POST)
	public ResponseEntity<ResponseModel> uploadImage(@RequestPart MultipartFile file, @RequestPart String description)
	{
		ResponseModel response = new ResponseModel();
		ErrorMessage errorMessage = new ErrorMessage();
		
		String path = null;
		try
		{
			path = miscService.saveFile(file, description);
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
		response.setData(path);
		return new ResponseEntity<ResponseModel>(response, path == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
	}

	@RequestMapping(value = "/avatar/upload", method = RequestMethod.POST)
	public ResponseEntity<ResponseModel> uploadAvatar(@RequestPart MultipartFile file)
	{
		ResponseModel response = new ResponseModel();
		ErrorMessage errorMessage = new ErrorMessage();

		String path = null;
		try
		{
			path = miscService.saveAvatar(file);
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
		response.setData(path);
		return new ResponseEntity<ResponseModel>(response, path == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
	}
}
