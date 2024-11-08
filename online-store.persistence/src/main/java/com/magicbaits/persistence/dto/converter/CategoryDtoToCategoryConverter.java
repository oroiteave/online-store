package com.magicbaits.persistence.dto.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.magicbaits.persistence.dto.CategoryDto;
import com.magicbaits.persistence.enteties.Category;
import com.magicbaits.persistence.enteties.impl.DefaultCategory;

@Component
public class CategoryDtoToCategoryConverter {
	public CategoryDto convertCategoryNameToCategoryDtoWithOnlyName(String categoryName) {
		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setCategoryName(categoryName);
		return categoryDto;
	}
	
	public List<Category> convertCategoryDtosToCategories(List<CategoryDto> categoryDtos) {
		List<Category> categories = new ArrayList<>();

		for (CategoryDto categoryDto : categoryDtos) {
			categories.add(convertCategoryDtoToCategory(categoryDto));
		}
		return categories;
	}

	public Category convertCategoryDtoToCategory(CategoryDto categoryDto) {
		DefaultCategory newCategory = new DefaultCategory();
		newCategory.setId(categoryDto.getId());
		newCategory.setCategoryName(categoryDto.getCategoryName());
		newCategory.setImgName(categoryDto.getImgName());
		return newCategory;
	}
}
