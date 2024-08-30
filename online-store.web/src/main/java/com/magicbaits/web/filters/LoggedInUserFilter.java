package com.magicbaits.web.filters;

import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import com.magicbaits.persistence.enteties.User;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;

@WebFilter(urlPatterns = {"/checkout.html","/profile-page.html"})
public class LoggedInUserFilter extends HttpFilter implements Filter {
	private static final long serialVersionUID = 1L;
	private static final String LOGGED_IN_USER_ATTR = "loggedInUser";
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String baseUrl = request.getScheme() + "://" + request.getServerName() + ":"
			      + request.getServerPort() + request.getServletContext().getContextPath();
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession();
		
		User user = (User) session.getAttribute(LOGGED_IN_USER_ATTR);
		if (user==null) {
          httpResponse.sendRedirect(baseUrl + "/homepage.html");
          return;
      }
		chain.doFilter(request, response);
	}
}
