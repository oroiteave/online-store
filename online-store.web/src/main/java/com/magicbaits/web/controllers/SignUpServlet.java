package com.magicbaits.web.controllers;

import java.io.IOException;

import com.google.gson.Gson;
import com.magicbaits.core.facades.UserFacade;
import com.magicbaits.core.facades.impl.DefaultUserFacade;
import com.magicbaits.persistence.enteties.User;
import com.magicbaits.persistence.enteties.impl.DefaultUser;
import static com.magicbaits.web.filters.PartnerCodeFilter.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/signUp")
public class SignUpServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private UserFacade userFacade;
	
	{
		userFacade = DefaultUserFacade.getInstance();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String errorMessage = (String) request.getSession().getAttribute("errorMessage");
        request.getSession().removeAttribute("errorMessage");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new Gson().toJson(errorMessage));
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String baseUrl = request.getScheme()+ "://"+ request.getServerName()+ ":"
			      + request.getServerPort()+ request.getServletContext().getContextPath();
		
        User user = new DefaultUser();
        user.setFirstName(request.getParameter("firstName"));
        user.setLastName(request.getParameter("lastName"));
        user.setEmail(request.getParameter("email"));
        user.setPassword(request.getParameter("password"));
        String confirmPassword = request.getParameter("confirmPassword");
        
        String validateMessage = validateSignUp(user, confirmPassword);
        
        if(validateMessage!=null) {
        	request.getSession().setAttribute("errorMessage", validateMessage);
        	response.sendRedirect(baseUrl+"/sign-up.html");
        	return;
        }
        
        String partnerCode = null;
        if(request.getCookies()!=null) {
        	for(Cookie cookie : request.getCookies()) {
        		if(cookie.getName().equals(PARTNER_CODE_COOKIE_NAME)) {
        			partnerCode = cookie.getValue();
        		}
        	}
        }
        
        userFacade.registerUser(user,partnerCode);
        response.sendRedirect(baseUrl+"/sign-in.html");
	}
	
	private String validateSignUp(User user, String confirmPassword) {
		
		if(!validatePassword(user.getPassword())) {
        	return "La contraseña debe tener entre 8 y 44 caracteres, y debe incluir al menos un carácter especial.";
        }
        
        if(!user.getPassword().equals(confirmPassword)) {
        	return "La contraseña de confirmación no coincide, por favor intente de nuevo.";
        }
        
        if(userFacade.getUserByEmail(user.getEmail())!=null) {
        	return "El email ya está registrado, por favor intenta con otro email.";
        }
		
		return null;
	}
	
	private boolean validatePassword(String password) {
		
		if (password.length() <= 8 || password.length() >= 44) {
            return false;
        }
        
        String specialCharacters = "[.,!@#$%^&*()_+=|<>?{}\\[\\]~-]";
        
        for (char c : password.toCharArray()) {
            if (String.valueOf(c).matches(specialCharacters)) {
                return true;
            }
        }
		return false;
	}
}
