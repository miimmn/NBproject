package com.example.demo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {
	
	org.slf4j.Logger log = LoggerFactory.getLogger(getClass());
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String requestURI = request.getRequestURI();
		log.info(" [checkInterceptor] redirect = {}",requestURI);
		
		HttpSession session = request.getSession(false);
		log.info("user : {}",session.getAttribute("loginUser"));
		
		
		if( session == null || session.getAttribute("loginUser") == null) {
			response.sendRedirect("/cop/bbs/signinView.do");
			log.info("로그인 실패...");
			
			// true: 계속 진행, false: 이후 진행 x
			return false;
		}
	
		
		log.info("로그인 됨 ㅇㅇ");
		// 로그인 되어있을 때
		return true;
	}
}
