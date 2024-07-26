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
	
	private ProductFacade productFacade;
	{
		productFacade = DefaultProductFacade.getInstance();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String categoryId = request.getParameter("id");
		
		List<Product> products = productFacade.getProductsByCategoryId(Integer.parseInt(categoryId));
		
		Gson gson = new Gson();
		String  json = gson.toJson(products);
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
	}
}
