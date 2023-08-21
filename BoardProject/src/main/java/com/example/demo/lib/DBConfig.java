package com.example.demo.lib;


import javax.sql.DataSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;



@Configuration
public class DBConfig {

    private String className = "org.postgresql.Driver";
    private String url = "jdbc:postgresql://192.168.56.10:5432/postgres"; 
    private String username = "postgres";
    private String password = "1234";
    
	public DataSource dataSource() { 
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		dataSource.setUrl(url);
		dataSource.setDriverClassName(className);
		
		return dataSource;
	}
	
	

}