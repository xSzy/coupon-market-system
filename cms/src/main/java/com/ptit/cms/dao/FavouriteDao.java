package com.ptit.cms.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ptit.cms.model.entity.Category;
import com.ptit.cms.model.entity.Favourite;
import com.ptit.cms.model.entity.User;

@Repository
public interface FavouriteDao extends JpaRepository<Favourite, Integer>
{

	@Query("SELECT f FROM Favourite f WHERE f.user = ?1 AND f.category = ?2")
	Favourite getFavourite(User user, Category category);

	@Query("SELECT f FROM Favourite f WHERE f.user = ?1")
	List<Favourite> getFavouriteByUser(User user);
	
	@Query("SELECT f.user FROM Favourite f WHERE f.category = ?1")
	List<User> getFavouriteByCategory(Category category);
}
