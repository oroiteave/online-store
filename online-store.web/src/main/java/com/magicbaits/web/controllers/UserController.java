package com.magicbaits.web.controllers;

import static com.magicbaits.web.filters.PartnerCodeFilter.PARTNER_CODE_COOKIE_NAME;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.magicbaits.core.facades.UserFacade;
import com.magicbaits.core.facades.impl.DefaultUserFacade;
import com.magicbaits.persistence.enteties.User;
import com.magicbaits.persistence.enteties.impl.DefaultUser;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController
public class UserController {
	private static final String LOGGED_IN_USER_ATTR = "loggedInUser";
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private UserFacade userFacade;
	
	{
		userFacade = DefaultUserFacade.getInstance();
	}
	
	@GetMapping("/user/current")
	public ResponseEntity<User> getUser(HttpSession session) {
		User user = (session != null) ? ((User) session.getAttribute(LOGGED_IN_USER_ATTR)) : null;
		return ResponseEntity.ok(user);
	}
	
	@GetMapping("/user")
	public User getUserById(@RequestParam String id) {
		return userFacade.getUserByid(Integer.parseInt(id));
	}
	
	@PutMapping("/user")
	public String updateuser(HttpSession session,@RequestParam String firstName, @RequestParam String lastName) {
		String message="Error al enviar los nuevos datos de usuario";
		User user = (User) session.getAttribute(LOGGED_IN_USER_ATTR);
		
		if(firstName!=null && lastName!=null) {
			user.setFirstName(firstName);
			user.setLastName(lastName);
			userFacade.updateUser(user);
			message = "Datos de usuario actualizados correctamente";
		}
		
		return message;
	}
	
	@PutMapping("/user/password")
	public String updatePassword(HttpSession session, @RequestParam String password, @RequestParam String newPassword) {
		String message="Error cambiar la contraseña";
		User user = (User) session.getAttribute(LOGGED_IN_USER_ATTR);
				
		if(checkPassword(password, user.getPassword()) && validatePassword(newPassword)) {
			String encodedPassword = passwordEncoder.encode(newPassword);
			user.setPassword(encodedPassword);
			userFacade.updateUser(user);
			message = "Contraseña cambiada con exito";
		}
		
		return message;
	}
	
	@PutMapping("/user/email")
	public String updateEmail(HttpSession session, @RequestParam String email) {
		String message ="Error al cambiar el email";
		User user = (User) session.getAttribute(LOGGED_IN_USER_ATTR);
		
		if(!user.getEmail().equals(email)) {
			user.setEmail(email);
			userFacade.updateUser(user);
			message = "Email cambiado con exito";
		}else {
			message = "El email no puede ser igual al anterior";
		}
		
		return message;
	}
	
	@PostMapping("/user")
	public void signUp(HttpServletRequest request,HttpServletResponse response, HttpSession session,@RequestParam String firstName, @RequestParam String lastName, @RequestParam String email, @RequestParam String password, @RequestParam String confirmPassword) throws IOException {
		User user = new DefaultUser();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setPassword(password);
		String validateMessage = validateSignUp(user, confirmPassword);
		
        
        if(validateMessage!=null) {
        	session.setAttribute("errorMessage", validateMessage);
        	response.sendRedirect("/sign-up.html");
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
        
        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);
        userFacade.registerUser(user,partnerCode);
        response.sendRedirect("/sign-in.html");
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
	private boolean checkPassword(String rawPassword, String encodedPassword) {
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}
}