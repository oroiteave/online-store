package com.magicbaits.persistence.dao;

import java.util.List;

import com.magicbaits.persistence.dto.CategoryDto;

public interface CategoryDao {
	CategoryDto getCategoryByCategoryId(int id);
	List<CategoryDto> getCategories();
}
