package com.ptit.cms.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tblAccount")
public class Account
{

	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@Column(name = "username", unique = true)
	private String username;

	@JsonIgnore
	@Column(name = "password")
	private String password;

	@Column(name = "userid")
	private String userId;

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}
}
