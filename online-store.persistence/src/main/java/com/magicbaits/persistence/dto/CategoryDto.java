package com.magicbaits.persistence.dto;

public class CategoryDto {
	private Integer id;
	private String categoryName;
	private String imgName;
	
	public String getCategoryName() {
		return this.categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	public Integer getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getImgName() {
		return this.imgName;
	}
	
	public void setImgName(String imgName) {
		this.imgName = imgName;
	}
}
