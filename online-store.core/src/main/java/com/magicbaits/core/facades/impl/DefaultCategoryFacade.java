package com.magicbaits.core.facades.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.magicbaits.core.facades.CategoryFacade;
import com.magicbaits.persistence.dao.CategoryDao;
import com.magicbaits.persistence.dto.converter.CategoryDtoToCategoryConverter;
import com.magicbaits.persistence.enteties.Category;

@Service
public class DefaultCategoryFacade implements CategoryFacade{
	
	private final CategoryDao categoryDao;
	private final CategoryDtoToCategoryConverter categoryConverter;
	
    public DefaultCategoryFacade(CategoryDao categoryDao, CategoryDtoToCategoryConverter categoryConverter) {
        this.categoryDao = categoryDao;
        this.categoryConverter = categoryConverter;
    }
	
	@Override
	public List<Category> getCategories() {
		return categoryConverter.convertCategoryDtosToCategories(categoryDao.getCategories());
	}

	@Override
	public Category getCategoryById(int id) {
		return categoryConverter.convertCategoryDtoToCategory(categoryDao.getCategoryByCategoryId(id));
	}

}
