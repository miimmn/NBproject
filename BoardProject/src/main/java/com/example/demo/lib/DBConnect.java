package com.example.demo.lib;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Logger;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class DBConnect {
	
	private Connection conn;

	public DBConnect() {
	      try {
	    	 
	    	 Class.forName("org.postgresql.Driver");
	         String url="jdbc:postgresql://192.168.56.10:5432/postgres";
	         conn = DriverManager.getConnection(url, "postgres", "1234");         
	      }catch(Exception e){    	  
	      }
	}
	//Connection 객체를 리턴하는 메소드
	public Connection getConn() {
		return conn;
	}
}
