package com.ptit.cms.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tblProductImage")
public class ProductImage
{
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;
	
//	@JsonIgnore
//	@ManyToOne
//	@JoinColumn(name = "productId")
//	private Product product;
	
	@Column(name = "image")
	private String image;
	
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
//	public Product getProduct()
//	{
//		return product;
//	}
//	public void setProduct(Product product)
//	{
//		this.product = product;
//	}
	public String getImage()
	{
		return image;
	}
	public void setImage(String image)
	{
		this.image = image;
	}
}
