package com.example.demo.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import com.example.demo.service.BoardService;
import com.example.demo.service.CommentService;

@Controller
@RequestMapping("/cop/bbs/")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	@Autowired
	private CommentService commentService;
	
	
	org.slf4j.Logger log = LoggerFactory.getLogger(getClass());
	
	
	// 게시물 목록 조회
	@GetMapping("selectArticleList.do")
	public String selectArticleList(Model model, HttpSession session) { 
		
		List<Map<String, Object>> boardlist = boardService.selectArticleList();
		
		model.addAttribute("list", boardlist);
		
		return "home";
	}
	
	
	// 게시물 등록 화면
	@GetMapping("insertArticleView.do")
	public String insertArticleView(HttpSession session ,Model model, HttpServletResponse response) throws Exception {
		model.addAttribute("mode", "reg");
		return "register";
	}
	
	
	// 게시물 등록
	@PostMapping("insertArticle.do")
	public String insertArticle(@RequestParam HashMap<String, Object> article, HttpSession session) { 
	 	
		Map<String, Object> user = (Map<String, Object>)session.getAttribute("loginUser");
		
		// user 정보 넣기
		article.put("ntcr_id", user.get("usr_email"));
		article.put("ntcr_nm", user.get("usr_nm"));
		article.put("frst_register_id", user.get("usr_email"));

		boardService.insertArticle(article);
		
		return "redirect:/cop/bbs/selectArticleList.do";
	}
	
	
	// 게시물 상세 조회
	@GetMapping("selectArticleDetail.do")
	public String selectArticleDetail(@RequestParam int ntt_id, Model model,
			HttpSession session) { 
			
		Map<String, Object> user = (Map<String, Object>) session.getAttribute("loginUser");
		Map<String, Object> article = boardService.selectArticleDetail(ntt_id);
		
		// 자신의 게시물이 아니면 false
		model.addAttribute("myArticle", false);
		
		// 로그인 유저 & 자신의 게시물인 경우 true
		if( user != null) {
			String userId = (String)user.get("usr_email");
			String articleId = (String)article.get("ntcr_id");
			boolean myArticle = userId.equals(articleId);
			
			model.addAttribute("myArticle", myArticle);
			model.addAttribute("loginUser", (String)user.get("usr_email"));
		}
	
		// 댓글 목록도 불러오기 
		List<Map<String, Object>> comments = commentService.getCommentList(ntt_id);
		
		model.addAttribute("article", article);
		model.addAttribute("comments", comments);
		if(user != null) model.addAttribute("loginUser", user.get("usr_email"));
		
		return "detail";
	}
	
	
	// 게시물 수정 화면
	@GetMapping("updateArticleView.do")
	public String updateArticleView(@RequestParam int ntt_id, Model model) { 
		
		// 게시물 상세 정보 (수정 전 내용 출력)
		Map<String, Object> article = boardService.selectArticleDetail(ntt_id);
		model.addAttribute("article", article);
		model.addAttribute("mode", "modi");
	
		return "register";
	}
	
	
	// 게시물 수정
	@PutMapping("updateArticle.do")
	public String updateArticle(@RequestParam HashMap<String, Object> article) { 
		
		boardService.updateArticle(article);
		
		int ntt_id = (int)article.get("ntt_id");
		// 목록 조회 화면으로 이동한다.
		return "redirect:/cop/bbs/selectArticleDetail.do?ntt_id="+ntt_id;
	}
	
	
	// 게시물 삭제
	@DeleteMapping("deleteArticle.do")
	@ResponseBody
	public void deleteBoardArticle(@RequestParam String ntt_id) {
		
		boardService.deleteArticle(Integer.parseInt(ntt_id));
	}

}
