package com.ptit.cms.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ptit.cms.dao.CategoryDao;
import com.ptit.cms.dao.CouponDao;
import com.ptit.cms.dao.FavouriteDao;
import com.ptit.cms.dao.NotificationDao;
import com.ptit.cms.model.entity.Category;
import com.ptit.cms.model.entity.Coupon;
import com.ptit.cms.model.entity.Notification;
import com.ptit.cms.model.entity.User;
import com.ptit.cms.service.CouponService;
import com.ptit.cms.util.Constant;
import com.ptit.cms.util.RestfulClientHandler;

@Service
public class CouponServiceImpl implements CouponService {
	@Autowired
	private CategoryDao categoryDao;

	@Autowired
	private CouponDao couponDao;

	@Autowired
	private FavouriteDao favouriteDao;

	@Autowired
	private NotificationDao notificationDao;

	@Override
	public Coupon createCoupon(Coupon coupon) throws Exception {
		// get category
		try {
			Category couponCat = categoryDao.getOne(coupon.getCategory().getId());
			coupon.setCategory(couponCat);
			coupon.setClickCount(0);
			coupon = couponDao.save(coupon);

			// create notification for all subscribed client
			Set<User> listUser = new HashSet<>();
			List<User> listUser1 = favouriteDao.getFavouriteByCategory(couponCat);

			// get the main category
			Category mainCat = getMainCategory(couponCat);
			if (mainCat == null) {
				System.out.println("Error while creating notification");
				return coupon;
			}
			// get the main category subscriber
			List<User> listUser2 = favouriteDao.getFavouriteByCategory(mainCat);

			// combine
			listUser.addAll(listUser1);
			listUser.addAll(listUser2);

			// create notification
			for (User user : listUser) {
				Notification noti = new Notification();
				noti.setCoupon(coupon);
				noti.setUser(user);
				notificationDao.save(noti);
			}

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return coupon;
	}

	@Override
	public boolean updateCouponClickcount(int couponId) throws Exception {
		try {
			Coupon coupon = couponDao.getOne(couponId);
			coupon.setClickCount(coupon.getClickCount() + 1);
			coupon = couponDao.save(coupon);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return true;
	}

	@Override
	public Iterable<Coupon> getAllCoupon(int pageNum, int pageSize) {
		Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
		
		return couponDao.findAll(pageable);
	}

	@Override
	public Iterable<Coupon> getCouponByCategory(int pageNum, int pageSize, int categoryId) throws Exception {
		Category category = categoryDao.getOne(categoryId);
		if (category == null)
			throw new Exception("category not found");
		List<Category> listCategory = getAllSubCategory(category);
		System.out.println(listCategory.size());
		List<Coupon> listCoupon = new ArrayList<>();
		for (Category c : listCategory) {
			listCoupon.addAll(couponDao.getCouponByCategory(c, new PageRequest(pageNum - 1, pageSize)));
		}
		return listCoupon;
	}

	@Override
	public Coupon getCouponById(int couponId) throws Exception {
		try {
			return couponDao.findById(couponId).get();
		} catch (NoSuchElementException e) {
			throw new Exception("coupon not found");
		}
	}

	@Override
	public List<Coupon> getCouponByName(int pageNum, int pageSize, String query) throws Exception {
		query = "%" + query + "%";
		query = query.toUpperCase();
		try {
			return couponDao.getCouponByName(query, new PageRequest(pageNum - 1, pageSize));
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public List<Coupon> getCouponByClickcount(int pageSize) throws Exception {
		try {
			return couponDao.getCouponByClickcount(new PageRequest(0, pageSize));
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public Coupon updateCoupon(Coupon coupon) throws Exception {
		// get category
		try {
			coupon.setCategory(categoryDao.getOne(coupon.getCategory().getId()));
			coupon.setClickCount(0);
			coupon = couponDao.save(coupon);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return coupon;
	}

	@Override
	public boolean deleteCoupon(Coupon coupon) {
		couponDao.delete(coupon);
		return true;
	}
	
	@Override
	public Object findByImage(MultipartFile file) throws Exception
	{
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		ByteArrayResource fileAsResources = null;
		try
		{
			fileAsResources = new ByteArrayResource(file.getBytes());
		} catch (IOException e)
		{
			throw new Exception(Constant.EXCEPTION_FILE_CORRUPT);
		}
		body.add("file", fileAsResources);
//		Map response = RestfulClientHandler.postForObject("http://localhost:5000/visualSearch", body, MediaType.MULTIPART_FORM_DATA);
		Map response = RestfulClientHandler.test();
		return response;
	}

	private List<Category> getAllSubCategory(Category category) {
		List<Category> listCategory = new ArrayList<>();
		if (category.getSubCategory() == null || category.getSubCategory().size() < 1) // leaf
		{
			listCategory.add(category);
			return listCategory;
		}
		for (Category c : category.getSubCategory()) {
			listCategory.addAll(getAllSubCategory(c));
		}
		return listCategory;
	}

	private Category getMainCategory(Category category) {
		// get all category;
		List<Category> listCategory = categoryDao.getAllCategory();
		for (Category mainCat : listCategory) {
			if (searchSubCategory(mainCat, category.getId()))
				return mainCat;
		}
		return null;
	}

	private boolean searchSubCategory(Category category, int id) {
		if (category.getId() == id)
			return true;
		if (category.getSubCategory() == null || category.getSubCategory().size() == 0)
			return false;
		boolean result = false;
		for (Category c : category.getSubCategory()) {
			result |= searchSubCategory(c, id);
		}
		return result;
	}

}
