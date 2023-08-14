package com.example.demo.dao;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.catalina.startup.Catalina.ServerXml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.demo.lib.DBConfig;
import com.example.demo.lib.DBConnect;

@Repository
public class BoardDAO {

	@Autowired
	DBConfig dbConfig;

	// 게시물 목록 조회
	public List<Map<String, Object>> selectArticleList() {

		String sql = "SELECT ntt_id, ntt_no, ntt_sj, ntcr_id, frst_regist_pnttm, rdcnt " + "FROM comtnbbs "
				+ "ORDER BY frst_regist_pnttm";

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dbConfig.dataSource());

		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);

		return list;
	}

	// 게시물 등록
	public void insertArticle(HashMap<String, Object> article) {

		article.put("ntt_id", newId());

		String sql = "INSERT INTO comtnbbs (ntt_id, ntt_sj, ntt_cn) " + "VALUES (:ntt_id, :ntt_sj, :ntt_cn)";

		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dbConfig.dataSource());
		jdbcTemplate.update(sql, article);

	}

	// 게시물 상세 조회
	public Map<String, Object> selectArticleDetail(int ntt_id) {

		String sql = "SELECT ntt_id, ntt_sj, ntt_cn, ntcr_nm, ntcr_id, frst_regist_pnttm, rdcnt, ntce_bgnde, ntce_endde "
				+ "FROM comtnbbs c " + "WHERE c.ntt_id = :ntt_id";

		incrementRdcnt(ntt_id);

		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dbConfig.dataSource());
		HashMap<String, Object> tempMap = new HashMap<>();
		tempMap.put("ntt_id", ntt_id);

		Map<String, Object> article = jdbcTemplate.queryForMap(sql, tempMap);

		return article;
	}

	// 게시물 수정
	public void updateArticle(HashMap<String, Object> article) {

		// character -> numeric
		article.put("ntt_id", Integer.parseInt((String) article.get("ntt_id")));

		String sql = "UPDATE comtnbbs " + "SET ntt_sj = :ntt_sj , ntt_cn = :ntt_cn " + "WHERE ntt_id = :ntt_id";

		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dbConfig.dataSource());
		jdbcTemplate.update(sql, new MapSqlParameterSource(article));

	}

	// 게시물 삭제
	public void deleteArticle(int ntt_id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = new DBConnect().getConn();

			String sql = " DELETE FROM comtnbbs c " + "WHERE c.ntt_id = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ntt_id);

			int result = pstmt.executeUpdate();
			if (result == 1)
				System.out.println("게시물 삭제 성공!");
			else
				System.out.println("게시물 삭제 실패");

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

			String sql = " UPDATE comtnbbs " + "SET rdcnt = rdcnt + 1 " + "WHERE ntt_id = ?";

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

	// id 설정
	public int newId() {

		String sql = "SELECT MAX(ntt_id) FROM comtnbbs";

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dbConfig.dataSource());

		Integer id = jdbcTemplate.queryForObject(sql, Integer.class);

		return id.intValue() + 1;
	}
}
