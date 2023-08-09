package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.service.BoardService;

@Controller
@RequestMapping("/cop/bbs/")
public class BoardController {
	
	@Autowired
	BoardService boardService;
	
	
	// 게시물 목록 조회
	@GetMapping("selectArticleList.do")
	public String selectArticleList(Model model) { 
		
		List<HashMap<String, Object>> boardlist = boardService.selectArticleList();
		model.addAttribute("list", boardlist);
		
		return "home";
	}
	
	// 게시물 등록 화면
	@GetMapping("insertArticleView.do")
	public String insertArticleView(Model model) {
		
		model.addAttribute("mode", "reg");
		return "register";
	}
	
	// 게시물 등록
	@PostMapping("insertArticle.do")
	public String insertArticle(@RequestParam HashMap<String, Object> article) { 
	 	
		boardService.insertArticle(article);
		
		return "redirect:/cop/bbs/selectArticleList.do";
	}
	
	
	// 게시물 상세 조회
	@GetMapping("selectArticleDetail.do")
	public String selectArticleDetail(@RequestParam int ntt_id, Model model) { 
					
		HashMap<String, Object> article = boardService.selectArticleDetail(ntt_id);
		model.addAttribute("article", article);
		
		return "detail";
	}
	
	
	// 게시물 수정 화면
	@GetMapping("updateArticleView.do")
	public String updateArticleView(@RequestParam int ntt_id, Model model) { 
		
		// 게시물 상세 정보 (수정 전 내용 출력)
		HashMap<String, Object> article = boardService.selectArticleDetail(ntt_id);
		model.addAttribute("article", article);
		model.addAttribute("mode", "modi");
		
		return "register";
	}
	
	// 게시물 수정
	@PatchMapping("updateArticle.do")
	public String updateArticle(@RequestBody HashMap<String, Object> article) { 
		
		boardService.updateArticle(article);
		
		// 목록 조회 화면으로 이동한다.
		return "redirect:/cop/bbs/selectArticleList.do";
	}
	
	
	// 게시물 삭제
	@GetMapping("deleteArticle.do")
	public String deleteBoardArticle(@RequestParam String ntt_id) {
		
		boardService.deleteArticle(Integer.parseInt(ntt_id));
		
		return "redirect:/cop/bbs/selectArticleList.do";
	}
	
	
	
	
	
	
	
	

}
