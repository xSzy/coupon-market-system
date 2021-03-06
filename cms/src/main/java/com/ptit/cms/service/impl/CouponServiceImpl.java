package com.ptit.cms.service.impl;

import com.ptit.cms.dao.*;
import com.ptit.cms.model.entity.Category;
import com.ptit.cms.model.entity.Coupon;
import com.ptit.cms.model.entity.Notification;
import com.ptit.cms.model.entity.User;
import com.ptit.cms.model.other.ImageResponse;
import com.ptit.cms.service.CouponService;
import com.ptit.cms.service.MiscService;
import com.ptit.cms.util.Constant;
import com.ptit.cms.util.RestfulClientHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

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

	@Autowired
	private MiscService miscService;

	@Autowired
	private UserDao userDao;

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
	public List<Coupon> getCouponByCategory(int pageNum, int pageSize, int categoryId) throws Exception {
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
	@Transactional
	public List<Coupon> findByImage(MultipartFile file, int limit) throws Exception
	{
		miscService.setMaxAllowedPacket();
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		ByteArrayResource fileAsResources = null;
		try
		{
			fileAsResources = new ByteArrayResource(file.getBytes()) {
				@Override
				public String getFilename()
				{
					return file.getOriginalFilename();
				}
			};
		} catch (IOException e)
		{
			throw new Exception(Constant.EXCEPTION_FILE_CORRUPT);
		}
		body.add("file", fileAsResources);
		body.add("limit", limit);
		Map response = RestfulClientHandler.postForObject(Constant.API_PYTHON_HOME + Constant.API_PREDICT, body, MediaType.MULTIPART_FORM_DATA);
//		Map response = RestfulClientHandler.test();
		List<ImageResponse> responseData = (List<ImageResponse>) response.get("result");
		Set<BigInteger> listCouponIds = couponDao.getCouponByImage(responseData);
		List<Coupon> listCoupon = new ArrayList<>();
		for(BigInteger id : listCouponIds)
			listCoupon.add(couponDao.getOne(id.intValue()));
		return listCoupon;
	}

	@Override
	public List<Coupon> getCouponByCreator(int pageNum, int pageSize, User user) throws Exception {
		user = userDao.getOne(user.getId());
		if (user == null)
			throw new Exception("user not found");
		List<Coupon> listCoupon = couponDao.getCouponByCreator(user, new PageRequest(pageNum - 1, pageSize));
		return listCoupon;
	}

	@Override
	public List<Coupon> getCouponByDiscount(int pageNum, int pageSize) {
		return couponDao.getCouponByDiscount(new PageRequest(pageNum - 1, pageSize));
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
