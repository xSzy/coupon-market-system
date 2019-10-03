package com.ptit.cms.service;

import java.util.List;

import com.ptit.cms.model.entity.Notification;
import com.ptit.cms.model.entity.User;

public interface NotificationService
{

	List<Notification> getNotificationByUser(User user);

	boolean removeUserNotification(User user) throws Exception;

}
