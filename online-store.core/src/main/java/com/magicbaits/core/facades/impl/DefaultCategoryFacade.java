package com.magicbaits.core.facades.impl;

import java.util.List;

import com.magicbaits.core.facades.CategoryFacade;
import com.magicbaits.persistence.dao.CategoryDao;
import com.magicbaits.persistence.dao.impl.MySqlJdbcCategoryDao;
import com.magicbaits.persistence.dto.converter.CategoryDtoToCategoryConverter;
import com.magicbaits.persistence.enteties.Category;

public class DefaultCategoryFacade implements CategoryFacade{
	
	private static DefaultCategoryFacade instance;
	CategoryDao categoryDao = new MySqlJdbcCategoryDao();
	CategoryDtoToCategoryConverter categoryConverter = new CategoryDtoToCategoryConverter();
	
	public static synchronized DefaultCategoryFacade getInstance() {
		if(instance==null) {
			instance = new DefaultCategoryFacade();
		}
		return instance;
	}
	
	@Override
	public List<Category> getCategories() {
		return categoryConverter.convertCategoryDtosToCategories(categoryDao.getCategories());
	}

}
