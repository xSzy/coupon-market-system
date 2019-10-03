package com.ptit.cms.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ptit.cms.model.entity.Coupon;
import com.ptit.cms.model.entity.Favourite;
import com.ptit.cms.model.entity.User;
import com.ptit.cms.model.other.ErrorMessage;
import com.ptit.cms.model.other.ResponseModel;
import com.ptit.cms.service.FavouriteService;
import com.ptit.cms.util.Constant;

@RestController
@RequestMapping("#{'${rest.base-path}'}")
public class FavouriteController
{
	@Autowired
	private FavouriteService favouriteService;
	
	@RequestMapping(value = "/favourite/add", method = RequestMethod.POST)
	public ResponseEntity<ResponseModel> createCoupon(@Valid @RequestBody Favourite favourite)
	{
		ResponseModel response = new ResponseModel();
		ErrorMessage errorMessage = new ErrorMessage();
		
		Favourite newFavourite = null;
		try
		{
			newFavourite = favouriteService.addFavourite(favourite);
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
		response.setData(newFavourite);
		return new ResponseEntity<ResponseModel>(response, newFavourite == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
	}
	
	@RequestMapping(value = "/favourite/getFavouriteByUser", method = RequestMethod.GET)
	public ResponseEntity<ResponseModel> getFavouriteByUser(@RequestBody User user)
	{
		ResponseModel response = new ResponseModel();
		ErrorMessage errorMessage = new ErrorMessage();
		
		List<Favourite> listFavourite = null;
		try
		{
			listFavourite = favouriteService.getFavouriteByUser(user);
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
		response.setData(listFavourite);
		return new ResponseEntity<ResponseModel>(response, listFavourite == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
	}
	
	@RequestMapping(value = "/favourite/delete", method = RequestMethod.POST)
	public ResponseEntity<ResponseModel> deleteFavourite(@RequestBody Favourite favourite)
	{
		ResponseModel response = new ResponseModel();
		ErrorMessage errorMessage = new ErrorMessage();
		
		boolean result = false;
		try
		{
			result = favouriteService.deleteFavourite(favourite);
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
