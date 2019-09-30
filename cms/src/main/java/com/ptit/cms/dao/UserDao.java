package com.ptit.cms.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ptit.cms.model.entity.Account;
import com.ptit.cms.model.entity.User;

@Repository
public interface UserDao extends JpaRepository<User, Integer>
{
	@Query("SELECT u FROM User u WHERE u.account = ?1")
	User getUserByAccount(Account account);

}
