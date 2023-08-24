package com.example.demo.lib;

import javax.sql.DataSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DBConfig {

	private String className = "org.postgresql.Driver";
	private String url = "jdbc:postgresql://192.168.56.10:5432/postgres";
	private String username = "postgres";
	private String password = "1234";

	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private JdbcTemplate jdbcTemplate;

	
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		dataSource.setUrl(url);
		dataSource.setDriverClassName(className);

		return dataSource;
	}

	
	public NamedParameterJdbcTemplate getNamedParameterJdbc() {

		if (namedParameterJdbcTemplate != null) {
			return namedParameterJdbcTemplate;
		}

		return new NamedParameterJdbcTemplate(dataSource());
	}

	public JdbcTemplate getJdbc() {

		if (jdbcTemplate != null) {
			return jdbcTemplate;
		}

		return new JdbcTemplate(dataSource());
	}

}