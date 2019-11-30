package com.ptit.cms.service;

import java.util.List;

import com.ptit.cms.model.entity.User;
import org.springframework.web.multipart.MultipartFile;

import com.ptit.cms.model.entity.Coupon;

public interface CouponService
{

	Coupon createCoupon(Coupon coupon) throws Exception;

	boolean updateCouponClickcount(int couponId) throws Exception;

	Iterable<Coupon> getAllCoupon(int pageNum, int pageSize);

	List<Coupon> getCouponByCategory(int pageNum, int pageSize, int categoryId) throws Exception;

	Coupon getCouponById(int couponId) throws Exception;

	List<Coupon> getCouponByName(int pageNum, int pageSize, String query) throws Exception;

	List<Coupon> getCouponByClickcount(int number) throws Exception;

	Coupon updateCoupon(Coupon coupon) throws Exception;

	boolean deleteCoupon(Coupon coupon);

	List<Coupon> findByImage(MultipartFile file, int limit) throws Exception;

	List<Coupon> getCouponByCreator(int pageNum, int pageSize, User user) throws Exception;

	List<Coupon> getCouponByDiscount(int pageNum, int pageSize);
}
