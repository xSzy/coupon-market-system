package com.ptit.cms.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ptit.cms.model.entity.Notification;
import com.ptit.cms.model.entity.User;

@Repository
public interface NotificationDao extends JpaRepository<Notification, Integer>
{

	@Query("SELECT n FROM Notification n WHERE n.user = ?1")
	List<Notification> getNotificationByUser(User user);
	
}
