package com.magicbaits.core.facades.impl;

import java.util.List;

import com.magicbaits.core.facades.CategoryFacade;
import com.magicbaits.persistence.dao.CategoryDao;
import com.magicbaits.persistence.dao.impl.MySqlJdbcCategoryDao;
import com.magicbaits.persistence.dto.converter.CategoryDtoToCategoryConverter;
import com.magicbaits.persistence.enteties.Category;

public class DefaultCategoryFacade implements CategoryFacade{
	
	private static DefaultCategoryFacade instance;
	private CategoryDao categoryDao = new MySqlJdbcCategoryDao();
	private CategoryDtoToCategoryConverter categoryConverter = new CategoryDtoToCategoryConverter();
	
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

	@Override
	public Category getCategoryById(int id) {
		return categoryConverter.convertCategoryDtoToCategory(categoryDao.getCategoryByCategoryId(id));
	}

}
