package com.example.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.BoardDAO;


@Service
public class BoardService {
	
	@Autowired
	BoardDAO boardDAO;
	
	// 게시물 목록 조회
	@Transactional
	public List<Map<String, Object>> selectArticleList() { 
		
		List<Map<String, Object>> list = boardDAO.selectArticleList();
		return list;
	}
	
	// 게시물 등록 
	public void insertArticle(HashMap<String, Object> article) {
		
		boardDAO.insertArticle(article);
	}
	
	// 게시물 상세 조회
	@Transactional
	public Map<String, Object> selectArticleDetail(int ntt_id) { 
	
		return boardDAO.selectArticleDetail(ntt_id);
	}
	
	
	// 게시물 수정 
	public void updateArticle(HashMap<String, Object> article) { 
		
		boardDAO.updateArticle(article);
	}
	
	// 게시물 삭제
	public void deleteArticle(int ntt_id) { 
		boardDAO.deleteArticle(ntt_id);
	}
	
	
	
	
	
	
	
	

}
