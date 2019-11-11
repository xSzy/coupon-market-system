package com.ptit.cms.model.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tblCoupon")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Coupon
{
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;
	
	@NotNull
	@Column(name = "title")
	private String title;
	
	@NotNull
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "productId")
	private Product product;
	
	@NotNull
	@Column(name = "type")
	private int type;
	
	@Column(name = "expiredate")
	private Date expireDate;
	
	@NotNull
	@Column(name = "value")
	private double value;
	
	@NotNull
	@Column(name = "valuetype")
	private int valueType;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "clickCount")
	private int clickCount;
	
	@Column(name = "createdDate")
	private Date createdDate;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "createdByUserId")
	private User createdBy;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "categoryId")
	private Category category;
	
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	public Product getProduct()
	{
		return product;
	}
	public void setProduct(Product product)
	{
		this.product = product;
	}
	public int getType()
	{
		return type;
	}
	public void setType(int type)
	{
		this.type = type;
	}
	public Date getExpireDate()
	{
		return expireDate;
	}
	public void setExpireDate(Date expireDate)
	{
		this.expireDate = expireDate;
	}
	public double getValue()
	{
		return value;
	}
	public void setValue(double value)
	{
		this.value = value;
	}
	public int getValueType()
	{
		return valueType;
	}
	public void setValueType(int valueType)
	{
		this.valueType = valueType;
	}
	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	public int getClickCount()
	{
		return clickCount;
	}
	public void setClickCount(int clickCount)
	{
		this.clickCount = clickCount;
	}
	public Date getCreatedDate()
	{
		return createdDate;
	}
	public void setCreatedDate(Date createdDate)
	{
		this.createdDate = createdDate;
	}
	public User getCreatedBy()
	{
		return createdBy;
	}
	public void setCreatedBy(User createdBy)
	{
		this.createdBy = createdBy;
	}
	public Category getCategory()
	{
		return category;
	}
	public void setCategory(Category category)
	{
		this.category = category;
	}
	
}
