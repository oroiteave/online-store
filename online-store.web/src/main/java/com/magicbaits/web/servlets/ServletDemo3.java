package com.magicbaits.web.servlets;

import java.io.IOException;

import jakarta.servlet.http.HttpServletResponse;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;

@WebServlet("/demo3")
public class ServletDemo3 extends HttpServlet{
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("attr", "Test Attribute Value");
		request.getRequestDispatcher("/demo").forward(request, response);
	}
	
}
