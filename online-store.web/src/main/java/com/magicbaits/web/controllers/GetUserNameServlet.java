package com.magicbaits.web.controllers;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/getUserName")
public class GetUserNameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
        String userName = (session != null) ? (String) session.getAttribute("userName") : null;

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