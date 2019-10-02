package com.ptit.cms.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ptit.cms.dao.CategoryDao;
import com.ptit.cms.dao.CouponDao;
import com.ptit.cms.model.entity.Category;
import com.ptit.cms.model.entity.Coupon;
import com.ptit.cms.service.CouponService;

@Service
public class CouponServiceImpl implements CouponService
{
	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private CouponDao couponDao;
	
	@Override
	public Coupon createCoupon(Coupon coupon) throws Exception
	{
		//get category
		try {
			coupon.setCategory(categoryDao.getOne(coupon.getCategory().getId()));
			coupon.setClickCount(0);
			coupon = couponDao.save(coupon); 
		}
		catch(Exception e)
		{
			throw new Exception(e.getMessage());
		}
		return coupon;
	}

	@Override
	public boolean updateCouponClickcount(int couponId) throws Exception
	{
		try 
		{
			Coupon coupon = couponDao.getOne(couponId);
			coupon.setClickCount(coupon.getClickCount() + 1);
			coupon = couponDao.save(coupon);
		}
		catch(Exception e)
		{
			throw new Exception(e.getMessage());
		}
		return true;
	}

	@Override
	public List<Coupon> getAllCoupon()
	{
		return couponDao.findAll();
	}

	@Override
	public List<Coupon> getCouponByCategory(int categoryId) throws Exception
	{
		Category category = categoryDao.getOne(categoryId);
		if(category == null)
			throw new Exception("category not found");
		List<Category> listCategory = getAllSubCategory(category);
		System.out.println(listCategory.size());
		List<Coupon> listCoupon = new ArrayList<>();
		for(Category c : listCategory)
		{
			listCoupon.addAll(couponDao.getCouponByCategory(c));
		}
		return listCoupon;
	}
	
	@Override
	public Coupon getCouponById(int couponId) throws Exception
	{
		try
		{
			return couponDao.findById(couponId).get();
		}
		catch(NoSuchElementException e)
		{
			throw new Exception("coupon not found");
		}
	}

	@Override
	public List<Coupon> getCouponByName(String query) throws Exception
	{
		query = "%" + query + "%";
		query = query.toUpperCase();
		try
		{
			return couponDao.getCouponByName(query);
		}
		catch(Exception e)
		{
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public List<Coupon> getCouponByClickcount(int number) throws Exception
	{
		try
		{
			return couponDao.getCouponByClickcount(new PageRequest(0, number));
		}
		catch(Exception e)
		{
			throw new Exception(e.getMessage());
		}
	}

	private List<Category> getAllSubCategory(Category category)
	{
		List<Category> listCategory = new ArrayList<>();
		if(category.getSubCategory() == null || category.getSubCategory().size() < 1)	//leaf
		{
			listCategory.add(category);
			return listCategory;
		}
		for(Category c : category.getSubCategory())
		{
			listCategory.addAll(getAllSubCategory(c));
		}
		return listCategory;
	}
	
}
