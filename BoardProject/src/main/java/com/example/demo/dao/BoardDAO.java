package com.example.demo.dao;

import java.security.Timestamp;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class BoardDAO {
	
	
	// 게시물 목록 조회
	public List<HashMap<String, Object>> selectArticleList() { 
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<HashMap<String, Object>> list = new ArrayList<>();
		
		try {
			conn = new DBConnect().getConn();

			String sql = "SELECT * FROM comtnbbs order by frst_regist_pnttm";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			while (rs.next()) {
				
				HashMap<String, Object> article = new HashMap<>();
				
				article.put("ntt_no", rs.getInt("ntt_no"));
				article.put("ntt_sj", rs.getString("ntt_sj"));
				article.put("ntcr_id", rs.getString("ntcr_id"));
				article.put("frst_regist_pnttm", rs.getDate("frst_regist_pnttm"));
				article.put("rdcnt", rs.getInt("rdcnt"));
				
				article.put("ntt_id", rs.getInt("ntt_id"));
				
				list.add(article);
			}
		} catch (Exception e) {
			e.printStackTrace();
		
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
			}
		}
		return list;	
	}
	
	
	// 게시물 등록 
	public void insertArticle(HashMap<String, Object> article) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result;
		
		try {
			conn = new DBConnect().getConn();
			
//			String sql = 
//					" INSERT INTO comtnbbs "
//					+ " (ntt_id, ntt_no, ntt_sj, ntt_cn, use_at, ntce_bgnde, ntce_endde, ntcr_id,"
//					+ " ntcr_nm, password, frst_regist_pnttm, frst_register_id, atch_file_id)"
//					+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
			// 테스트용
			String sql = "INSERT INTO comtnbbs (ntt_id ,ntt_sj, ntt_cn) VALUES (?,?,?)";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, 444);
			pstmt.setString(2, article.get("ntt_sj").toString());
			pstmt.setString(3, article.get("ntt_cn").toString());
//			
//			pstmt.setString(3, (String)article.get("ntt_sj"));
//			pstmt.setString(4, (String)article.get("ntt_cn"));
//			// pstmt.setString(5, "y");
//			pstmt.setString(6, (String)article.get("ntce_bgnde"));
//			pstmt.setString(7, (String)article.get("ntce_endde"));
//			pstmt.setString(8, (String)article.get("ntcr_id"));
//			pstmt.setString(9, (String)article.get("ntcr_nm"));
//			
//			pstmt.setString(10, (String)article.get("password"));
//			pstmt.setTimestamp(11, java.sql.Timestamp.valueOf((String)article.get("frst_regist_pnttm")));
//			pstmt.setString(12, (String)article.get("frst_register_id"));
//			
//			pstmt.setString(13, (String)article.get("atch_file_id"));
			
			
			result = pstmt.executeUpdate();
			if(result==1) System.out.println("게시물 등록 성공!"); 
			else System.out.println("게시물 등록 실패");

			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
			}
		}

	}
	
	
	// 게시물 상세 조회
	public HashMap<String, Object> selectArticleDetail(int ntt_id) { 
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		HashMap<String, Object> article = new HashMap<>();
		
		try {
			
			// 조회수 증가
			incrementRdcnt(ntt_id);
			
			conn = new DBConnect().getConn();
			
			String sql = 
					" SELECT * "
					+ "FROM comtnbbs c "
					+ "WHERE c.ntt_id = ?";
			
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ntt_id);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				article.put("ntt_sj", rs.getString("ntt_sj"));
				article.put("ntcr_nm", rs.getString("ntcr_nm"));
				article.put("ntcr_id", rs.getString("ntcr_id"));
				article.put("frst_regist_pnttm", rs.getDate("frst_regist_pnttm"));
				article.put("rdcnt", rs.getInt("rdcnt"));
				
				article.put("ntt_cn", rs.getString("ntt_cn"));
				
				article.put("ntce_bgnde", rs.getString("ntce_bgnde"));
				article.put("ntce_endde", rs.getString("ntce_endde"));
				
				article.put("ntt_id", rs.getInt("ntt_id"));
			}
			
			
		
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
			}
		}
		
		return article;
	}
	
	
	// 게시물 수정 
	public void updateArticle(HashMap<String, Object> article) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = new DBConnect().getConn();
			
			String sql = "";
					
			pstmt = conn.prepareStatement(sql);
//			pstmt.setInt(1, Integer.parseInt(article.get("ntt_id").toString()));
			
			int result = pstmt.executeUpdate();
			if(result==1) System.out.println("게시물 삭제 성공!"); 
			else System.out.println("게시물 삭제 실패");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
			}
		}
	}
	
	// 게시물 삭제
	public void deleteArticle(int ntt_id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = new DBConnect().getConn();
			
			String sql = 
					" DELETE FROM comtnbbs c "
					+ "WHERE c.ntt_id = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ntt_id);
			
			int result = pstmt.executeUpdate();
			if(result==1) System.out.println("게시물 삭제 성공!"); 
			else System.out.println("게시물 삭제 실패");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
			}
		}
	}
	
	// 조회수 증가
	public void incrementRdcnt(int ntt_id) { 
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = new DBConnect().getConn();
			
			String sql = 
					" UPDATE comtnbbs "
					+ "SET rdcnt = rdcnt + 1 "
					+ "WHERE ntt_id = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ntt_id);
			
			pstmt.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
			}
		}
	}

}
