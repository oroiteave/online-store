package com.magicbaits.web.listeners.exceptions;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class DriverCleanUpListener implements ServletContextListener {

	 @Override
	    public void contextDestroyed(ServletContextEvent sce) {
	        // Close all database connections
	        Enumeration<java.sql.Driver> drivers = DriverManager.getDrivers();
	        while (drivers.hasMoreElements()) {
	            java.sql.Driver driver = drivers.nextElement();
	            try {
	                DriverManager.deregisterDriver(driver);
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }

	        com.mysql.cj.jdbc.AbandonedConnectionCleanupThread.checkedShutdown();
	    }
	
}
