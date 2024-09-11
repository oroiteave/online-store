package com.magicbaits.web.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.magicbaits.core.facades.ProductFacade;
import com.magicbaits.core.facades.impl.DefaultProductFacade;
import com.magicbaits.persistence.enteties.Product;

@RestController
public class ProductController {
	private final int PAGINATION_LIMIT = 6;
	private ProductFacade productFacade;
	
	{
		productFacade = DefaultProductFacade.getInstance();
	}
	
	@GetMapping("/product")
	public Product getProduct(@RequestParam String id) {
		return productFacade.getProductById(Integer.parseInt(id));
	}
	
	@GetMapping("/product/search")
	public List<Product> getProductsLikeSearch(@RequestParam String query){
		
		List<Product> products = productFacade.getProductsLikeName(query);
		for(Product p: products) {
			System.out.println(p.getProductName());
		}
		return products;
	}
	
	@GetMapping("/product/category")
	public Map<String,Object> getProductsByCategory(@RequestParam String id,@RequestParam String page){
		Map<String,Object> response = new HashMap<>();
		response.put("products", productFacade.getProductsByCategoryIdForPageWithLimit(Integer.parseInt(id), Integer.parseInt(page), PAGINATION_LIMIT));
		response.put("numberOfPages", productFacade.getNumberOfPagesForCategory(Integer.parseInt(id), PAGINATION_LIMIT));
		return response;
	}
}