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

import com.ptit.cms.model.entity.Category;
import com.ptit.cms.model.other.ErrorMessage;
import com.ptit.cms.model.other.ResponseModel;
import com.ptit.cms.service.CategoryService;
import com.ptit.cms.util.Constant;

@RestController
@RequestMapping("#{'${rest.base-path}'}")
public class CategoryController
{
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping(value = "/category/create", method = RequestMethod.POST)
	public ResponseEntity<ResponseModel> createCategory(@Valid @RequestBody Category category)
	{
		ResponseModel response = new ResponseModel();
		ErrorMessage errorMessage = new ErrorMessage();
		
		Category newCategory = null;
		try
		{
			newCategory = categoryService.createCategory(category);
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
		response.setData(newCategory);
		return new ResponseEntity<ResponseModel>(response, newCategory == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
	}
	
	@RequestMapping(value = "/category/getAll", method = RequestMethod.GET)
	public ResponseEntity<ResponseModel> getAllCategory()
	{
		ResponseModel response = new ResponseModel();
		ErrorMessage errorMessage = new ErrorMessage();
		
		List<Category> listCategory = null;
		try
		{
			listCategory = categoryService.getAllCategory();
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
		response.setData(listCategory);
		return new ResponseEntity<ResponseModel>(response, listCategory == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
	}
	
	@RequestMapping(value = "/category/update", method = RequestMethod.POST)
	public ResponseEntity<ResponseModel> updateCategory(@Valid @RequestBody Category category)
	{
		ResponseModel response = new ResponseModel();
		ErrorMessage errorMessage = new ErrorMessage();
		
		Category newCategory = null;
		try
		{
			newCategory = categoryService.updateCategory(category);
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
		response.setData(newCategory);
		return new ResponseEntity<ResponseModel>(response, newCategory == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
	}
	
	@RequestMapping(value = "/category/delete", method = RequestMethod.POST)
	public ResponseEntity<ResponseModel> deleteCategory(@Valid @RequestBody Category category)
	{
		ResponseModel response = new ResponseModel();
		ErrorMessage errorMessage = new ErrorMessage();
		
		try
		{
			categoryService.deleteCategory(category);
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
		response.setData(null);
		return new ResponseEntity<ResponseModel>(response, response.getStatus() == Constant.STATUS_ERROR ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
	}
}
