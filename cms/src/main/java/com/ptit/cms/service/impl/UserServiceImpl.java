package com.ptit.cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ptit.cms.dao.AccountDao;
import com.ptit.cms.dao.UserDao;
import com.ptit.cms.model.entity.Account;
import com.ptit.cms.model.entity.User;
import com.ptit.cms.service.UserService;
import com.ptit.cms.util.Constant;

@Service
public class UserServiceImpl implements UserService
{
	@Autowired
	private AccountDao accountDao;
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public User register(Account account) throws Exception
	{
		List<Account> listAccount = null;
		//check if account exists
		if(account.getUserId().isEmpty()) {
			 listAccount = accountDao.findAccountByUsername(account.getUsername());
		}else {
			 listAccount = accountDao.findAccountByUserid(account.getUserId());
		}
		
		if(listAccount.size() > 0)	//account exist
		{
			throw new Exception(Constant.EXCEPTION_ACCOUNT_EXISTS);
		}
		User user = new User();
		if(!account.getUserId().isEmpty()) {
			account.setUsername(account.getUserId());
			account.setPassword(account.getUserId());
		}
		user.setAccount(account);
		user = userDao.save(user);
		return user;
	}

	@Override
	public User login(Account account) throws Exception
	{
		//check if account exists
		if(account.getUserId().isEmpty()){
			account = accountDao.checkLogin(account.getUsername(), account.getPassword());

		}else {
			account = accountDao.checkUserID(account.getUserId());
		}
		if(account == null)	//wrong login credential
		{
			throw new Exception(Constant.EXCEPTION_INCORRECT_LOGIN);
		}
		User user = userDao.getUserByAccount(account);
		return user;
	}

	@Override
	public User update(User user) throws Exception
	{
		try
		{
			user = userDao.save(user);
		}
		catch (Exception e)
		{
			throw new Exception(e.getMessage());
		}
		return user;
	}
	
}
