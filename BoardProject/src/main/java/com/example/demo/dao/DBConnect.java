package com.example.demo.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnect {
	
	private Connection conn;
	
	public DBConnect() {
	      try {
	    	 
	    	 Class.forName("org.postgresql.Driver");
	         String url="jdbc:postgresql://192.168.56.10:5432/postgres";
	         conn = DriverManager.getConnection(url, "postgres", "1234");
	         System.out.println("postgre DB 접속!");
	         
	      }catch(Exception e){
	    	  
	      }
	}
	
	//Connection 객체를 리턴하는 메소드
	public Connection getConn() {
		return conn;
	}
}
