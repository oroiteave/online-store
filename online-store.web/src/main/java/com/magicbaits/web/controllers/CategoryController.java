package com.magicbaits.web.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.magicbaits.core.facades.CategoryFacade;
import com.magicbaits.core.facades.impl.DefaultCategoryFacade;
import com.magicbaits.persistence.enteties.Category;

@RestController
public class CategoryController {
	private CategoryFacade categoryFacade;
	
	{
		categoryFacade = DefaultCategoryFacade.getInstance();
	}
	
	
	@GetMapping("/category")
	public List<Category> getCategories(){
		return categoryFacade.getCategories();
	}
	
}
