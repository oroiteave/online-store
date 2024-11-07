package com.magicbaits.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.magicbaits.core.facades.CategoryFacade;
import com.magicbaits.persistence.enteties.Category;

@RestController
public class CategoryController {
	
	@Autowired
	private CategoryFacade categoryFacade;
	
	@GetMapping("/category")
	public List<Category> getCategories(){
		return categoryFacade.getCategories();
	}
	
}
