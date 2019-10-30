package com.ptit.cms.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.ptit.cms.model.entity.Category;
import com.ptit.cms.model.entity.Coupon;

@Repository
public interface CouponDao extends JpaRepository<Coupon, Integer>,PagingAndSortingRepository<Coupon, Integer>
{
	@Query("SELECT c FROM Coupon c WHERE c.category = ?1")
	List<Coupon> getCouponByCategory(Category c);
	
	@Query("SELECT c FROM Coupon c WHERE UPPER(c.title) LIKE ?1")
	List<Coupon> getCouponByName(String query);
	
	@Query("SELECT c FROM Coupon c ORDER BY c.clickCount DESC")
	List<Coupon> getCouponByClickcount(Pageable pageable);
}
