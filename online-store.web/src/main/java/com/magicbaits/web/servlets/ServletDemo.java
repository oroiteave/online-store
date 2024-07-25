package com.magicbaits.web.servlets;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;


@WebServlet(urlPatterns = "/demo", initParams = {
		@WebInitParam(name = "firstName", value = "Oroi"),
		@WebInitParam(name = "lastName", value = "Teave")
})
public class ServletDemo extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		String firstName = getInitParameter("firstName");
        String lastName = getInitParameter("lastName");

        PrintWriter writer = response.getWriter();

        writer.println("firstName = " + firstName + "; ");
        writer.println("lastName = " + lastName);
        
        writer.println("</br>");
        
        writer.println("Servlet name: " + getServletConfig().getServletName());
        writer.println("</br>");
        writer.println("Servlet name: " + getServletName());
        writer.println("</br>");
        writer.println("Context path: " + getServletContext().getContextPath());
        writer.println("</br>");
        
        getServletContext().setAttribute("locale", Locale.US);
        
        String param = request.getParameter("param");
        writer.println("Request param value: " + param);
        writer.println("</br>");
        writer.println("Request attribute: " + request.getAttribute("attr"));
		
	}

}
