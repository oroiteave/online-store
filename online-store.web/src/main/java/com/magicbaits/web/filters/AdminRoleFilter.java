package com.magicbaits.web.filters;

import java.io.IOException;


import com.magicbaits.persistence.enteties.User;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@WebFilter("/resources/admin/*")
public class AdminRoleFilter extends HttpFilter implements Filter {
	
	private static final String LOGGED_IN_USER_ATTR = "loggedInUser";
    private static final String ADMIN_ROLE_NAME = "ROLE_ADMIN";
       
	private static final long serialVersionUID = 1L;

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String baseUrl = request.getScheme() + "://" + request.getServerName() + ":"
			      + request.getServerPort() + request.getServletContext().getContextPath();
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession();
		
		User user = (User) session.getAttribute(LOGGED_IN_USER_ATTR);
		if (user==null) {
            httpResponse.sendRedirect(baseUrl + "/sign-in.html");
            return;
        }
		
		if(user.getRoleName().equals(ADMIN_ROLE_NAME)) {
			chain.doFilter(request, response);
		}else {
			httpResponse.sendError(403);
		}
		
	}

}
