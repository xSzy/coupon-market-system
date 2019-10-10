package com.ptit.cms.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ptit.cms.model.entity.Account;

@Repository
public interface AccountDao extends JpaRepository<Account, Integer>
{
	@Query("SELECT a FROM Account a WHERE a.username = ?1")
	List<Account> findAccountByUsername(String username);
	

	@Query("SELECT a FROM Account a WHERE a.username = ?1 AND a.password = ?2")
	Account checkLogin(String username, String password);
	
	@Query("SELECT a FROM Account a WHERE a.userId = ?1")
	List<Account> findAccountByUserid(String userId);
	
	@Query("SELECT a FROM Account a WHERE a.userId = ?1")
	Account checkUserID(String userId);
	
	

}
