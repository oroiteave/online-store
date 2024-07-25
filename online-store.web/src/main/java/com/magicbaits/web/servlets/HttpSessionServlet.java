package com.magicbaits.web.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.http.HttpSession;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HttpSessionServlet
 */
public class HttpSessionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		PrintWriter writer = response.getWriter();
		
		writer.println("Session is created");
		
		session.setAttribute("sessionAttribute", "Session Atribute");
		
		writer.println("<br>");
		writer.println("Session attribute is set");
	}

}
