package com.ptit.cms.service;

import java.util.List;

import javax.validation.Valid;

import com.ptit.cms.model.entity.Category;

public interface CategoryService
{
	public Category createCategory(Category category) throws Exception;

	public List<Category> getAllCategory() throws Exception;

	public Category updateCategory(Category category) throws Exception;

	public boolean deleteCategory(Category category) throws Exception;
}
