package com.ptit.cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ptit.cms.dao.NotificationDao;
import com.ptit.cms.model.entity.Notification;
import com.ptit.cms.model.entity.User;
import com.ptit.cms.service.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService
{
	@Autowired
	private NotificationDao notificationDao;
	
	@Override
	public List<Notification> getNotificationByUser(User user)
	{
		return notificationDao.getNotificationByUser(user);
	}

	@Override
	public boolean removeUserNotification(User user) throws Exception
	{
		List<Notification> listNoti = notificationDao.getNotificationByUser(user);
		try
		{
			notificationDao.deleteAll(listNoti);
		}
		catch(Exception e)
		{
			throw new Exception(e.getMessage());
		}
		return true;
	}

}
