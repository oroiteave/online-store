package com.magicbaits.web.controllers;

import java.io.IOException;
import java.io.PrintWriter;

import com.google.gson.Gson;
import com.magicbaits.core.facades.ProductFacade;
import com.magicbaits.core.facades.impl.DefaultProductFacade;
import com.magicbaits.persistence.enteties.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/getProduct")
public class GetProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ProductFacade productFacade;
	
	{
		productFacade = DefaultProductFacade.getInstance();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String productId = request.getParameter("id");
		
		Product product = productFacade.getProductById(Integer.parseInt(productId));
		
		Gson gson = new Gson();
		String json = gson.toJson(product);
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
	}
}