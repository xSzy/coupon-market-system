package com.ptit.cms.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.ptit.cms.model.entity.Category;
import com.ptit.cms.model.entity.Coupon;
import com.ptit.cms.model.other.ErrorMessage;
import com.ptit.cms.model.other.ResponseModel;
import com.ptit.cms.service.CouponService;
import com.ptit.cms.util.Constant;

@RestController
@RequestMapping("#{'${rest.base-path}'}")
public class CouponController {
	@Autowired
	private CouponService couponService;

	@RequestMapping(value = "/coupon/create", method = RequestMethod.POST)
	public ResponseEntity<ResponseModel> createCoupon(@Valid @RequestBody Coupon coupon) {
		ResponseModel response = new ResponseModel();
		ErrorMessage errorMessage = new ErrorMessage();

		Coupon newCoupon = null;
		try {
			newCoupon = couponService.createCoupon(coupon);
			response.setStatus(Constant.STATUS_SUCCESS);
			response.setError(null);
		} catch (Exception e) {
			response.setStatus(Constant.STATUS_ERROR);
			errorMessage.setErrorCode(-1);
			errorMessage.setMessage(e.getMessage());
			response.setError(errorMessage);
			e.printStackTrace();
		}
		response.setData(newCoupon);
		return new ResponseEntity<ResponseModel>(response,
				newCoupon == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
	}

	@RequestMapping(value = "/coupon/click", method = RequestMethod.GET)
	public ResponseEntity<ResponseModel> couponClick(@RequestParam int couponId) {
		ResponseModel response = new ResponseModel();
		ErrorMessage errorMessage = new ErrorMessage();

		boolean result = false;
		try {
			result = couponService.updateCouponClickcount(couponId);
			response.setStatus(Constant.STATUS_SUCCESS);
			response.setError(null);
		} catch (Exception e) {
			response.setStatus(Constant.STATUS_ERROR);
			errorMessage.setErrorCode(-1);
			errorMessage.setMessage(e.getMessage());
			response.setError(errorMessage);
			e.printStackTrace();
		}
		response.setData(result);
		return new ResponseEntity<ResponseModel>(response,
				result == false ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
	}

	@RequestMapping(value = "/coupon/getAll", method = RequestMethod.GET)
	public ResponseEntity<ResponseModel> getAllCoupon(
			@RequestParam(value = "_page", required = true) int _page,
			@RequestParam(value = "_limit", required = true) int _limit)

	{
		
		ResponseModel response = new ResponseModel();
		ErrorMessage errorMessage = new ErrorMessage();

		List<Coupon> listCoupon = null;
		try {
			listCoupon = new ArrayList<Coupon>();
			//listCoupon = Lists.newArrayList(couponService.getAllCoupon(1, 10));
			couponService.getAllCoupon(_page,_limit).forEach(listCoupon::add);
			response.setStatus(Constant.STATUS_SUCCESS);
			response.setError(null);
		} catch (Exception e) {
			response.setStatus(Constant.STATUS_ERROR);
			errorMessage.setErrorCode(-1);
			errorMessage.setMessage(e.getMessage());
			response.setError(errorMessage);
			e.printStackTrace();
		}
		response.setData(listCoupon);
		return new ResponseEntity<ResponseModel>(response,
				listCoupon == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
	}

	@RequestMapping(value = "/coupon/findByCategory", method = RequestMethod.GET)
	public ResponseEntity<ResponseModel> getCouponByCategory(@RequestParam int categoryId,
			@RequestParam(value = "_page", required = true) int _page,
			@RequestParam(value = "_limit", required = true) int _limit) {
		ResponseModel response = new ResponseModel();
		ErrorMessage errorMessage = new ErrorMessage();

		List<Coupon> listCoupon = null;
		try {
			listCoupon = new ArrayList<Coupon>();
			couponService.getCouponByCategory(_page, _limit, categoryId).forEach(listCoupon::add);
			response.setStatus(Constant.STATUS_SUCCESS);
			response.setError(null);
		} catch (Exception e) {
			response.setStatus(Constant.STATUS_ERROR);
			errorMessage.setErrorCode(-1);
			errorMessage.setMessage(e.getMessage());
			response.setError(errorMessage);
			e.printStackTrace();
		}
		response.setData(listCoupon);
		return new ResponseEntity<ResponseModel>(response,
				listCoupon == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
	}

	@RequestMapping(value = "/coupon/getById", method = RequestMethod.GET)
	public ResponseEntity<ResponseModel> getCouponById(@RequestParam int couponId) {
		ResponseModel response = new ResponseModel();
		ErrorMessage errorMessage = new ErrorMessage();

		Coupon coupon = null;
		try {
			coupon = couponService.getCouponById(couponId);
			response.setStatus(Constant.STATUS_SUCCESS);
			response.setError(null);
		} catch (Exception e) {
			response.setStatus(Constant.STATUS_ERROR);
			errorMessage.setErrorCode(-1);
			errorMessage.setMessage(e.getMessage());
			response.setError(errorMessage);
			e.printStackTrace();
		}
		response.setData(coupon);
		return new ResponseEntity<ResponseModel>(response,
				coupon == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
	}

	@RequestMapping(value = "/coupon/findByName", method = RequestMethod.GET)
	public ResponseEntity<ResponseModel> getCouponByName(@RequestParam int pageNum, @RequestParam int pageSize, @RequestParam String query) {
		ResponseModel response = new ResponseModel();
		ErrorMessage errorMessage = new ErrorMessage();

		List<Coupon> listCoupon = null;
		try {
			listCoupon = couponService.getCouponByName(pageNum,pageSize,query);
			response.setStatus(Constant.STATUS_SUCCESS);
			response.setError(null);
		} catch (Exception e) {
			response.setStatus(Constant.STATUS_ERROR);
			errorMessage.setErrorCode(-1);
			errorMessage.setMessage(e.getMessage());
			response.setError(errorMessage);
			e.printStackTrace();
		}
		response.setData(listCoupon);
		return new ResponseEntity<ResponseModel>(response,
				listCoupon == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
	}

	// trả về danh sách coupon xem nhiều nhất
	@RequestMapping(value = "/coupon/getMostViewed", method = RequestMethod.GET)
	public ResponseEntity<ResponseModel> getCouponByClickcount(@RequestParam int pageSize) {
		ResponseModel response = new ResponseModel();
		ErrorMessage errorMessage = new ErrorMessage();
		List<Coupon> listCoupon = null;
		try {
			listCoupon = couponService.getCouponByClickcount(pageSize);
			System.out.println("value type : " + listCoupon.get(0).getValueType());
			response.setStatus(Constant.STATUS_SUCCESS);
			response.setError(null);
		} catch (Exception e) {
			response.setStatus(Constant.STATUS_ERROR);
			errorMessage.setErrorCode(-1);
			errorMessage.setMessage(e.getMessage());
			response.setError(errorMessage);
			e.printStackTrace();
		}
		response.setData(listCoupon);
		return new ResponseEntity<ResponseModel>(response,
				listCoupon == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
	}

	@RequestMapping(value = "/coupon/update", method = RequestMethod.POST)
	public ResponseEntity<ResponseModel> updateCoupon(@Valid @RequestBody Coupon coupon) {
		ResponseModel response = new ResponseModel();
		ErrorMessage errorMessage = new ErrorMessage();

		Coupon newCoupon = null;
		try {
			newCoupon = couponService.updateCoupon(coupon);
			response.setStatus(Constant.STATUS_SUCCESS);
			response.setError(null);
		} catch (Exception e) {
			response.setStatus(Constant.STATUS_ERROR);
			errorMessage.setErrorCode(-1);
			errorMessage.setMessage(e.getMessage());
			response.setError(errorMessage);
			e.printStackTrace();
		}
		response.setData(newCoupon);
		return new ResponseEntity<ResponseModel>(response,
				newCoupon == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
	}

	@RequestMapping(value = "/coupon/delete", method = RequestMethod.POST)
	public ResponseEntity<ResponseModel> deleteCoupon(@RequestBody Coupon coupon) {
		ResponseModel response = new ResponseModel();
		ErrorMessage errorMessage = new ErrorMessage();

		boolean result = false;
		try {
			result = couponService.deleteCoupon(coupon);
			response.setStatus(Constant.STATUS_SUCCESS);
			response.setError(null);
		} catch (Exception e) {
			response.setStatus(Constant.STATUS_ERROR);
			errorMessage.setErrorCode(-1);
			errorMessage.setMessage(e.getMessage());
			response.setError(errorMessage);
			e.printStackTrace();
		}
		response.setData(result);
		return new ResponseEntity<ResponseModel>(response,
				result == false ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
	}
	
	@RequestMapping(value = "/coupon/findByImage", method = RequestMethod.POST)
	public ResponseEntity<ResponseModel> findCouponByImage(@RequestPart MultipartFile file, @RequestPart String limit)
	{
		ResponseModel response = new ResponseModel();
		ErrorMessage errorMessage = new ErrorMessage();

		List<Coupon> data = null;
		try
		{
			data = couponService.findByImage(file, Integer.parseInt(limit));
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
		response.setData(data);
		return new ResponseEntity<ResponseModel>(response, data == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
	}
}
