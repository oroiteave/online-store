package com.magicbaits.web.controllers;

import static com.magicbaits.persistence.dto.RoleDto.ADMIN_ROLE_NAME;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.magicbaits.core.facades.UserFacade;
import com.magicbaits.persistence.enteties.User;
import com.magicbaits.web.utils.PasswordSecurityEncode;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/session")
public class SessionController {
	
private static final String LOGGED_IN_USER_ATTR = "loggedInUser";
	
	@Autowired
	private UserFacade userFacade;
	
	@Autowired
	private PasswordSecurityEncode passwordSecurityEncode;
	

	@PostMapping("/logout")
	public void logOut(HttpSession session, HttpServletResponse response) throws IOException{
		if(session != null) {
			session.invalidate();
		}
		response.sendRedirect("/online-store/index.html");
	}
	
	@PostMapping("/login")
	public void signIn(@RequestParam String email,@RequestParam String password, HttpSession session,  HttpServletResponse response) throws IOException{
		User user = (User)userFacade.getUserByEmail(email);
		String errorMessage = validateSignIn(user, password);
		
		if(errorMessage!=null) {
			session.setAttribute("errorMessage", errorMessage);
			response.sendRedirect("/sign-in.html");
			return;
		}
		
		session.setAttribute(LOGGED_IN_USER_ATTR, user);
		if (user.getRoleName().equals(ADMIN_ROLE_NAME)) {
			response.sendRedirect("/admin/panel.html");
			return;
		} else {
			session.setAttribute("errorMessage", "");
			response.sendRedirect("/index.html");
			return;
		}
	}
	
	private String validateSignIn(User user,String password) {
		if(user == null) {
			return "No existe un usuario con ese email";
		}
		if(!passwordSecurityEncode.checkPassword(password, user.getPassword())) {
			return "La contraseña es incorrecta";
		}
		return null;
	}
	
	@GetMapping("/errorMessage")
	public String errorMessage(HttpSession session) {
		return (String) session.getAttribute("errorMessage");
	}
}