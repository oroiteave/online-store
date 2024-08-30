package com.magicbaits.web.controllers;

import static com.magicbaits.persistence.dto.RoleDto.ADMIN_ROLE_NAME;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.magicbaits.core.facades.UserFacade;
import com.magicbaits.core.facades.impl.DefaultUserFacade;
import com.magicbaits.persistence.enteties.User;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class SessionController {
	
private static final String LOGGED_IN_USER_ATTR = "loggedInUser";
	
	private UserFacade userFacade;
	{
		userFacade = DefaultUserFacade.getInstance();
	}

	@PostMapping("/logout")
	public String logOut(HttpSession session, HttpServletResponse response) throws IOException{
		if(session != null) {
			session.invalidate();
		}
		return "redirect:/index.html";
	}
	
	@PostMapping("/login")
	public String signIn(@RequestParam String email,@RequestParam String password, HttpSession session,  HttpServletResponse response) throws IOException{
		User user = (User)userFacade.getUserByEmail(email);
		System.out.println(user.toString());
		if (user != null && user.getPassword().equals(password)) {
			session.setAttribute(LOGGED_IN_USER_ATTR, user);
			if (user.getRoleName().equals(ADMIN_ROLE_NAME)) {
				return "redirect:/admin/panel";
			} else {
				return "redirect:/index.html";
			}
		}
		return "redirect:/sign-in.html";
	}
}
