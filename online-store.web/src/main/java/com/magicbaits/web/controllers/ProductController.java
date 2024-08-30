package com.magicbaits.web.controllers;

import java.util.List;

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
	public ProductResponse getProductsByCategory(@RequestParam String id,@RequestParam String page){
		int intId = Integer.parseInt(id);
		int intPage = Integer.parseInt(page);
		List<Product> products = productFacade.getProductsByCategoryIdForPageWithLimit(intId, intPage, PAGINATION_LIMIT);
		int numberOfPages =  productFacade.getNumberOfPagesForCategory(intId, PAGINATION_LIMIT);
		return new ProductResponse(products, numberOfPages);
	}
	
	private class ProductResponse {
        private List<Product> products;
        private int numberOfPages;

        public ProductResponse(List<Product> products, int numberOfPages) {
            this.products = products;
            this.numberOfPages = numberOfPages;
        }

        @SuppressWarnings("unused")
		public List<Product> getProducts() {
            return products;
        }

        @SuppressWarnings("unused")
		public int getNumberOfPages() {
            return numberOfPages;
        }
    }
	
}