package com.ptit.cms.model.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tblCategory")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Category
{

	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "icon")
	private String icon;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Category> subCategory;

	@Column(name = "description")
	private String description;
	
	@Column(name = "descriptionEnglish")
	private String descriptionEnglish;
	
	@Column(name = "isTopCategory")
	private boolean topCategory;

	public boolean isTopCategory()
	{
		return topCategory;
	}

	public void setTopCategory(boolean topCategory)
	{
		this.topCategory = topCategory;
	}

	public String getDescriptionEnglish()
	{
		return descriptionEnglish;
	}

	public void setDescriptionEnglish(String descriptionEnglish)
	{
		this.descriptionEnglish = descriptionEnglish;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getIcon()
	{
		return icon;
	}

	public void setIcon(String icon)
	{
		this.icon = icon;
	}

	public List<Category> getSubCategory()
	{
		return subCategory;
	}

	public void setSubCategory(List<Category> subCategory)
	{
		this.subCategory = subCategory;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public boolean add(Category e)
	{
		return subCategory.add(e);
	}

	public boolean remove(Object o)
	{
		return subCategory.remove(o);
	}
}
