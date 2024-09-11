package com.magicbaits.web.filters;

import java.io.IOException;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class PartnerCodeFilter implements Filter {
	
	public static final String PARTNER_CODE_PARAMETER_NAME = "partner_code";
	public static final String PARTNER_CODE_COOKIE_NAME ="partner_code";
       
	@Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        String partnerCode = httpRequest.getParameter(PARTNER_CODE_PARAMETER_NAME);
        
        if (partnerCode != null && !partnerCode.isEmpty()) {
            Cookie cookie = new Cookie(PARTNER_CODE_COOKIE_NAME, partnerCode);
            cookie.setPath("/"); 
            cookie.setHttpOnly(true);
            httpResponse.addCookie(cookie);
        }
        
        chain.doFilter(request, response);
    }
}