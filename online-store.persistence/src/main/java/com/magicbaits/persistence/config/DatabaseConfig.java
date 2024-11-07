package com.magicbaits.persistence.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class DatabaseConfig {

	    @Bean
	    PlatformTransactionManager transactionManager(DataSource dataSource) {
	        return new DataSourceTransactionManager(dataSource);
	    }
	
}
