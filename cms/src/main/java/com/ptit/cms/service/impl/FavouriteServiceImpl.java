package com.ptit.cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ptit.cms.dao.FavouriteDao;
import com.ptit.cms.model.entity.Favourite;
import com.ptit.cms.model.entity.User;
import com.ptit.cms.service.FavouriteService;

@Service
public class FavouriteServiceImpl implements FavouriteService
{
	@Autowired
	private FavouriteDao favouriteDao;
	
	@Override
	public Favourite addFavourite(Favourite favourite)
	{
		//check if favourite exists
		if(favouriteDao.getFavourite(favourite.getUser(), favourite.getCategory()) == null)
		{
			favourite = favouriteDao.save(favourite);
		}
		return favourite;
	}

	@Override
	public List<Favourite> getFavouriteByUser(User user) throws Exception
	{
		try
		{
			return favouriteDao.getFavouriteByUser(user);
		}
		catch(Exception e)
		{
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public boolean deleteFavourite(Favourite favourite) throws Exception
	{
		Favourite f = favouriteDao.getFavourite(favourite.getUser(), favourite.getCategory());
		if(f == null)
			throw new Exception("favourite not found");
		favouriteDao.delete(f);
		return true;
	}

}
