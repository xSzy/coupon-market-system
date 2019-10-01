package com.ptit.cms.service.impl;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ptit.cms.dao.CategoryDao;
import com.ptit.cms.model.entity.Category;
import com.ptit.cms.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService
{
	@Autowired
	private CategoryDao categoryDao;
	
	public Category createCategory(Category category) throws Exception
	{
		//check if category exist
		if(category.getId() != 0 && categoryDao.findById(category.getId()).isPresent())
			throw new Exception("category exists");
		category = categoryDao.save(category);
		return category;
	}

	@Override
	public List<Category> getAllCategory() throws Exception
	{
		try
		{
			return categoryDao.getAllCategory();
		}
		catch(Exception e)
		{
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public Category updateCategory(Category category) throws Exception
	{
		//check if category exist
		if(category.getId() == 0 || !categoryDao.findById(category.getId()).isPresent())
			throw new Exception("category not exists");
		category = categoryDao.save(category);
		return category;
	}

	@Override
	public boolean deleteCategory(Category category) throws Exception
	{
		try
		{
			categoryDao.delete(category);
			return true;
		}
		catch(Exception e)
		{
			throw new Exception(e.getMessage());
		}
	}
	
}
