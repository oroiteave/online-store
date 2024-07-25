package com.magicbaits.web.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class HttpSessionServlet2
 */
public class HttpSessionServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		PrintWriter writer = response.getWriter();
		writer.println("Session attribute: " + session.getAttribute("sessionAttribute"));
		writer.println("<br>");
		writer.println("Session ID: " + session.getId());
	}
}