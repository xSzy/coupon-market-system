package com.ptit.cms.service;

import java.util.List;

import javax.validation.Valid;

import com.ptit.cms.model.entity.Favourite;
import com.ptit.cms.model.entity.User;

public interface FavouriteService
{

	Favourite addFavourite(Favourite favourite);

	List<Favourite> getFavouriteByUser(User user) throws Exception;

	boolean deleteFavourite(Favourite favourite) throws Exception;

}
