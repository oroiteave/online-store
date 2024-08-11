package com.magicbaits.web.controllers;

import java.io.IOException;

import static com.magicbaits.persistence.dto.RoleDto.*;
import com.magicbaits.core.facades.UserFacade;
import com.magicbaits.core.facades.impl.DefaultUserFacade;
import com.magicbaits.persistence.enteties.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/signIn")
public class SignInServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String LOGGED_IN_USER_ATTR = "loggedInUser";
	
	private UserFacade userFacade;
	{
		userFacade = DefaultUserFacade.getInstance();
	}
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String baseUrl = request.getScheme() + "://" + request.getServerName() + ":"
			      + request.getServerPort() + request.getServletContext().getContextPath();
		User user = userFacade.getUserByEmail(request.getParameter("email"));
		
		if (user != null && user.getPassword().equals(request.getParameter("password"))) {
			request.getSession().setAttribute(LOGGED_IN_USER_ATTR, user);
			if (user.getRoleName().equals(ADMIN_ROLE_NAME)) {
				response.sendRedirect(baseUrl + "/admin/panel");
			} else {
				response.sendRedirect(baseUrl);
			}
		} else {
			response.sendRedirect(baseUrl + "/sign-in.html");
		}
	}

}
