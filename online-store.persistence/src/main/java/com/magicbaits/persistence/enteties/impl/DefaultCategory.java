package com.magicbaits.persistence.enteties.impl;

import com.magicbaits.persistence.enteties.Category;

public class DefaultCategory implements Category{

	private int id;
	private String categoryName;
	private String imgName;
	
	@Override
	public Integer getId() {
		return this.id;
	}

	@Override
	public String getCategoryName() {
		return this.categoryName;
	}
	
	public void setId(int id){
		this.id = id;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	public String getImgName() {
		return this.imgName;
	}
	
	public void setImgName(String imgName) {
		this.imgName = imgName;
	}
}
