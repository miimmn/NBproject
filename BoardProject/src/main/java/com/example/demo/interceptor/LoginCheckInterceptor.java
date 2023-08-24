package com.example.demo.interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;



public class LoginCheckInterceptor implements HandlerInterceptor {
	
	org.slf4j.Logger log = LoggerFactory.getLogger(getClass());
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String requestURI = request.getRequestURI();
		log.info(" [checkInterceptor] redirect = {}", requestURI);
		
		HttpSession session = request.getSession(false);
		
		if( session == null || session.getAttribute("loginUser") == null) {
			
			response.sendError(401, "로그인 후 이용 가능합니다.");

			log.info("로그인 실패");
			return false;  // true: 계속 진행, false: 이후 진행 x
		}
	
		
		log.info("로그인 성공");
		return true;
	}
}
