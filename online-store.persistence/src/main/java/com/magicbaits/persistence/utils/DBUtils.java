package com.magicbaits.persistence.utils;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DBUtils {
	private static final String JDBC_MYSQL_HOST = "jdbc:mysql://localhost:3306/";
	private static final String DB_NAME = "online_store_db";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "toor";
	
	private static HikariDataSource dataSource;
	
	static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(JDBC_MYSQL_HOST + DB_NAME);
        config.setUsername(USERNAME);
        config.setPassword(PASSWORD);
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setMaximumPoolSize(10);  // Número máximo de conexiones en el pool
        config.setMinimumIdle(2);       // Número mínimo de conexiones inactivas
        config.setIdleTimeout(30000);   // Tiempo de inactividad antes de cerrar una conexión
        config.setMaxLifetime(1800000); // Tiempo máximo de vida de una conexión

        dataSource = new HikariDataSource(config);
    }
	
	public static Connection getConnection() throws SQLException{
		return dataSource.getConnection();
	}
}
