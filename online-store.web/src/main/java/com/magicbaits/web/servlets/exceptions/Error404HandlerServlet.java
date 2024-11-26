package com.magicbaits.web.servlets.exceptions;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/errorHandler")
public class Error404HandlerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		handleError(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		handleError(request, response);
	}

	private void handleError(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Integer statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");

        if (statusCode == 404) {
            response.sendRedirect(request.getContextPath() + "/404.html");
        } else {
            response.sendRedirect(request.getContextPath() + "/error.html");
        }
	}
}
