package com.example.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.demo.interceptor.LogInterceptor;
import com.example.demo.interceptor.LoginCheckInterceptor;


@Configuration
public class WebConfig implements WebMvcConfigurer{
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LogInterceptor())
				.order(1)
				.addPathPatterns("/cop/bbs/**")
				.excludePathPatterns("/css/**", "/*.ico", "/error",
						"/*.css", "/*js", "/**/*.css", "/**/*.js");
		
		registry.addInterceptor(new LoginCheckInterceptor())
				.order(2)
				.addPathPatterns("/cop/bbs/**")
				.excludePathPatterns("/", "/cop/bbs/selectArticleList.do", "/cop/bbs/selectArticleDetail.do*", 
					"/cop/bbs/signinView.do", "/cop/bbs/signin.do", "/cop/bbs/signout.do", "/css/**", "/*.ico", "/error" , "/js/**", "/**/*.css", "/**/*.json");
	}
}
