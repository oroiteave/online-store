package com.magicbaits.web.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.google.gson.Gson;
import com.magicbaits.core.facades.CategoryFacade;
import com.magicbaits.core.facades.impl.DefaultCategoryFacade;
import com.magicbaits.persistence.enteties.Category;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/getCategories")
public class GetCategoriesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private CategoryFacade categoryFacade;
	
	{
		categoryFacade = DefaultCategoryFacade.getInstance();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<Category> categories = categoryFacade.getCategories();
		
		Gson gson = new Gson();
        String json = gson.toJson(categories);

        // Set response type to JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Write JSON to response
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
		
	}

}
