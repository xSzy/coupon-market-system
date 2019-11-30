package com.ptit.cms.dao;

import java.util.List;

import com.ptit.cms.model.entity.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ptit.cms.model.entity.Category;
import com.ptit.cms.model.entity.Coupon;

@Repository
public interface CouponDao extends JpaRepository<Coupon, Integer>,PagingAndSortingRepository<Coupon, Integer>, ImageDao
{
	@Query("SELECT c FROM Coupon c WHERE c.category = ?1")
	List<Coupon> getCouponByCategory(Category c,Pageable pageable);
	
	@Query("SELECT c FROM Coupon c WHERE UPPER(c.title) LIKE ?1")
	List<Coupon> getCouponByName(String query,Pageable pageable);
	
	@Query("SELECT c FROM Coupon c ORDER BY c.clickCount DESC")
	List<Coupon> getCouponByClickcount(Pageable pageable);

	@Query("SELECT c FROM Coupon c WHERE c.createdBy = ?1")
    List<Coupon> getCouponByCreator(User user, Pageable pageable);

	@Query("SELECT c FROM Coupon c WHERE c.type = 2 AND c.valueType = 1 ORDER BY c.value DESC")
	List<Coupon> getCouponByDiscount(Pageable pageable);
}
