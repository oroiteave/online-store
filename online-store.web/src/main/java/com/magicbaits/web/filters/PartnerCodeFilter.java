package com.magicbaits.web.filters;

import java.io.IOException;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpFilter;

@WebFilter("/*")
public class PartnerCodeFilter extends HttpFilter implements Filter {
	
	private static final long serialVersionUID = 1L;
	public static final String PARTNER_CODE_PARAMETER_NAME = "partner_code";
	public static final String PARTNER_CODE_COOKIE_NAME ="partner_code";
       
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String partnerCode = request.getParameter(PARTNER_CODE_COOKIE_NAME);
		if(partnerCode != null && !partnerCode.isEmpty()) {
			((HttpServletResponse) response).addCookie(new Cookie(PARTNER_CODE_COOKIE_NAME,partnerCode));
		}
		chain.doFilter(request, response);
	}
}