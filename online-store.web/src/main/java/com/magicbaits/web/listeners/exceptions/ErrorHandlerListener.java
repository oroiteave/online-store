package com.magicbaits.web.listeners.exceptions;

import com.magicbaits.web.servlets.exceptions.Error404HandlerServlet;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

public class ErrorHandlerListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public ErrorHandlerListener() {
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().addServlet("errorHandler", new Error404HandlerServlet()).addMapping("/errorHandler");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

}
