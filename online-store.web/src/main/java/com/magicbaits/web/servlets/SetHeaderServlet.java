package com.magicbaits.web.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SetHeaderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setIntHeader("Refresh", 1);
		response.setContentType("text/html");
		
		Calendar calendar = new GregorianCalendar();
		String am_pm;
		int hour = calendar.get(Calendar.HOUR);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		
		if(calendar.get(Calendar.AM_PM) == 0) {
			am_pm = "AM";
		}
		else {
			am_pm = "PM";
		}
		
		String CT = hour + ":" + minute + ":" + second + " " + am_pm;
		
		PrintWriter out = response.getWriter();
		String title = "Auto refresh header setting";
		String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
		
		out.println(docType + "<html>\n" + "<head><title>" + title + "</title></head>\n" + "<body bgcolor = \"#f0f0f0\">\n"+ "<h1 align = \"center\">" + title + "</h1>\n" + "<p>Current Time is: " + CT + "</p>\n");
	}

}
