package com.ptit.cms.service;

import java.util.List;

import javax.validation.Valid;

import com.ptit.cms.model.entity.Coupon;

public interface CouponService
{

	Coupon createCoupon(Coupon coupon) throws Exception;

	boolean updateCouponClickcount(int couponId) throws Exception;

	List<Coupon> getAllCoupon();

	List<Coupon> getCouponByCategory(int categoryId) throws Exception;

	Coupon getCouponById(int couponId) throws Exception;

	List<Coupon> getCouponByName(String query) throws Exception;

	List<Coupon> getCouponByClickcount(int number) throws Exception;

}
