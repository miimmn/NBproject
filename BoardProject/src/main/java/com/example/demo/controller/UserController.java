package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.UserService;

import lombok.extern.slf4j.Slf4j;

import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("/cop/bbs/")
public class UserController {
	
	org.slf4j.Logger log = LoggerFactory.getLogger(getClass());
	
	
	@Autowired
	private UserService userService;

	// 로그인 화면
	@GetMapping("signinView.do")
	public String signinView(Model model) {
		return "login";
	}

	// 로그인
	@PostMapping("signin.do")
	public @ResponseBody Object signin(@RequestParam Map<String, Object> user, 
			 HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		Map<String, Object> LoginUser = userService.getUser(user);
		
		if( LoginUser != null) {	
			session.setAttribute("loginUser", LoginUser);
			session.setMaxInactiveInterval(1800);
		}
		else return 0;
		
		return 1;
	}
	
	
	
	
	// 로그아웃
	@PostMapping("signout.do")
	public String signout(HttpSession session) {
		session.invalidate();
		return "redirect:/cop/bbs/signinView.do";
	}
}
