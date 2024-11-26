package com.magicbaits.web.filters;

import java.io.IOException;

import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class AuthenticationFilter implements Filter{

	private static final String LOGGED_IN_USER_ATTR = "loggedInUser";
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        boolean loggedIn = (session != null && session.getAttribute(LOGGED_IN_USER_ATTR) != null);

        String requestURI = httpRequest.getRequestURI();
        if (!loggedIn && (requestURI.endsWith("profile-page.html") || requestURI.endsWith("checkout.html")
                || requestURI.endsWith("panel.html") || requestURI.endsWith("purchases.html"))) {
            httpResponse.sendRedirect("/sign-in.html");
            return; 
        }
        chain.doFilter(request, response);
	}

}