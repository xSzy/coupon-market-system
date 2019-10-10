package com.ptit.cms.service;

import javax.validation.Valid;


import com.ptit.cms.model.entity.Account;
import com.ptit.cms.model.entity.User;


public interface UserService
{
	public User register(Account account) throws Exception;

	public User login(Account account) throws Exception;

	public User update(User user) throws Exception;
}
