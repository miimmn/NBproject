package com.example.demo.dao;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.lib.DBConfig;

@Repository
public class UserDAO {
	
	@Autowired
	private DBConfig dbConfig;
	
	org.slf4j.Logger log = LoggerFactory.getLogger(getClass());
	
	
	// user 정보 갖고오기
	public Map<String, Object> getUser(Map<String, Object> user) { 
		
		String sql = 
				  "SELECT usr_email, usr_nm, usr_pw "
				+ "FROM boarduser "
				+ "WHERE usr_email = :usr_email AND usr_pw = :usr_pw";
		
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dbConfig.dataSource());
		
		
		try {
			Map<String, Object> loginUser = namedParameterJdbcTemplate.queryForMap(sql, user);
			return loginUser;
		} catch (Exception e) {
			return null;
		}
		
		
		
		
	}

}
