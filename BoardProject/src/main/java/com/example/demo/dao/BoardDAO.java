package com.example.demo.dao;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import com.example.demo.lib.DBConfig;


@Repository
public class BoardDAO {

	@Autowired
	DBConfig dbConfig;

	org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

	// 게시물 목록 조회
	public List<Map<String, Object>> selectArticleList() {

		String sql = 
				  "SELECT ntt_id, ntt_no, ntt_sj, ntcr_id, ntcr_nm, frst_regist_pnttm, rdcnt "
				+ "FROM comtnbbs "
				+ "ORDER BY frst_regist_pnttm";

		return dbConfig.getJdbc().queryForList(sql);

	}

	// 게시물 등록
	public void insertArticle(HashMap<String, Object> article) {

		article.put("ntt_id", newId());

		String sql = 
				  "INSERT INTO comtnbbs (ntt_id, ntt_sj, ntt_cn, ntcr_id, ntcr_nm, frst_register_id) "
				+ "VALUES (:ntt_id, :ntt_sj, :ntt_cn, :ntcr_id, :ntcr_nm, :frst_register_id)";

		dbConfig.getNamedParameterJdbc().update(sql, article);
	}

	// 게시물 상세 조회
	public Map<String, Object> selectArticleDetail(int ntt_id) {

		// 조회수 증가
		incrementRdcnt(ntt_id);

		String sql = 
				  "SELECT ntt_id, ntt_sj, ntt_cn, ntcr_nm, ntcr_id, frst_regist_pnttm, rdcnt, ntce_bgnde, ntce_endde, last_updt_pnttm, last_updusr_id "
				+ "FROM comtnbbs " 
			    + "WHERE ntt_id = :ntt_id";

		return dbConfig.getNamedParameterJdbc().queryForMap(sql, new MapSqlParameterSource().addValue("ntt_id", ntt_id));
	}

	// 게시물 수정
	public void updateArticle(HashMap<String, Object> article) {

		// character -> numeric
		article.put("ntt_id", Integer.parseInt((String) article.get("ntt_id")));
		article.put("last_updusr_id", article.get("last_updusr_id"));
		article.put("last_updt_pnttm", LocalDateTime.now());

		String sql = 
				  "UPDATE comtnbbs "
				+ "SET ntt_sj = :ntt_sj , ntt_cn = :ntt_cn, last_updt_pnttm = :last_updt_pnttm, last_updusr_id = :last_updusr_id "
				+ "WHERE ntt_id = :ntt_id";

		dbConfig.getNamedParameterJdbc().update(sql, new MapSqlParameterSource(article));
	}

	// 게시물 삭제
	public void deleteArticle(int ntt_id) {

		String sql = "DELETE FROM comtnbbs " + "WHERE ntt_id = :ntt_id";
		dbConfig.getNamedParameterJdbc().update(sql, new MapSqlParameterSource().addValue("ntt_id", ntt_id));
	}

	// 조회수 증가
	public void incrementRdcnt(int ntt_id) {
		
		String sql = "UPDATE comtnbbs SET rdcnt = rdcnt + 1 WHERE ntt_id = :ntt_id";
		dbConfig.getNamedParameterJdbc().update(sql, new MapSqlParameterSource().addValue("ntt_id", ntt_id));
	}

	// id 설정
	public int newId() {
		String sql = "SELECT MAX(ntt_id) FROM comtnbbs";
		Integer id = dbConfig.getJdbc().queryForObject(sql, Integer.class);

		return id.intValue() + 1;
	}
}
