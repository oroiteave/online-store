package com.magicbaits.web.controllers;

import java.io.IOException;
import java.io.PrintWriter;

import com.google.gson.Gson;
import com.magicbaits.persistence.enteties.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/getUserName")
public class GetUserNameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String LOGGED_IN_USER_ATTR = "loggedInUser";

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		
		User user = (session != null) ? ((User) session.getAttribute(LOGGED_IN_USER_ATTR)) : null;
		String userName = (user!=null) ? user.getFirstName() : null;

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        if (userName != null) {
            out.print("{\"userName\":\"" + userName + "\"}");
        } else {
            out.print("{\"userName\": null}");
        }
        out.flush();
	}
}