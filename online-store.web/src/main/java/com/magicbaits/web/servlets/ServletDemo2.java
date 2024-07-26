package com.magicbaits.web.servlets;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;

@WebServlet("/demo2")
public class ServletDemo2 extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		response.getWriter().println("Locale attribute: " + getServletContext().getAttribute("locale"));
	}
	
}
