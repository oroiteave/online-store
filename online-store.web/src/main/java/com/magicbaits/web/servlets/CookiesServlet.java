package com.magicbaits.web.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.servlet.http.Cookie;

/**
 * Servlet implementation class CookiesServlet
 */
public class CookiesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cookie[] cookies = request.getCookies();
		
		response.addCookie(new Cookie("name","value"));
		
		PrintWriter out = response.getWriter();
		String title = "Reading cookies example";
		String docType ="<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
		
		out.println(docType + "<html>\n" + "<head><title>" + title + "</title></head>\n"+ "<body bgcolor = \"#f0f0f0\">\n");
		out.println("<h2> Found cookies name and value</h2>");
		
		for(int i=0;i<cookies.length;i++) {
			Cookie cookie = cookies[i];
			out.println("Name: " + cookie.getName());
			out.println("Value: " + cookie.getValue());
			out.println("<br>");
		}
		out.println("</body>");
		out.println("</html>");
	}
}
