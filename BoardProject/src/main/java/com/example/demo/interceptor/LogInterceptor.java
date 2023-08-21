package com.example.demo.interceptor;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LogInterceptor implements HandlerInterceptor{
	
	org.slf4j.Logger log = LoggerFactory.getLogger(getClass());
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) 
			throws Exception{
		
		String requestURI = request.getRequestURI();
		log.info(" !!!!!!!!!! requestURI : {}", requestURI);
		
		return true;
	}
	
	
	// controller 실행 후, 뷰 실행 전
	@Override
	public void postHandle( HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) {
		
		log.info("[interceptor postHandle]");
	}
	
	// 뷰 response 끝난 후 실행
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		log.info("[interceptor afterCompletion]");
	}
}
