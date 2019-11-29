package com.ptit.cms.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "tblAccount")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Account
{

	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@NotBlank(message = "username cannot be blank")
	@Column(name = "username", unique = true)
	private String username;

	@NotBlank(message = "password cannot be blank")
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

//	@JsonProperty
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
	
	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}
}
