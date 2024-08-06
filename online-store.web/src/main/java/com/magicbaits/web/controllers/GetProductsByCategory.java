package com.magicbaits.web.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.google.gson.Gson;
import com.magicbaits.core.facades.ProductFacade;
import com.magicbaits.core.facades.impl.DefaultProductFacade;
import com.magicbaits.persistence.enteties.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/getproductsbycategory")
public class GetProductsByCategory extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final int PAGINATION_LIMIT = 6;
	
	private ProductFacade productFacade;
	{
		productFacade = DefaultProductFacade.getInstance();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String categoryId = request.getParameter("id");
		String page = request.getParameter("page");
		
		List<Product> products = productFacade.getProductsByCategoryIdForPageWithLimit(Integer.parseInt(categoryId),Integer.parseInt(page),PAGINATION_LIMIT);
		int numberOfPages = productFacade.getNumberOfPagesForCategory(Integer.parseInt(categoryId),PAGINATION_LIMIT);
		
		 ProductResponse productResponse = new ProductResponse(products, numberOfPages);
		
		Gson gson = new Gson();
		String  json = gson.toJson(productResponse);
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
	}
	 private class ProductResponse {
	        private List<Product> products;
	        private int numberOfPages;

	        public ProductResponse(List<Product> products, int numberOfPages) {
	            this.products = products;
	            this.numberOfPages = numberOfPages;
	        }

	        public List<Product> getProducts() {
	            return products;
	        }

	        public int getNumberOfPages() {
	            return numberOfPages;
	        }
	    }
}
