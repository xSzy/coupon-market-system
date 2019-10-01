package com.ptit.cms.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ptit.cms.model.entity.Category;

@Repository
public interface CategoryDao extends JpaRepository<Category, Integer>
{
	@Query("SELECT c FROM Category c WHERE c.topCategory = true")
	List<Category> getAllCategory();
}
