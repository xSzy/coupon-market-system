package com.ptit.cms.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ptit.cms.model.other.ErrorMessage;
import com.ptit.cms.model.other.ResponseModel;

public class ValidationHandler
{
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ResponseModel> handleValidationExceptions(MethodArgumentNotValidException ex)
	{
		ResponseModel response = new ResponseModel();
		response.setData(null);
		response.setStatus(Constant.STATUS_ERROR);
		ErrorMessage message = new ErrorMessage();
		message.setErrorCode(Constant.VALIDATION_FAILED);
		String msg = "";
		List<ObjectError> listError = ex.getBindingResult().getAllErrors();
		for(ObjectError error : listError)
		{
			msg += error.getDefaultMessage() + ". ";
		}
		message.setMessage(msg);
		response.setError(message);
		return new ResponseEntity<ResponseModel>(response, HttpStatus.BAD_REQUEST);
	}
}
