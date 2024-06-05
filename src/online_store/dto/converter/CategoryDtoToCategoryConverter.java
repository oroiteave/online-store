package online_store.dto.converter;

import online_store.dto.CategoryDto;

public class CategoryDtoToCategoryConverter {
	public CategoryDto convertCategoryNameToCategoryDtoWithOnlyName(String categoryName) {
		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setCategoryName(categoryName);
		return categoryDto;
	}
}
