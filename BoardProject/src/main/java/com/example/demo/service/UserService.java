package com.example.demo.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.UserDAO;

@Service
public class UserService {

	@Autowired
	private UserDAO userDAO;
	
	org.slf4j.Logger log = LoggerFactory.getLogger(getClass());
	
	// 유저 정보 갖고오기
	public Map<String, Object> getUser(Map<String, Object> user) {
		
		Map<String, Object> loginUser = userDAO.getUser(user);
		
		return userDAO.getUser(user);
	
	}
	
}
